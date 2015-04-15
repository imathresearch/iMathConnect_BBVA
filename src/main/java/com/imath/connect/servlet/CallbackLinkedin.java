package com.imath.connect.servlet;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.CharacterData;

import com.google.common.collect.ImmutableMap;
import com.imath.connect.model.UserAccess;
import com.imath.connect.model.UserConnect;
import com.imath.connect.service.UserAccessController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.service.UserJBossController;
import com.imath.connect.service.UserJBossRolesController;
import com.imath.connect.util.Constants;
import com.imath.connect.util.Mail;
import com.imath.connect.util.Security;
import com.imath.connect.util.SecurityImpl;
import com.imath.connect.util.SecurityInterface;

/**
 * Servlet implementation class CallbackLinkedin
 */
@WebServlet("/CallbackLinkedin")
public class CallbackLinkedin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject protected Logger LOG;
	@Inject UserConnectController ucc;
	@Inject UserAccessController uac;
	@Inject Security sc;
	
	private SecurityInterface security = new SecurityImpl();
	private Mail mail = new Mail();
       
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("CallbackLinkedin");
		
		
		if (request.getParameter("error") != null) {
			//response.getWriter().println(request.getParameter("error"));
			response.sendRedirect("loginErrorThirdAccount.html");
		    return;
		}
		    
		// google returns a code that can be exchanged for a access token
		String code = request.getParameter("code");
		    
		// get the access token by post to Google
		String body = post("https://www.linkedin.com/uas/oauth2/accessToken", ImmutableMap.<String,String>builder()
		.put("code", code)
		.put("client_id", Constants.CLIENTID_LINKEDIN)
		.put("client_secret", Constants.CLIENTSECRET_LINKEDIN)
		.put("redirect_uri", "http://" + Constants.IMATH_HOST() + ":" + Constants.IMATH_PORT + "/iMathConnect/callbacklinkedin")
		.put("grant_type", "authorization_code").build());
		 		 		    
		 JSONObject jsonObject = null;		    
		 // get the access token from json and request info from Google
		 try {
			 jsonObject = (JSONObject) new JSONParser().parse(body);
		 } catch (org.json.simple.parser.ParseException e) {
			 LOG.severe(e.getMessage());
			 throw new RuntimeException("Unable to parse json " + body);
		 }
		    
		 // google tokens expire after an hour, but since we requested offline access we can get a new token without user involvement via the refresh token
		 String accessToken = (String) jsonObject.get("access_token");
		
		// you may want to store the access token in session
		request.getSession().setAttribute("access_token", accessToken);
	    
		// now we could get the email address with the access token
		String json = get(new StringBuilder("https://api.linkedin.com/v1/people/~/email-address?oauth2_access_token=").append(accessToken).toString());
	    
		DocumentBuilder db;
		String email = new String();
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(json));
			Document doc = db.parse(is);
		    NodeList nodes = doc.getElementsByTagName("email-address");
		    Element line = (Element) nodes.item(0);
		    email = getCharacterDataFromElement(line);
		    System.out.println(email);
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
													
		
		UserConnect user; 		
		try {		
			
			user = ucc.getUserByEMail(email);
			
			try{ 
				// The user already exists in the system
				UserAccess access = uac.getUserAccess(user.getUUID());
				if(access.getPassword() != null){
					try {
						request.login(user.getUserName(), access.getPassword());
			            response.sendRedirect("indexNew.jsp");
			            return;
			        } catch(ServletException e) {
			        	LOG.severe(e.getMessage());
			            response.sendRedirect("loginerror.html");
			            return;
			        }
					
				}
				else{
					// The user has not password, so this is not its login way
					response.sendRedirect("loginerror.html");
	                return;					
				}
			} catch (Exception e1){
				LOG.severe(e1.getMessage());
				response.sendRedirect("loginerror.html");
                return;
			}
			
		} catch(Exception e2){
			// New user
			try{
			
				user = ucc.newUserConnectThirdParty(email, Constants.LINKEDIN_ACCOUNT);
				//this.security.createSystemUser(user.getUserName(), user.getUserAccess().getPassword(), Constants.SYSTEM_ROLE);
                sc.createSystemUserDB(user.getUserName(), user.getUserAccess().getPassword(), Constants.SYSTEM_ROLE);               
				this.mail.sendWelcomeMail(user.getEMail(), user.getUserName());
	            this.mail.sendNewUserMailToAdmin(user.getEMail(), user.getUserName()); 
	            request.login(user.getUserName(), user.getUserAccess().getPassword());
                response.sendRedirect("indexNew.jsp");
			
			} catch (Exception e3){
				LOG.severe(e3.getMessage());
				response.sendRedirect("registererror.html");
                return;
				
			}
		
		}	   
			
		
	}

	// makes a GET request to url and returns body as a string
	public String get(String url) throws ClientProtocolException, IOException {
		return execute(new HttpGet(url));
	}
		  
	 // makes a POST request to url with form parameters and returns body as a string
	 public String post(String url, Map<String,String> formParameters) throws ClientProtocolException, IOException { 
		 HttpPost request = new HttpPost(url);
	    
		 List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	   
		 for (String key : formParameters.keySet()) {
			 nvps.add(new BasicNameValuePair(key, formParameters.get(key))); 
		 }
	 
		 request.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));		 		
	   
		 return execute(request);
	 }
		  
	 // makes request and checks response code for 200
	 private String execute(HttpRequestBase request) throws ClientProtocolException, IOException {
		 HttpClient httpClient = new DefaultHttpClient();
		 HttpResponse response = httpClient.execute(request);
	      
		 HttpEntity entity = response.getEntity();
	     String body = EntityUtils.toString(entity);
	 
	     if (response.getStatusLine().getStatusCode() != 200) {
	    	 throw new RuntimeException("Expected 200 but got " + response.getStatusLine().getStatusCode() + ", with body " + body);
	     }
	 
	     return body;
	 }
		
	 public static String getCharacterDataFromElement(Element e) {
		    Node child = e.getFirstChild();
		    if (child instanceof CharacterData) {
		      CharacterData cd = (CharacterData) child;
		      return cd.getData();
		    }
		    return "";
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

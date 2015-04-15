package com.imath.connect.servlet;

import java.io.IOException;
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

import org.apache.http.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
 * Servlet implementation class CallbackGoogle
 */
@WebServlet("/CallbackGoogle")
public class CallbackGoogle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private final String clientId = "230432612246-dpbgp99e9n17tvki2v5dtg5n6qilajfs.apps.googleusercontent.com";
	//private final String clientSecret = "bR-dClMFs2g7aLtGI_JIQCRv";  
    
	@Inject UserConnectController ucc;
	@Inject UserAccessController uac;
	@Inject protected Logger LOG;
	@Inject Security sc;
	
	private SecurityInterface security = new SecurityImpl();
	private Mail mail = new Mail();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("CallbackGoogle servlet");
		
		if (request.getParameter("error") != null) {
			//response.getWriter().println(request.getParameter("error"));
			response.sendRedirect("loginErrorThirdAccount.html");
		    return;
		}
		    
		// google returns a code that can be exchanged for a access token
		String code = request.getParameter("code");
		    
		// get the access token by post to Google
		String body = post("https://accounts.google.com/o/oauth2/token", ImmutableMap.<String,String>builder()
		.put("code", code)
		.put("client_id", Constants.CLIENTID_GOOGLE)
		.put("client_secret", Constants.CLIENTSECRET_GOOGLE)
		.put("redirect_uri", "http://" + Constants.IMATH_HOST() +":" + Constants.IMATH_PORT + "/iMathConnect/callbackgoogle")
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
	    
		// get some info about the user with the access token
		String json = get(new StringBuilder("https://www.googleapis.com/oauth2/v1/userinfo?access_token=").append(accessToken).toString());
	    
		// now we could get the email address 
		org.json.JSONObject jsonObj = new org.json.JSONObject(json);
		String email = jsonObj.getString("email");					
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
			
				user = ucc.newUserConnectThirdParty(email, Constants.GOOGLE_ACCOUNT);
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
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

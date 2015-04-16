package com.imath.connect.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imath.connect.config.AppConfig;
import com.imath.connect.util.Constants;

/**
 * Servlet implementation class LoginLinkedin
 */
@WebServlet("/LoginLinkedin")
public class LoginLinkedin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	   // redirect to linkedin for authorization
	   StringBuilder oauthUrl = new StringBuilder().append("https://www.linkedin.com/uas/oauth2/authorization")
	   .append("?client_id=").append(AppConfig.getProp(AppConfig.CLIENTID_LINKEDIN)) // the client id from the api console registration
	   .append("&response_type=code")
	   .append("&scope=r_emailaddress") // scope is the api permissions we are requesting
	   .append("&redirect_uri=http://" + AppConfig.getProp(AppConfig.IMATH_HOST) + ":" + AppConfig.getProp(AppConfig.IMATH_PORT) + "/iMathConnect/callbacklinkedin") // the servlet that google redirects to after authorization
	   .append("&state=this_can_be_anything_to_help_correlate_the_response%3Dlike_session_id")
	   .append("&access_type=offline") // here we are asking to access to user's data while they are not signed in
	   .append("&approval_prompt=force"); // this requires them to verify which account to use, if they are already signed in	  
	   response.sendRedirect(oauthUrl.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

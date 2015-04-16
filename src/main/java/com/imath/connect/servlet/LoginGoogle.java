package com.imath.connect.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imath.connect.config.AppConfig;
import com.imath.connect.util.Constants;


public class LoginGoogle extends HttpServlet {
	
	//private final String clientId = "230432612246-dpbgp99e9n17tvki2v5dtg5n6qilajfs.apps.googleusercontent.com";
	//private final String clientSecret = "bR-dClMFs2g7aLtGI_JIQCRv";

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
	   
		System.out.println("Login google " + "http://" + AppConfig.getProp(AppConfig.IMATH_HOST) +":" + AppConfig.getProp(AppConfig.IMATH_PORT) + "/iMathConnect/callbackgoogle");
	   // redirect to google for authorization
	   StringBuilder oauthUrl = new StringBuilder().append("https://accounts.google.com/o/oauth2/auth")
	   .append("?client_id=").append(AppConfig.getProp(AppConfig.CLIENTID_GOOGLE)) // the client id from the api console registration
	   .append("&response_type=code")
	   .append("&scope=email") // scope is the api permissions we are requesting
	   .append("&redirect_uri=http://" + AppConfig.getProp(AppConfig.IMATH_HOST) +":" + AppConfig.getProp(AppConfig.IMATH_PORT) + "/iMathConnect/callbackgoogle") // the servlet that google redirects to after authorization
	   .append("&state=this_can_be_anything_to_help_correlate_the_response%3Dlike_session_id")
	   .append("&access_type=online") // here we are asking to access to user's data while they are not signed in
	   .append("&approval_prompt=auto"); // this requires them to verify which account to use, if they are already signed in	  
	   resp.sendRedirect(oauthUrl.toString());
  }  
}

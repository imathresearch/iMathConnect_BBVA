package com.imath.connect.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imath.connect.util.Constants;

public class LoginGitHub extends HttpServlet {
    
        
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        
       // redirect to google for authorization
       StringBuilder oauthUrl = new StringBuilder().append("https://github.com/login/oauth/authorize")
       .append("?client_id=").append(Constants.CLIENTID_GITHUB) // the client id from the api console registration
       .append("&scope=user:email") // scope is the api permissions we are requesting
       .append("&redirect_uri=http://"+ Constants.IMATH_HOST() + ":" + Constants.IMATH_PORT + "/iMathConnect/callbackgithub") // the servlet that google redirects to after authorization
       .append("&state=this_can_be_anything_to_help_correlate_the_response%3Dlike_session_id");
        resp.sendRedirect(oauthUrl.toString());
  }  


}

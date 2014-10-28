package com.imath.connect.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imath.connect.model.UserConnect;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.Mail;
import com.imath.connect.util.Security;
import com.imath.connect.util.Util;

import java.util.logging.Logger;


public class RecoverPassword extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Inject UserConnectController ucc;
    @Inject Logger LOG;
    
    // imathcloud943793072
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	//Encryptor.init();
        String eMail = request.getParameter("emailsignup");
        UserConnect user=null;
		try {
			user = ucc.getUserByEMail(eMail);
		} catch (Exception e1) {
			response.sendRedirect("recoverPasswordError.html");
        	return;
		}
        
        if(user == null){
        	response.sendRedirect("recoverPasswordError.html");
        	return;
        }
        
        //Check if the user was registered using a third-party account
        if(user.getUserAccess().getPassword() == null){
        	// Generate an random password
	        String randomPassword = Util.randomString(10);
	        
	        String userName = user.getUserName();
	        try {
	            Security.updateSystemPassword(userName, randomPassword);
	        } catch (Exception e) {
	            
	            response.sendRedirect("loginerror.html");
	            return;
	        }
	        
	        try {
	            Mail mail = new Mail();
	            mail.sendRecoverPasswordMail(eMail, userName, randomPassword);
	            response.sendRedirect("recoverPasswordInfo.html");
	            
	        } catch (Exception e) {
	            // Nothing happens here...
	        }
        }
        else{
        	response.sendRedirect("notAllowRecoverPassword.html");
        	return;
        }
        
             
        
        
    }
}
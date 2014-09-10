package com.imath.connect.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imath.connect.model.UserConnect;
import com.imath.connect.service.UserConnectController;

public class Login extends HttpServlet{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    @Inject 
    private Logger LOG;
    @Inject UserConnectController ucc;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("New login servlet");
    	try { 
    		String userName = request.getParameter("j_username");
    		String passWord = request.getParameter("j_password");
    		request.login(userName, passWord);
    		UserConnect user=null;
    		user = ucc.getUserConnectByUserName(userName);
    		ucc.setCurrentConnection(user.getUUID());    		
    		response.sendRedirect("indexNew.jsp"); 
    	} catch(ServletException e) {
    	    // Login failed
    	    response.sendRedirect("loginerror.html");
    	}
    }
}

package com.imath.connect.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    @Inject 
    private Logger LOG;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getUserPrincipal().getName();
        LOG.warning("User Logout: " + userName );
        // we logout from the system
        request.logout();
        response.sendRedirect(".");
    }
}

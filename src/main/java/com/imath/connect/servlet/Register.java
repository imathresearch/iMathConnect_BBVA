package com.imath.connect.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imath.connect.model.Notification;
import com.imath.connect.model.UserConnect;
import com.imath.connect.service.NotificationController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.Mail;
import com.imath.connect.util.Security;
import com.imath.connect.util.Constants;

public class Register extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Inject UserConnectController ucc;
    @Inject NotificationController notc;
    @Inject protected Logger LOG;
    
    // imathcloud943793072
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String userName = request.getParameter("usernamesignup");
        String password = request.getParameter("passwordsignup");
        String passwordRep = request.getParameter("passwordsignup_confirm");
        String eMail = request.getParameter("emailsignup");
        
        /*
         * We have to add a recover the photo to store in the database.
         * */

        
        if (!password.equals(passwordRep)) {
            response.sendRedirect("registererrorPasswords.html");
            return;
        }
        
        try {
        	UserConnect user = ucc.newUserConnect(userName, eMail, "", null, null, null);            
            Security.createSystemUser(userName, passwordRep, Constants.SYSTEM_ROLE);            
                        
            try {
                Mail mail = new Mail();
                mail.sendWelcomeMail(eMail, userName);
            } catch (Exception e) {
                // Nothing happens so far...
            }
            try {
                request.login(userName, password);
                response.sendRedirect("indexNew.jsp");
                return;
            } catch(ServletException e) {
                response.sendRedirect("loginerror.html");
                return;
            }
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            response.sendRedirect("registererror.html");
            return;
        }
    }
}

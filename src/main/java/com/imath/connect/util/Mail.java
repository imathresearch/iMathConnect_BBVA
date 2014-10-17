package com.imath.connect.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import
java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Mail {
    // TODO: put this into protected external files
    public static String cr1 = "imathcloud@imathresearch.com";
    public static String cr2 = "imathcloud943793072";
    
    public static void sendBasicMail(String to, String subject, String body) throws Exception {
        Session session = getSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(cr1));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void sendHTMLMail(String to, String subject, String htmlBody) throws Exception {
        Session session = getSession();
        try {
            Multipart mp = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html");
            mp.addBodyPart(htmlPart);
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(cr1));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void sendWelcomeMail(String to, String username) throws Exception {
        String html="";
        InputStream in = this.getClass().getResourceAsStream(Constants.WELLCOME_TEMPLATE);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = br.readLine()) != null) {
            html += line + "\n";
        }
        br.close();
        html = html.replace("[USERNAME]", username);
        String url = Constants.IMATH_HTTP + Constants.IMATH_HOST();
        if (!Constants.IMATH_PORT.equals("80")) {
            url += ":" + Constants.IMATH_PORT;
        }
        url += "/iMathConnect";
        html = html.replace("[URL_IMATHCLOUD]", url);
        Mail.sendHTMLMail(to, "Welcome to iMathCloud! The Cloud Platform for Data Scientists", html);
    }
    
    public void sendInvitationMail(String to, String username, String project) throws Exception {
        String html="";
        InputStream in = this.getClass().getResourceAsStream(Constants.INVITATION_TEMPLATE);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = br.readLine()) != null) {
            html += line + "\n";
        }
        br.close();
        html = html.replace("[USERNAME]", username);
        html = html.replace("[PROJECT]", project);
        String url = Constants.IMATH_HTTP + Constants.IMATH_HOST();
        if (!Constants.IMATH_PORT.equals("80")) {
            url += ":" + Constants.IMATH_PORT;
        }
        url += "/iMathConnect";
        html = html.replace("[URL_IMATHCLOUD]", url);
        Mail.sendHTMLMail(to, "[iMathCloud] Invitation to participate in the project " + project, html);
    }
    
    public void sendInvitationNewUserMail(String to, String username, String password, String project) throws Exception {
        String html="";
        InputStream in = this.getClass().getResourceAsStream(Constants.INVITATION_TEMPLATE_NEW_USER);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = br.readLine()) != null) {
            html += line + "\n";
        }
        br.close();
        html = html.replace("[USERNAME]", username);
        html = html.replace("[PROJECT]", project);
        html = html.replace("[PASSWORD]", password);
        
        String url = Constants.IMATH_HTTP + Constants.IMATH_HOST();
        if (!Constants.IMATH_PORT.equals("80")) {
            url += ":" + Constants.IMATH_PORT;
        }
        url += "/iMathConnect";
        html = html.replace("[URL_IMATHCLOUD]", url);
        Mail.sendHTMLMail(to, "Welcome to iMathCloud! The Cloud Platform for Data Scientists", html);
    }
    
    public void sendNewUserMailToAdmin(String useremail, String username) throws Exception { 
        String html="";
        InputStream in = this.getClass().getResourceAsStream(Constants.NEW_USER_ADMIN_NOTIFICATION_TEMPLATE);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = br.readLine()) != null) {
            html += line + "\n";
        }
        br.close();

        html = html.replace("[USERNAME]", username);
        html = html.replace("[EMAIL]", useremail);
                               
        List<String> listEmails = readAdminEmail();
        for(String st: listEmails){
        	Mail.sendHTMLMail(st, "[newUser][iMathCloud] A new user is registered to iMathCloud!", html);
        }
        
    }
    
    
    private List<String> readAdminEmail() throws IOException {
    	File adminEmail = new File(Constants.ADMIN_FILE_EMAIL);
    	BufferedReader br = new BufferedReader(new FileReader(adminEmail));
     
    	String line = null;
    	List<String> listEmails = new ArrayList<String>();
    	while ((line = br.readLine()) != null) {
    		listEmails.add(line);
    	}
    	
    	br.close();
    	return listEmails;
    }
    
    public void sendRecoverPasswordMail(String to, String username, String newPassword) throws Exception {
        String html="";
        InputStream in = this.getClass().getResourceAsStream(Constants.RECOVER_TEMPLATE);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = br.readLine()) != null) {
            html += line + "\n";
        }
        br.close();
        html = html.replace("[USERNAME]", username);
        html = html.replace("[PASSWORD]", newPassword);
        String url = Constants.IMATH_HTTP + Constants.IMATH_HOST();
        if (!Constants.IMATH_PORT.equals("80")) {
            url += ":" + Constants.IMATH_PORT;
        }
        url += "/iMathConnect";
        html = html.replace("[URL_IMATHCLOUD]", url);
        Mail.sendHTMLMail(to, "[iMathCloud] Recover Password", html);
    }
    
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.alwaysdata.com");
        props.put("mail.smtp.port", "587");
 
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(cr1, cr2);
            }
          });
        return session;
    }
    
    
}

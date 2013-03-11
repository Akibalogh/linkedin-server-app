package com.generationanalytics.app;

import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

public class SendMail {

    private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
    private static final String SMTP_AUTH_USER = "akibalogh@gmail.com";
    private static final String SMTP_AUTH_PWD  = "Generation1234!";
    private static final String TYEMAILTEMPLATE = "/root/biffle-prototype/email-templates/coffee-signup-ty";

    public static void main(String emailAddress) throws Exception{
       new SendMail().sendThankYou(emailAddress);
    }

    public void sendThankYou(String emailAddress) throws Exception
	{
	        try
		{
			Properties props = new Properties();
		        props.put("mail.transport.protocol", "smtp");
		        props.put("mail.smtp.host", SMTP_HOST_NAME);
		        props.put("mail.smtp.port", 587);
		        props.put("mail.smtp.auth", "true");

		        Authenticator auth = new SMTPAuthenticator();
		        Session mailSession = Session.getDefaultInstance(props, auth);
		        // uncomment for debugging infos to stdout
		        // mailSession.setDebug(true);
		        Transport transport = mailSession.getTransport();

		        MimeMessage message = new MimeMessage(mailSession);
		        Multipart multipart = new MimeMultipart("alternative");

		 	File emailTemplateFile = new File(TYEMAILTEMPLATE);
	                FileReader emailStream = new FileReader(emailTemplateFile);
	                BufferedReader emailIn = new BufferedReader(emailStream);

			String emailTemplate = "";
			String currentLine;

			while ((currentLine = emailIn.readLine()) != null)
			{ emailTemplate += currentLine; }

		        BodyPart htmlPart = new MimeBodyPart();
		        htmlPart.setContent(emailTemplate, "text/html");
		        multipart.addBodyPart(htmlPart);

		        message.setContent(multipart);
		        message.setFrom(new InternetAddress("aki@biffle.co"));
		        message.setSubject("Welcome to Biffle!");
		        message.addRecipient(Message.RecipientType.TO,
		             new InternetAddress(emailAddress));

		        transport.connect();
		        transport.sendMessage(message,
		            message.getRecipients(Message.RecipientType.TO));
		        transport.close();
		}
		catch (Exception e)
		{ System.out.println("ERROR in SendEmail");}
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = SMTP_AUTH_USER;
           String password = SMTP_AUTH_PWD;
           return new PasswordAuthentication(username, password);
        }
    }
}

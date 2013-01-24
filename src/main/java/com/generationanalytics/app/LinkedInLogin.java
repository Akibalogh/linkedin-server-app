package com.generationanalytics.app;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Properties;

import com.google.code.linkedinapi.schema.Person;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;

@SuppressWarnings("serial")
public class LinkedInLogin extends HttpServlet {

	private static final String CONSUMER_KEY="lffo6yr4wx9t";
	private static final String CONSUMER_SECRET="ns8Br5H7hREyRCQq";

	private static String tokenValue;
	private static String tokenSecret;


    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
	String callbackUrl = "http://50.22.200.186:8080/servlet/LinkedInLogin";
	Properties systemSettings = System.getProperties();
	// systemSettings.put("http.proxyHost", "proxy");
	// systemSettings.put("http.proxyPort", "8080");
	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
	out.println("<html>");
        out.println("<body>");

	final LinkedInOAuthService oauthService = LinkedInOAuthServiceFactory.getInstance().createLinkedInOAuthService(CONSUMER_KEY, CONSUMER_SECRET);

	LinkedInRequestToken requestToken = oauthService.getOAuthRequestToken(callbackUrl);
	// LinkedInRequestToken requestToken = oauthService.getOAuthRequestToken();

	String oauthVerifier = request.getParameter("oauth_verifier");
	
	if (oauthVerifier == null)
	{ 
		String authUrl = requestToken.getAuthorizationUrl();
		response.sendRedirect(authUrl);
	}

	out.println("request token:");
	out.println("<br>auth URL: " + requestToken.getAuthorizationUrl());
	out.println("<br>token: " + requestToken.getToken());
	out.println("<br>token secret: " + requestToken.getTokenSecret());
	out.println("<br>expiration time: " + requestToken.getExpirationTime());

/*
	LinkedInAccessToken accessToken = oauthService.getOAuthAccessToken(requestToken, oauthVerifier);


	final LinkedInApiClientFactory factory = LinkedInApiClientFactory.newInstance(CONSUMER_KEY, CONSUMER_SECRET);
	final LinkedInApiClient client = factory.createLinkedInApiClient(accessToken);


	Person profile = client.getProfileForCurrentUser();

	out.println("Fetching profile for current user");
	out.println("Headline: " + profile.getHeadline());
*/
    	out.println("</body>");
        out.println("</html>");
    
    }
}

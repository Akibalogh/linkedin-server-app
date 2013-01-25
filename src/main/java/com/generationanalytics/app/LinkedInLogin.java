package com.generationanalytics.app;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.code.linkedinapi.schema.Person;
import com.google.code.linkedinapi.client.oauth.*;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;

@SuppressWarnings("serial")
public class LinkedInLogin extends HttpServlet {

	private static final String CONSUMER_KEY="34eiwzgyjlyn";
	private static final String CONSUMER_SECRET="Oo0P0QU0oBaIYs9W";

	private static String tokenValue;
	private static String tokenSecret;


    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
	String callbackUrl = "http://50.22.200.186:8080/servlet/LinkedInLogin";
	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
	out.println("<html>");
        out.println("<body>");

	final LinkedInOAuthService oauthService = LinkedInOAuthServiceFactory.getInstance().createLinkedInOAuthService(CONSUMER_KEY, CONSUMER_SECRET);

	out.println("<br>Request Token: " + request.getParameter("requestToken")+ "<br>");

	LinkedInRequestToken requestToken = (LinkedInRequestToken) request.getAttribute("requestToken");

	if (requestToken == null)
	{ requestToken = oauthService.getOAuthRequestToken(callbackUrl); }

	String oauthVerifier = request.getParameter("oauth_verifier");
	
	if (oauthVerifier == null)
	{
		String authUrl = requestToken.getAuthorizationUrl();
		response.sendRedirect(authUrl);
	}

	out.println("API key: " + CONSUMER_KEY + "/<br>API secret: " + CONSUMER_SECRET);
	out.println("<br>request token:");
	out.println("<br>auth URL: " + requestToken.getAuthorizationUrl());
	out.println("<br>token: " + requestToken.getToken());
	out.println("<br>token secret: " + requestToken.getTokenSecret());
	out.println("<br>expiration time: " + requestToken.getExpirationTime());

	out.println("<br>parameter map: " + request.getParameterMap());
	out.println("<br>oauth token: " + request.getParameter("oauth_token"));
	out.println("<br>oauth verifier: " + request.getParameter("oauth_verifier"));

	out.println("<br>header: " + request.getHeaders("ValuesEnumerator").hasMoreElements());


	// TODO: Returns 400 error
	LinkedInAccessToken accessToken = oauthService.getOAuthAccessToken(requestToken, oauthVerifier);
/*
	final LinkedInApiClientFactory factory = LinkedInApiClientFactory.newInstance(CONSUMER_KEY, CONSUMER_SECRET);

	final LinkedInApiClient client = factory.createLinkedInApiClient(accessToken);

	out.println("<br><br>access token: " + request.getParameter("accessToken"));
	Person profile = client.getProfileForCurrentUser();

	out.println("Fetching profile for current user");
	out.println("Headline: " + profile.getHeadline());
    	out.println("</body>");
        out.println("</html>");
  */  
    }
}

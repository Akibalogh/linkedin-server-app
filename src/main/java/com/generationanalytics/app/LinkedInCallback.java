package com.generationanalytics.app;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Enumeration;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

@SuppressWarnings("serial")
public class LinkedInCallback extends HttpServlet {

	private static final String CONSUMER_KEY="34eiwzgyjlyn";
	private static final String CONSUMER_SECRET="Oo0P0QU0oBaIYs9W";
	private static String PROTECTED_RESOURCE_URL = "http://api.linkedin.com/v1/people/~:(id,first-name,last-name,industry,summary,specialties,positions,email-address,associations,honors,interests,publications,skills,twitter-accounts)";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
	out.println("<html>");
        out.println("<body>");

	OAuthService service = new ServiceBuilder()
		.provider(LinkedInApi.class)
		.apiKey(CONSUMER_KEY)
		.apiSecret(CONSUMER_SECRET)
		.build();

	String verifierStr = request.getParameter("oauth_verifier");
	Verifier verifier = new Verifier(verifierStr);

	HttpSession session = request.getSession(false);
	
	Token requestToken = (Token) session.getAttribute("requestToken");
	Token accessToken = service.getAccessToken(requestToken, verifier);

	OAuthRequest apiRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
	service.signRequest(accessToken, apiRequest);
	Response apiResponse = apiRequest.send();

    	out.println(apiResponse.getBody());
	out.println("</body>");
        out.println("</html>");
    }
}

package com.generationanalytics.app;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Date;
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
	private static String profile;

	private static final String OUTPATH = "/usr/share/downloaded-li-profiles";
	private static String outFileName;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
	response.setContentType("text/html");
	profile += "<html>\n";
        profile += "<body>\n";

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
	// Set a header to tell the server to return JSON
	apiRequest.addHeader("x-li-format", "json");
	service.signRequest(accessToken, apiRequest);
	Response apiResponse = apiRequest.send();

	profile += apiResponse.getBody() + "\n";
	profile += "</body>\n";
        profile += "</html>";
       
	// Write the API response to the user
	PrintWriter outHTML = response.getWriter();
	outHTML.println("Thanks! We'll be sending you emails going forward.");

	// The timestamp will be used to name the file
	java.util.Date date = new java.util.Date();
	outFileName = String.valueOf(date.getTime());
	
	// Write the API response to a file
	File outFile = new File(OUTPATH + "/" + outFileName);
	FileWriter fstream = new FileWriter(outFile);
	BufferedWriter fout = new BufferedWriter(fstream);

	fout.write(profile);
	fout.close();
	}
}

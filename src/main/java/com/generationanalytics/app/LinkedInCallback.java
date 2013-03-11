package com.generationanalytics.app;

import com.generationanalytics.app.SendMail;
import com.generationanalytics.app.EmailAddressParser;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Date;
import java.util.Enumeration;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import javax.xml.stream.*;

@SuppressWarnings("serial")
public class LinkedInCallback extends HttpServlet {

	private static final String CONSUMER_KEY="34eiwzgyjlyn";
	private static final String CONSUMER_SECRET="Oo0P0QU0oBaIYs9W";
	private static String PROTECTED_RESOURCE_URL = "http://api.linkedin.com/v1/people/~:(id,first-name,last-name,location,public-profile-url,industry,summary,specialties,positions,email-address,associations,honors,interests,publications,skills,twitter-accounts)";
	private static String profile;
	private static String ApiResponseStr;

	private static final String PROFILEOUTPATH = "/usr/share/downloaded-li-profiles";
	private static final String SUCCESSPAGE = "http://biffle.co:8080/success.jsp";
	private static String outFileName;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
	response.setContentType("text/html");
	profile = "<html>\n";
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
	// Disabled: Set a header to tell the server to return JSON
	// Currently request returns XML
	// apiRequest.addHeader("x-li-format", "json");
	service.signRequest(accessToken, apiRequest);
	Response apiResponse = apiRequest.send();

	ApiResponseStr = apiResponse.getBody();
	profile += ApiResponseStr + "\n";
	profile += "</body>\n";
        profile += "</html>";
       
	// Get timestamp to name the file on disk
	java.util.Date date = new java.util.Date();
	outFileName = String.valueOf(date.getTime());
	
	// Write the API response to a file
	File outFile = new File(PROFILEOUTPATH + "/" + outFileName);
	FileWriter fstream = new FileWriter(outFile);
	BufferedWriter fout = new BufferedWriter(fstream);

	fout.write(ApiResponseStr);
	fout.close();
	
	// Redirect the user to the success page
	PrintWriter outHTML = response.getWriter();
	//outHTML.println("Success! Biffle will send you an email every day at 7AM going forward<br>");
	response.sendRedirect(SUCCESSPAGE);
	try
	{
		EmailAddressParser emailAddressParser = new EmailAddressParser();
		String emailAddress = emailAddressParser.parseEmailAddress(ApiResponseStr);

		SendMail sendMail = new SendMail();
		sendMail.sendThankYou(emailAddress);
	}

	catch (XMLStreamException xe)
	{ 
		outHTML.println("ERROR: XML Exception");
		xe.printStackTrace(outHTML);
	}

	catch (Exception e)
	{
		outHTML.println("ERROR: Exception");
		e.printStackTrace(outHTML);
	}
	
	
	}
}

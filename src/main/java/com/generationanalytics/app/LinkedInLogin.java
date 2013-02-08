package com.generationanalytics.app;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

@SuppressWarnings("serial")
public class LinkedInLogin extends HttpServlet {

	private static final String CONSUMER_KEY="34eiwzgyjlyn";
	private static final String CONSUMER_SECRET="Oo0P0QU0oBaIYs9W";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
	String callbackUrl = "http://50.22.200.186:8080/servlet/LinkedInCallback";
	OAuthService service = new ServiceBuilder()
		.provider(LinkedInApi.class)
		.apiKey(CONSUMER_KEY)
		.apiSecret(CONSUMER_SECRET)
		.scope("r_basicprofile,r_fullprofile,r_emailaddress")
		.callback(callbackUrl)
		.build();

	Token requestToken = service.getRequestToken();

	HttpSession session = request.getSession(true);
	session.setAttribute("requestToken", requestToken);

	String authUrl = service.getAuthorizationUrl(requestToken);
	response.sendRedirect(authUrl);
    }
}


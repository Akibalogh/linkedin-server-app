package com.generationanalytics.app;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
*/

@SuppressWarnings("serial")
public class LinkedInLogin extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<script type='text/javascript' src='http://platform.linkedin.com/in.js'>");
        out.println("api_key: 34eiwzgyjlyn </script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<script type='in/Login'>");
        out.println("Hello, <?js= firstName ?> <?js= lastName ?>.");
        out.println("</script>");
 
        out.println("<script type='IN/Login' data-onAuth='demoLoginTagAuthCapture' data-onLogout='demoLoginTagLogoutCapture'>");
        out.println("<p>You are logged in and authorized.</p>");
        out.println("</script>");
        
        
    	/* OAuthService service = new ServiceBuilder()
        .provider(LinkedInApi.class)
        .apiKey("lffo6yr4wx9t")
        .apiSecret("ns8Br5H7hREyRCQq")
        .build();
    
    	Token requestToken = service.getRequestToken();
    	
    	out.println(service.getAuthorizationUrl(requestToken));
    	*/
    	
    	out.println("</body>");
        out.println("</html>");
    
    }
}
package com.generationanalytics.app;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;

public class EmailAddressParser
{
	public static String parseEmailAddress(String apiResponseStr)
	throws UnsupportedEncodingException, XMLStreamException
	{
		String emailAddress = "NULL";

		try
		{
			byte[] byteArray = apiResponseStr.getBytes("UTF-8");
		        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
		       	XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		       	XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);

		        while (reader.hasNext())
		        {
		                XMLEvent event = (XMLEvent) reader.next();
	
	        	        if (event.isStartElement())
		                {
	        	                StartElement element = event.asStartElement();
	                	        if (element.getName().getLocalPart() == "email-address")
	                       		{
						event = (XMLEvent) reader.next();
						emailAddress = event.asCharacters().getData();
					}
	               		}
	        	}

	        reader.close();
		}
		catch (UnsupportedEncodingException ex)
		{ return "ERROR"; }
		catch (XMLStreamException ex)
		{ return "ERROR"; }

		return emailAddress;
	}
}	

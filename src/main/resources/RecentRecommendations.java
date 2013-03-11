package com.generationanalytics.app;

import com.mongodb.*;
import java.util.Arrays;
import java.net.UnknownHostException;

@SuppressWarnings("serial")
public class RecentRecommendations {

    public static String getRecentRecommendations ()
    	{
		String recentRecommendations = "";	
		DBObject recd_article;
		DBObject recd_webpage;
		Object aid;
		Object wid;
		
		try
		{
			// Pull recommendations from Mongo
			MongoClient mongoClient = new MongoClient();
			DB db = mongoClient.getDB("db");
			DBCollection rec_articles = db.getCollection("recommended_articles");
			DBCollection articles = db.getCollection("articles");
			DBCollection rec_webpages = db.getCollection("recommended_webpages");
			DBCollection webpages = db.getCollection("webpages");

			DBCursor articleRecCursor = rec_articles.find();
			DBCursor webpageRecCursor = rec_webpages.find();

			while(articleRecCursor.hasNext())
			{
				recd_article = articleRecCursor.next();
				aid = recd_article.get("aid");
				BasicDBObject aidQuery = new BasicDBObject("_id", aid);
				DBObject articleObject = articles.findOne(aidQuery);
				recentRecommendations += "<a href='" + articleObject.get("url") + "'>";
				recentRecommendations += articleObject.get("t") + "</a><br>";
			}

		
			while(webpageRecCursor.hasNext())
			{
				recd_webpage = webpageRecCursor.next();
				wid = recd_webpage.get("wid");
				BasicDBObject widQuery = new BasicDBObject("_id", wid);
				DBObject webpageObject = webpages.findOne(widQuery);
				recentRecommendations += "<a href='" + webpageObject.get("url") + "'>";
				recentRecommendations += webpageObject.get("t") + "</a><br>";
			}


		articleRecCursor.close();
		webpageRecCursor.close();
		mongoClient.close();
		
		
		}
		catch (MongoException e)
		{ e.printStackTrace(); }

		catch (UnknownHostException e)
		{ e.printStackTrace(); }
		
		return recentRecommendations;
	}
}


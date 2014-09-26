package com.cse490m.app;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class LoginHandler extends AbstractHandler {

  public static MongoClient CLIENT;
  
  public LoginHandler() {
	  if (CLIENT == null) {
		  try {
			  CLIENT = new MongoClient();
		  } catch (UnknownHostException e) {
			  throw new RuntimeException(e);
		  }
	  }
  }
	
	
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
      
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
      response.setContentType("text/html;charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);
      baseRequest.setHandled(true);
      
      String name = request.getReader().readLine();
      
      DBObject user = new BasicDBObject("name", name);
      DBObject newTimes = new BasicDBObject("$push", new BasicDBObject("times", new Date().toString()));
      
      DBCollection col = CLIENT.getDB("490m").getCollection("users");
      col.update(user, newTimes, true, false);
      
      response.setContentType("text/x-son;charset=UTF-8");
      response.getWriter().write(col.findOne(user).toString());
  }
  
}

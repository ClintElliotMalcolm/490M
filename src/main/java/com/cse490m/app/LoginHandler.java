package com.cse490m.app;

import java.io.IOException;

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
  
  public final MongoClient client;
  
  public LoginHandler(MongoClient client) {
	  super();
	  this.client = client;
  }
	
	
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
      response.setContentType("text/html;charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);
      baseRequest.setHandled(true);
      
      String name = request.getParameter("name");
      String password = request.getParameter("password");
      
      DBObject user = new BasicDBObject("name", name).append("password", password);
      DBObject newTimes = new BasicDBObject("times", new BasicDBObject("$push", System.currentTimeMillis()));
      
      DBCollection col = client.getDB("490m").getCollection("users");
      col.update(user, newTimes, true, false);
      
      response.setContentType("text/x-son;charset=UTF-8");
      response.getWriter().write(col.findOne(user).toString());
  }
  
}
package com.cse490m.app;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import com.mongodb.MongoClient;

public class App {
  
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    
    ContextHandler helloContext = new ContextHandler();
    helloContext.setContextPath("/hello");
    helloContext.setHandler(new HelloHandler());
    
    ContextHandler loginContext = new ContextHandler();
    loginContext.setContextPath("/login");
    loginContext.setHandler(new LoginHandler());
    
    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[] { helloContext, loginContext });
       
    server.setHandler(contexts);   
    server.start();
    server.join();
  }

}

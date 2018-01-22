package mypack;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class MyServletContextListener implements ServletContextListener {

  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("helloapp application is Initialized.");
    
    ServletContext context = sce.getServletContext();
    try {
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(context.getResourceAsStream("/count/count.txt")));
      int count = Integer.parseInt(reader.readLine());
      reader.close();
      
      Counter counter = new Counter(count);
      context.setAttribute("counter", counter);
    }
    catch(IOException e) {
		e.printStackTrace();
    }
    catch(Exception e) {
		e.printStackTrace();
    }
  }
  
  public void contextDestroyed(ServletContextEvent sce) {
    System.out.println("helloapp application is Destroyed.");
    ServletContext context = sce.getServletContext();
    Counter counter = (Counter)context.getAttribute("counter");
    if (counter != null) {
      try {
        String filepath = context.getRealPath("/count");
        filepath =filepath + "/count.txt";
        PrintWriter pw = new PrintWriter(filepath);
        pw.println(counter.getCount());
        pw.close();
      }
      catch(IOException e) {
        e.printStackTrace();
      }
      catch(Exception e) {
		e.printStackTrace();
      }
    }
  }
}

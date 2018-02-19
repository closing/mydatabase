<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.Date" %>
<%!
  int globalCounter = 0;
  Date startDate;
  public void jspInit() {
    startDate = new Date();
  }
  public void jspDestroy(){
    ServletContext context = getServletConfig().getServletContext();
    context.log("initdestroy.jsp was visited " + globalCounter + 
                " times between " + startDate + " and " + (new Date()));
  }
%>
<html>
<head><title>A page with a counter</title></head>
<body bgcolor="white">
  This page has been visited: <%= ++globalCounter%> times since <%=startDate%>.
</body>
</html>

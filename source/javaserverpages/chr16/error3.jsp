<%@ page language="java" contentType="text/html;charset=utf-8" %>
<html>
  <head>
    <title>Using a class from the unnamed package</title>
  </head>
  <body bgcolor="white">
    <jsp:useBean id="greeting" class="GreetingBean" />
    <%= greeting.getGreeting() %>
  </body>
</html>

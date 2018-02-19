<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.Date" %>
<html>
  <head>
    <title>Scriptlet instead of expression</title>
  </head>
  <body bgcolor="white">
    Howdy
    <% if (request.getParameter("name") == null) { %>
      stranger!
    <% } else { %>
      partner!
    <% } %>
    It's <% new Date().toString() %> and all is well.
  </body>
</html>

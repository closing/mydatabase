<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
  <head>
    <title>NOT *NSYNC </title>
  </head>
  <body bgcolor="white">
    <jsp:useBean id="artistName" scope="session" class="java.lang.String" />
    <% artistName = "U2"; 
       // request.setAttribute("artistName", artistName);
    %>
    And the winner is... ${fn:escapeXml(artistName)}
  </body>
</html>

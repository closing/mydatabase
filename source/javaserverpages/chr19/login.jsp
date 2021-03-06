<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>Project Billboard</title>
  </head>
  <body bgcolor="white">
    <h1>Welcome to the Project Billboard</h1>
    Your personalized project news web site.
    <p>

    <%-- Error Message --%>
    <font color="red">
      <c:out value="${param.errorMsg}" />
    </font>

    <%-- 
      Create a server-relative path for the form so that it's
      valid even when a protected resource forwards to this page.
    --%>
    <form action="<c:url value="/chr19/authenticate.do" />" method="post">

      <input type="hidden" name="origURL" 
        value="${fn:escapeXml(param.origURL)}">

      Please enter your User Name and Password, and click Enter.
      <p>
      Name: 
      <input name="userName" 
        value="${fn:escapeXml(cookie.userName.value)}" 
        size="10">
      Password: 
      <input type="password" name="password" 
        value="${fn:escapeXml(cookie.password.value)}" 
        size="10">
      <input type="submit" value="Enter">
      <p>
      Remember my name and password:
      <input type="checkbox" name="remember"
        ${!empty cookie.userName ? 'checked' : ''}>
      <br>
      (This feature requires cookies to be enabled in your browser)
    </form>
  </body>
</html>

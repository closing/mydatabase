<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="common.taglibs" %>

<html>
  <head>
    <title>Debug Output</title>
  </head>
  <body bgcolor="white">

    <%-- Add test variables to the request scope --%>
    <c:set var="aString" scope="request" value="Hello World!" />
    <jsp:useBean id="aDate" scope="request" class="java.util.Date" />
    <c:set var="aNumber" scope="request" value="${aDate.minutes}" />

    <h1>Debug Output</h1>

    <cm:debug type="headers" />
    <cm:debug type="cookies" />
    <cm:debug type="params" />
    <cm:debug type="requestScope" />

  </body>
</html>

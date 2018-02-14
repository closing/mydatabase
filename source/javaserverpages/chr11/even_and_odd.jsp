<%@ page contentType="text/html" %>
<%@ taglib prefix="chr11" tagdir="/WEB-INF/tags/mytags/chr11" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Even and Odd Rows</title>
  </head>
  <body bgcolor="white">
    <h1>Even and Odd Rows</h1>
    <table>
      <chr11:forEvenAndOdd items="a,b,c,d,e">
        <jsp:attribute name="even">
          <c:set var="counter" value="${counter + 1}" />
          <tr bgcolor="red"><td>${counter}: Even Row</td></tr>
        </jsp:attribute>
        <jsp:attribute name="odd">
          <c:set var="counter" value="${counter + 1}" />
          <tr bgcolor="blue"><td>${counter}: Odd Row</td></tr>
        </jsp:attribute>
      </chr11:forEvenAndOdd>
    </table>
  </body>
</html>


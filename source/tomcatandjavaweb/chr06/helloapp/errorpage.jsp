<%@page contentType="text/html; charset=UTF-8" %>
<%@page isErrorPage="true" %>
<%@page import="java.io.PrintWriter" %>
<html>
<head><title>Error Page</title></head>
<body>
  <p>
    The parameter(num1=<%=request.getParameter("num1")%>,num2=<%=request.getParameter("num2")%>)
  </p>
  <p>
    Error :<% exception.printStackTrace(new PrintWriter(out));%>
  </p>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.File" %>
<html>
<head><title>visit</title></head>
<body>
  <%!
    File tempDir = null;
    public void jspInit() {
      ServletContext application = getServletConfig().getServletContext();
      tempDir = (File)application.getAttribute("javax.servlet.context.tempdir");
    }
  %>
  The path of work is <%=tempDir.getPath() %>
</body>	
</html>

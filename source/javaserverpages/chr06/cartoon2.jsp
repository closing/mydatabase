<%@ page contentType="text/html;charset=utf-8" %>
<html>
  <head>
    <title>A dose of Dilbert</title>
  </head>
  <body bgcolor="white">
    <h1>A dose of Dilbert</h1>

    <jsp:useBean id="cartoon" 
      class="chr06.CartoonBean" />

    <img src="../images/chr06/${cartoon.fileName}">
    <br>
    FileName:${cartoon.fileName}
  </body>
</html>

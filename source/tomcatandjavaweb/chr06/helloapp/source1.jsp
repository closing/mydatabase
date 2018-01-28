<html>
<head><title>Source Page</title></head>
<body>
  <p>This is output of source.jsp before forward</p>
  <jsp:forward page="target1.jsp">
    <jsp:param name="password" value="123456" />
    <jsp:param name="username" value="TOM" />
  </jsp:forward>
  <p>This is output of source.jsp after forward</p>
</body>
</html>

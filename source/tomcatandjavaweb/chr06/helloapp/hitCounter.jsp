<html>
<head><title>hitCounter</title></head>
<body>
  <H1>
    You hit the page:
    <%! int hitCounter = 1; %>
    <%-- <%
      int count = 0;
      hitCounter = count++;
    %>
    --%>
    <%=hitCounter++ %>
    times
  </H1>
</body>	
</html>

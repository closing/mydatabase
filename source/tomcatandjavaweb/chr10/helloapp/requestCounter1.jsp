<html>
<head>
	<title>Counter</title>
</head>
<body>
	<jsp:useBean id="myPageBean" scope="request" class="mypack.CounterBean" />
	<jsp:setProperty name="myPageBean" property="count" value="<%=myPageBean.getCount() + 1 %>" />
	Current count value is : <jsp:getProperty name="myPageBean" property="count" />
	<%
	String[] scopeNames = {"No scope", "page", "request", "session", "application"};
	int scope = pageContext.getAttributesScope("myPageBean");
	%>
	<p>scope=<%=scopeNames[scope] %></p>
	<jsp:forward page="requestCounter2.jsp" />
</body>
</html>

<html>
<head>
	<title>Counter</title>
</head>
<body>
	<jsp:useBean id="myPageBean" scope="request" class="mypack.CounterBean" />
	Current count value is : <jsp:getProperty name="myPageBean" property="count" />
</body>
</html>

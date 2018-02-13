<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>JSP is Easy</title></head>
<body>
	<h1>JSP is as easy as ...</h1>
	<%-- Calculate the sum of 1 + 2 + 3 dynamiclly --%>
	1 + 2 + 3 = <c:out value="${1 + 2 + 3}" />
</body>
</html>

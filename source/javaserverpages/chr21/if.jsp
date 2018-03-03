<%@ taglib prefix="chr21" uri="chr21.taglib" %>
<html>
    <head>
        <title>IfTag</title>
    </head>
<body>
    <chr21:if test="${param.param2==param.param1}">
        <p>OK</p>
    </chr21:if>
</body>
</html>
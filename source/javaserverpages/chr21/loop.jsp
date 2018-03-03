<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="chr21" uri="chr21.taglib" %>
<html>
    <head>
        <title>Loop Tag</title>
    </head>
<body>
    <ul>
        <chr21:loop items = "${myCollection}" var = "current" >
            <li>
                ${current.lastName}, ${current.firstName}
            </li>
        </chr21:loop>
    </ul>
</body>
</html>
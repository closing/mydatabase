<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
<title>MailLogout</title>
</head>
<body>
    <%
        User user=(User)session.getAttribute("user");
        request.setCharacterEncoding("UTF-8");
        // String username = (String)session.getAttribute("username");
        String username;
        if (user != null) {
            username = user.getName();
        }
        session.invalidate();
    %>
    <%=username %>,再见!
    <p>
    <p>
    <a href="maillogin.jsp">重新登录邮件系统</a>&nbsp;&nbsp;&nbsp; <!-- cookie-->
    <a href="<%=response.encodeURL("maillogin.jsp")%>">重新登录邮件系统</a>&nbsp;&nbsp;&nbsp; <!-- no cookie-->
    
</body>
</html>
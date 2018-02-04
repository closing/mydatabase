<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="mypack.User" %>
<%@ page import="mypack.OnlineUsers" %>
<%@ page import="java.util.List" %>
<html>
<head>
<title>MailCheck</title>
</head>
<body>
    <%
        request.setCharacterEncoding("UTF-8");
        String username = null;
        User user = null;
        username = request.getParameter("username");
        if (username != null) {
            // session.setAttribute("username",username);
            session.setAttribute("user",new User(username));
        } else {
            user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect(response.encodeURL("maillogin.jsp"));
            }
            // username = (String)session.getAttribute("username");
            // if (username == null) {
            //     response.sendRedirect(response.encodeURL("maillogin.jsp"));
            // }
        }
    %>
    <a href="maillogin.jsp">登录</a>&nbsp;&nbsp;&nbsp; <!-- Cookie -->
    <a href="maillogout.jsp">注销</a>&nbsp;&nbsp;&nbsp;
    <a href="<%=response.encodeURL("maillogin.jsp")%>">登录</a>&nbsp;&nbsp;&nbsp; <!-- NO Cookie -->
    <a href="<%=response.encodeURL("maillogout.jsp")%>">注销</a>&nbsp;&nbsp;&nbsp;
    <p>当前用户为:<%=username %></p>
    <p>你的信箱中有100封邮件</p>
    <%
        OnlineUsers onlineUsers = OnlineUsers.getInstance();
        List<String> users = onlineUsers.getUsers();
    %>
    <hr>
    当前在线人数为：<%=onlineUsers.getCount() %><br>
    <%
        for (int i=0; i<onlineUsers.getCount(); i++) {
    %>
            <%=users.get(i) %>&nbsp;&nbsp;
    <%
        }
    %>
    <br>
    <%
        Integer counter = (Integer) application.getAttribute("counter");
        if (counter != null) {
    %>
        当前在线人数：<%=counter %><br>
    <%
        } 
    %>
</body>
</html>
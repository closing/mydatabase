<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="mypack.User" %>
<html>
<head>
<title>MailLogin</title>
</head>
<body bgcolor="#FFFFFF" onLoad="document.loginForm.username.focus()">
    <%
        request.setCharacterEncoding("UTF-8");
        String username = "";
        User user = null;
        if(!session.isNew()) {
            user = (User)session.getAttribute("user");
            if (user == null) {
                username = "";
            } else {
                username = user.getName();
            }
            
            // username = (String)session.getAttribute("username");
            // if (username == null) {
            //     username = "";
            // }
        }
    %>
    <p>欢迎光临邮件系统</p>
    <p>Session ID:<%=session.getId() %></p>

    <table width="500" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
                <table witdh="500" border="0" cellpadding="0" cellspacing="0">
                    <form name="loginForm" method="POST" action="<%=response.encodeURL("mailcheck.jsp") %>">
                        <tr>
                            <td width="401">
                                <div align="rigth">User Name:&nbsp;</div>
                            </td>
                            <td width="399">
                                <input type="text" name="username" value="<%=username%>" />
                            </td>
                        </tr>
                        <tr>
                            <td width="401">
                                <div align="rigth">Password:&nbsp;</div>
                            </td>
                            <td width="399">
                                <input type="password" name="password"  />
                            </td>
                        </tr>
                        <tr>
                            <td width="401">
                                <div align="rigth">&nbsp;</div>
                            </td>
                            <td width="399">
                                <br><input type="submit" name="submit" value="提交" />
                            </td>
                        </tr>
                    </form>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page contentType="text/html;charset=utf8" %>
<html>
<head><title>DbAccess2</title></head>
<body>
<%
try{
    
    Connection conn;
    Statement stmt;
    ResultSet rs;
    
    Context ctx = new InitialContext();
    Context envCtx = (Context)ctx.lookup("java:/comp/env");
    DataSource ds = (DataSource)envCtx.lookup("jdbc/BookDB");
    conn = ds.getConnection();
    
    stmt=conn.createStatement();
%>

<%@ include file="pages.jsp" %>
<%
    stmt.close();
    conn.close();
}
catch(Exception e) {
    out.println(e.toString());

}

%>
</body>
</html>

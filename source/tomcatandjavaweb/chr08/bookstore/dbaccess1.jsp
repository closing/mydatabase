<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page contentType="text/html;charset=utf8" %>
<html>
<head><title>DbAccess1</title></head>
<body>
<%
try{
    
    Connection conn;
    Statement stmt;
    ResultSet rs;
    
    Context ctx = new InitialContext();
    Context envCtx = (Context)ctx.lookup("java:/comp/env");
    DataSource ds = (DataSource)envCtx.lookup("jdbc/BookDB");
    out.println(ds);
    conn = ds.getConnection();
    
    stmt=conn.createStatement();
    stmt.executeUpdate("insert into BOOKS values('999','TOM','Tomcat Bible',65,2006,'Good book',20000)");
    rs =stmt.executeQuery("select ID,NAME,TITLE,PRICE from BOOKS");
    out.println("<table border = '1' width='400'");
    while(rs.next()) {
        String col1 = rs.getString(1);
        String col2 = rs.getString(2);
        String col3 = rs.getString(3);
        float col4 = rs.getFloat(4);
        out.println("<tr><td>" + col1 + "</td><td>"
                               + col2 + "</td<td>"
                               + col3+ "</td<td>"
                               + col4 + "</td</tr>");
    }
    out.println("</table>");

    stmt.executeUpdate("delete from BOOKS where ID = '999'");
    rs.close();
    conn.close();
}
catch(Exception e) {
    out.println(e.toString());

}

%>
</body>
</html>

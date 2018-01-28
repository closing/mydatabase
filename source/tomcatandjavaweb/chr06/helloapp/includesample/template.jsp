<%@page contentType="text/html; charset=UTF-8" %>
<html>
<head><title><%=titlename %></title></head>
<body>
  <table width="100%" height="100%">
    <tr>
      <td width="150" valign="top" align="left" bgcolor="#CCFFCC">
        <table>
          <tr>
            <td witdh="150" height="65" valign="top" align="left">
              <a href="">
                <img src="chinese.gif" border="0" />
			  </a>
			  <a href="">
			    <img src="usa.gif" border="0" />
			  </a>
            </td>
          </tr>
          <tr>
            <td>
              <font size="5">Links</font><p>
              <a href="index.jsp">Home</a><br>
              <a href="product.jsp">Products</a><br>
              <a href="">Hot Link1</a><br>
              <a href="">Hot Link2</a><br>
              <a href="">Hot Link2</a><br>
            </td>
          </tr>
        </table>
      </td>
      <td valign="top" height="100%" witdh="*">
        <table width="100%" height="100%">
          <tr>
            <td valign="top" height="15%">
			  <jsp:include page="header.html" />
            </td>
          </tr>
          <tr>
            <td valign="top">
			  <jsp:include page="<%=bodyfile %>" />
            </td>
          </tr>
          <tr>
            <td valign="bottom" height="15%">
			  <jsp:include page="footer.html" />
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</body>
</html>

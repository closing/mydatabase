<%@ page import="mypack.BookDB" %>
<%@ page import="mypack.BookDetails" %>
<%@ page import="mypack.ShoppingCart" %>
<%@ page import="mypack.ShoppingCartItem" %>

<jsp:userBean id="bookDB" scope="application" class="mypack.BookDB" />

<%!
  public String convert(String s) {
    try {
      return new String(s.getBytes("UTF-8"), "UTF-8");
    } catch (Exception e) {
      return null;
    }
  }
%>
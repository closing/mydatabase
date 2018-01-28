din.jsp is including content.jsp
<% int var = 1;
   request.setAttribute("username", "TOM");
%>
<jsp:include page="content.jsp" flush="true" />
<p>din.jsp is doing something else.</p>

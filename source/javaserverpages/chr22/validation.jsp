<%@ page contentType="text/plain" %>
<%@ taglib prefix="chr22" uri="chr22lib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Correct usage. --%>
<chr22:parent>
  <chr22:child/>
</chr22:parent>

<%-- Incorrect usage. The validator finds and reports these error. --%>
<chr22:child/>
<c:if test="true">
  <chr22:child/>
</c:if>

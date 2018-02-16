<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="common.taglibs" %>

<%-- 
  Terminate the session and redirect to the login page.
--%>
<cm:invalidateSession/>

<c:redirect url="login.jsp" />

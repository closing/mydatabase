<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%-- 
  Execute query, with wildcard characters added to the
  parameter values used in the search criteria
--%>
<sql:query var="empList" scope="request">
  SELECT * FROM employee 
    WHERE firstname LIKE ?
      AND lastname LIKE ?
      AND dept LIKE ?
    ORDER BY lastname
  <sql:param value="%${param.firstName}%" />
  <sql:param value="%${param.lastName}%" />
  <sql:param value="%${param.dept}%" />
</sql:query>

<jsp:forward page="list.jsp" />

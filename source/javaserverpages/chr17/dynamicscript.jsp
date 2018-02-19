<%@ page contentType="application/x-javascript;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<sql:setDataSource var="pizza" 
  driver="org.postgresql.Driver"
  url="jdbc:postgresql://localhost:5432/postgres?currentSchema=jspdb"
  user="postgres"
  password="admin"
/>

<sql:query var="sizes" dataSource="${pizza}">
  SELECT * FROM sizes
</sql:query>

<sql:query var="toppings" dataSource="${pizza}">
  SELECT * FROM toppings
</sql:query>

values = new Array(
  new Array(
    <c:forEach items="${sizes.rows}" var="size" varStatus="s">
      new Array("${fn:escapeXml(size.name)}", "${size.id}")
      <c:if test="${not s.last}">,</c:if>
    </c:forEach>
  ),
  new Array(
    <c:forEach items="${toppings.rows}" var="topping" varStatus="s">
      new Array("${fn:escapeXml(topping.name)}", "${topping.id}")
      <c:if test="${not s.last}">,</c:if>
    </c:forEach>
  )
);

function setList(selectCtrl, itemArray) {
  // Remove current items
  for (i = selectCtrl.options.length; i >= 0; i--) {
    selectCtrl.options[i] = null; 
  }
  for (i = 0; i < itemArray.length; i++) {
    selectCtrl.options[i] = new Option(itemArray[i][0]);
    selectCtrl.options[i].value = itemArray[i][1]; 
  }
}

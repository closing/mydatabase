<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="chr11" tagdir="/WEB-INF/tags/mytags/chr11" %>

<html>
  <head>
    <title>Messages of the Day</title>
  </head>
  <body bgcolor="white">
    <h1>Messages of the Day</h1>
    <h2>Deep Thoughts - by Jack Handey</h2>
    <i>
      <chr11:motd category="thoughts" />
    </i>

    <h2>Quotes From the Famous and the Unknown</h2>
    <i>
      <chr11:motd category="quotes" />
    </i>
  </body>
</html>

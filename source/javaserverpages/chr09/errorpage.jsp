<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="common.taglibs" %>
<%@ page isErrorPage="true" %>

<html>
  <head>
    <title>Sorry</title>
  </head>
  <body bgcolor="white">
    We're sorry but the request could not be processed. 
    Detailed information about the error has been logged so we will
    analyze it and correct whatever is causing it as soon as possible.
    <p>
    Please try again, and 
    <a href="mailto:webmaster@mycompany.com">let us know</a> if the
    problem persists.

    <cm:fileWrite fileName="log">
      Error in: ${pageContext.errorData.requestURI}
      Error message: ${pageContext.errorData.throwable.message}
    </cm:fileWrite>

    <cm:debug type="params" />

  </body>
</html>

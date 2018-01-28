<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>life</title></head>
<body>
  <%!
    private int initVar = 0;
    private int serviceVar = 0;
    private int destroyVar = 0;
  %>
  <%!
    public void jspInit() {
		initVar++;
		System.out.println("jspInit(): JSP init is called " + initVar + " times");
    }
    public void jspDestroy() {
		destroyVar++;
		System.out.println("jspDestroy(): JSP destroy is called " + destroyVar + " times");
    }
  %>
  <%
    serviceVar++;
	System.out.println("_jspService(): JSP service is called " + initVar + " times");
	String content1 = "Init times:" + initVar;
	String content2 = "Service times:" + serviceVar;
	String content3 = "Destroy times:" + destroyVar;
	%>
  <h1><%=content1 %></h1>
  <h1><%=content2 %></h1>
  <h1><%=content3 %></h1>
</body>	
</html>

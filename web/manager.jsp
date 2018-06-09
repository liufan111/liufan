<%--
  Created by IntelliJ IDEA.
  User: zxw
  Date: 17-11-16
  Time: 下午4:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="error.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

    String flag=(String)session.getAttribute("login");
    if(flag==null || !flag.equals("ok"))
    {
        request.getSession().setAttribute("loginError", "请从入口登陆。");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <style type="text/css">
        body {font-size:24pt}
        input {font-size:24pt}
    </style>
</head>
<body>
欢迎，${name} !
<br>
administrator目录：<br>
<a href="manager/manager1.jsp">administrator/administrator1.jsp</a><br>

</body>
</html>


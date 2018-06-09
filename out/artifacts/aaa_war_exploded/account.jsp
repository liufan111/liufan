<%--
  Created by IntelliJ IDEA.
  User: zxw
  Date: 17-11-19
  Time: 下午8:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.regex.Pattern" %>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    if(username.equals("") || password.equals("")){
        session.setAttribute("login_error","用户名和密码不能为空");
        response.sendRedirect("/login.html");
    }
    String match = "^[a-zA-Z0-9]{6,}$";
    Boolean user = Pattern.matches(match,username);
    Boolean pass = Pattern.matches(match,password);
    if(!user || !pass){
        session.setAttribute("login_error","用户名或密码不符合要求！");
        response.sendRedirect("/login.html");
    }
    else{
        response.sendRedirect("/index.html");
    }
%>

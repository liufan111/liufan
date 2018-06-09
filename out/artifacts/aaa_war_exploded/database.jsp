<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="sss.ConnectionManager" %>
<%--
  Created by IntelliJ IDEA.
  User: zxw
  Date: 17-11-8
  Time: 下午9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试数据库</title>
</head>
<body>
<%
    Connection con = ConnectionManager.getInstance().getConnection();
//    Connection con = sss.ConnectionManager.getInstance().getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try
    {
        // 获取所有用户数据
        pstmt = con.prepareStatement("select * from employee");
        rs = pstmt.executeQuery();
        String result;
        while (rs.next())
        {
            result = "";
            result += rs.getInt("emp_id") + " ";
            result += rs.getString("emp_no") + " ";

            System.out.println(result);
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    finally
    {
        ConnectionManager.close(rs, pstmt, con);
    }
%>
</body>
</html>

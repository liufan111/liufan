<%--
  Created by IntelliJ IDEA.
  User: wangjing
  Date: 18-6-9
  Time: 下午10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>售票界面</title>
    <script src="/static/javascript/s_seat.js"></script>
    <link rel="stylesheet" type="text/css" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/studio.css">
    <link rel="stylesheet" type="text/css" href="/static/css/user.css">
    <script src="/static/javascript/studio.js"></script>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/static/javascript/userMessage.js"></script>
    <script src="/static/javascript/readmessge.js"></script>
    <%--<script src="/static/javascript/message.js"></script>--%>
    <link rel="stylesheet" href="/static/css/me.css">
    <script src="/static/javascript/manstu.js"></script>
</head>
<body>
演出厅：<input type="text" id="studio_id" name="studioid" >
演出计划：<input type="text" id="sched_id" name="schedid" >
<input type="button" id="buy_ticket" name="buyticket" value="开始购票" onclick="buy()">
<div class="modal fade" id="error" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="alert alert-warning">
            <%--<a href="#" class="close" data-dismiss="alert">--%>
            <%--&times;--%>
            <%--</a>--%>
            <strong>警告</strong><p id = 'waring'></p>
        </div>
    </div><!-- /.modal -->
</div>
</body>
<script>
    get_user_message('/me.jsp');
</script>
</html>

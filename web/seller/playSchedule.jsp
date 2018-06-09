<%--
  Created by IntelliJ IDEA.
  User: 刘凡
  Date: 2018/6/8
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>剧目的演出计划</title>
    <link rel="stylesheet" type="text/css" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/user.css">
    <script src="/static/javascript/playSchedule.js"></script>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/static/javascript/readmessge.js"></script>
    <script src="/static/javascript/userMessage.js"></script>
    <script src="/static/javascript/message.js"></script>
    <link rel="stylesheet" href="/static/css/me.css">

</head>
<body>
<!--导航-->

<div id="usermessage">
    <div id="header">
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <ul class="nav nav-tabs" id = "navbar">
                    <li class="nav navbar-nav navbar-right" id = "logout"><a href="/out"><span><i class="fa fa-sign-out" aria-hidden="true"></i></span>退出登录</a></li>
                    <li class="nav navbar-nav navbar-right" id = "user"></li>
                    <li class="nav navbar-nav navbar-right" id = "name"></li>
                </ul>

            </div>
        </nav>
    </div>
</div>
<!--显示剧目信息-->
<div>

</div>
<!--显示剧目的演出计划-->
<div id="table">
    <table class="table table-striped" id="Schedule">
        <tr class="warning">
            <td>演出厅</td>
            <td>电影名称</td>
            <td>演出时间</td>
            <td>票价</td>
        </tr>
        <tbody id = "tbody">


        </tbody>
    </table>
</div>
<div style="margin:0px 30px;font-size: 18px;text-align: center">
    <button type = "button" class="btn btn-default navbar-btn"onclick="other_page(-1)"><</button>
    第<u id="now_page">1</u>页

    <button type = "button" class="btn btn-default navbar-btn"onclick="other_page(1)">></button>
    每页显示
    <select class="form-control" id="each_nums" style="width: 100px;display: inline-block"  onchange="reset_get()">
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="15">15</option>
    </select>
</div>
<li id="delete_now" style="display: none"></li>

</body>

<script>
    get_user_message('/me.jsp');
    message();
    //    get_back_entry();
    get_schedule();
</script>
</html>

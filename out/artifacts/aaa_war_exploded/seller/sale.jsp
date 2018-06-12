<%--
  Created by IntelliJ IDEA.
  User: 刘凡
  Date: 2018/6/5
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>售票</title>
    <link rel="stylesheet" type="text/css" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/user.css">
    <script src="/static/javascript/sale.js"></script>
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

<!--热映电影-->
<div id="test">
    <%--<input id="input_play">--%>
    <%--<button id="submit" onclick="window.open('playSchedule.jsp')">--%>
        <%--提交--%>
    <%--</button>--%>
    <%--<img id="play_img" src="/static/image/test_play.jpg" onclick="">--%>
    <img id="play_img" src="/static/image/test_play.jpg" onclick="get_play()">

</div>
<div id="'main">
    <div class="pin" >
        <div class="box">
            <img src = "/Login_ssm_mav_picbyte/user/toLookImage?id=13">
        </div>
    </div>
    <div class="pin" >
        <div class="box">
            <img src = "/Login_ssm_mav_picbyte/user/toLookImage?id=14">
        </div>
    </div>
    <div class="pin" >
        <div class="box">
            <img src = "/Login_ssm_mav_picbyte/user/toLookImage?id=13">
        </div>
    </div>
    <div class="pin" >
        <div class="box">
            <img src = "/Login_ssm_mav_picbyte/user/toLookImage?id=15">
        </div>
    </div>
    <div class="pin">
        <div class="box">
            <img src="/Login_ssm_mav_picbyte/user/toLookImage?id=16">
        </div>
    </div>
    <div class="pin">
        <div class="box">
            <img src="/Login_ssm_mav_picbyte/user/toLookImage?id=17">
        </div>
    </div>



</div>


</body>
<script>
    get_user_message('/me.jsp');
    message();
    //    get_back_entry();

</script>

</html>

<%--
  Created by IntelliJ IDEA.
  User: wyr
  Date: 18-6-6
  Time: 上午10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/error.jsp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>剧目管理</title>
    <link rel="stylesheet" type="text/css" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/play.css">
    <script src="/static/javascript/play.js"></script>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/static/javascript/readmessge.js"></script>
    <script src="/static/javascript/userMessage.js"></script>
    <script src="/static/javascript/message.js"></script>
    <link rel="stylesheet" href="/static/css/me.css">
</head>
<body>

<div id="usermesssage">
    <div id="header">
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <ul class="nav nav-tabs" id="navbar">
                    <li class="nav navbar-nav navbar-right" id="layout"><a href="/out"><span><i class="fa fa-sign-out" aria-hidden="true"></i></span>退出登录</a></li>
                    <li class="nav navbar-nav navbar-right" id="user"></li>
                    <li class="nav navbar-nav navbar-right" id="name"></li>
                </ul>
            </div>
        </nav>
    </div>
</div>

<!--所有剧目-->


<div class="container">
    <div id="play">

    </div>

</div>

<div style="margin: 0px 30px;font-size: 18px;text-align: center ">
    <button type = "button" class="btn btn-default navbar-btn" onclick="other_page(-1)"><</button>
    第<u id="now_page">1</u>页/<sanp id = "pages"></sanp>

    <button type = "button" class="btn btn-default navbar-btn" onclick="other_page(1)">></button>
    每页显示
    <select class="form-control" id="each_nums" style="width: 100px;display: inline-block" onchange="reset_get()" >
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="15">15</option>
    </select>
</div>

<li id="delete_now" style="display: none"></li>

<!--剧目的增查-->
<div style="float: right">
    <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
            <input type="text" class="form-control" id="play_name" placeholder="查找剧目">
        </div>
    </form>
    <button type = "button" class="btn btn-default navbar-btn" onclick="reset_get()"><i class="fa fa-search" aria-hidden="true"></i>查找</button>
    <button type="button" class="btn btn-default navbar-btn" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus" aria-hidden="true"></i> 添加剧目</button>
</div>


<!--添加剧目-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel" style="text-align: center">
                    添加剧目
                </h4>
            </div>
            <div class="modal-body">
                <form role="form">
                    剧目类型：
                    <div class="form-group">
                        <select class="form-control" id="type">
                            <option value="古装剧">古装剧</option>
                            <option value="动漫片">动漫片</option>
                            <option value="生活剧">生活剧</option>
                            <option value="恐怖片">恐怖片</option>
                            <option value="战争片">战争片</option>
                            <option value="科幻片">科幻片</option>
                        </select>
                    </div>
                    语言类型：
                    <div class="form-group">
                        <select class="form-control" id="lang">
                            <option value="国语">国语</option>
                            <option value="英语">英语</option>
                        </select>
                    </div>
                    剧目名称：<input type="text" name="playName" id="playName" class="form-control" placeholder="请输入剧目名称" onblur="check1()" required/><br><span id="err1"></span><br>
                    剧目介绍：<input type="text" name="playIntro" id="playIntro" class="form-control" placeholder="请输入剧目简介"><br>
                    <input type="text" name="playIMage" id="playImage" class="form-control" placeholder="请输入剧目海报路径" value="null" style="display: none"><br>
                    剧目时长：<input type="text" name="playLength" id="playLength" class="form-control" placeholder="请输入剧目时长（分钟）"><br>
                    剧目票价：<input type="text" name="playPrice" id="playPrice" class="form-control" placeholder="请输入剧目票价" onblur="check3()" required/><br><span id="err3"></span><br>
                    剧目状态：<input type="text" name="playState" value="待安排演出" id="playState" class="form-control" ><br>

                </form>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary " onclick="addPlay()" data-dismiss="modal">提交更改</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!--修改剧目信息-->
<div class="modal fade" id="myModals" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="ModalLabel" style="text-align: center">
                    修改剧目信息
                </h4>
            </div>
            <div class="modal-body">
                <form role="form" id="change">
                    剧目名称：<input type="text" name="playName" id="changeplayName" class="form-control" disabled ><br>

                    剧目类型：
                    <div class="form-group">
                        <select class="form-control" id="changetype">
                            <option value="古装剧">古装剧</option>
                            <option value="动漫片">动漫片</option>
                            <option value="生活剧">生活剧</option>
                            <option value="恐怖片">恐怖片</option>
                            <option value="战争片">战争片</option>
                            <option value="科幻片">科幻片</option>
                        </select>
                    </div>
                    语言类型：
                    <div class="form-group">
                        <select class="form-control" id="changelang">
                            <option value="国语">国语</option>
                            <option value="英语">英语</option>
                        </select>
                    </div>
                    剧目介绍：<input type="text" name="playIntro" id="changeplayIntro" class="form-control" ><br>
                    剧目时长：<input type="text" name="playLength" id="changeplayLength" class="form-control" ><br>
                    剧目票价：<input type="text" name="playPrice" id="changeplayPrice" class="form-control" onblur="check5()" required/><br><span id="err5"></span><br>
                    剧目状态：<input type="text" name="playState" value="待安排演出" id="changeplayState"  ><br>

                </form>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary " onclick="putPlay()" data-dismiss="modal">提交更改</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!--修改剧目海报-->
<div class="modal fade" id="play_image" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel1">
                    修改剧目海报
                </h4>
            </div>
            <div class="modal-body">
                <form method="post" action="/head" enctype="multipart/form-data" onload="findId()">
                    <input type="text" id="play_id"  name="play_id" vlaue="" style="display: none">
                    <input id="file" name="head" type="file" >
                    <ul style="color: red" id="file_error"></ul>
                    <input type="submit" value="上传" onclick="get_play()">
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="modal fade" id="error" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
    <div class="alert alert-warning">
        <a href="#" class="close" data-dismiss="alert">
            &times;
        </a>
        <strong>警告</strong><p id = 'waring'></p>
    </div>
</div><!-- /.modal -->
</div>
</body>
<script>
    getPlayCount();
    get_play();
    get_user_message('/me.jsp');
</script>
</html>

<!--<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="/error.jsp"%>-->

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>座位管理</title>
    <link rel="stylesheet" href="/static/css/aboutMe.css">
    <link rel="stylesheet" type="text/css" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/static/css/me.css">
    <script src="/static/javascript/messinfo.js"></script>
    <script src="/static/javascript/readmessge.js"></script>
    <script src="/static/javascript/message.js"></script>
    <script src="/static/javascript/userMessage.js"></script>
</head>
<body>
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
<div class="container" id = "about">
    <div class="row">
        <div class="col-md-3" id = "headPath">

            <div id = "img"></div>
        </div>
        <div class="col-md-9" id = "message">
           <div id = "info">
               <table class="table table-hover" id = "table">
                   <tbody>
                   <tr>
                       <td>员工编号</td>
                   </tr>
                   <tr>
                       <td>员工姓名</td>
                   </tr>
                   <tr>
                       <td>员工住址</td>
                   </tr>
                   <tr>
                       <td>员工邮箱</td>
                   </tr>
                   <tr>
                       <td>联系方式</td>
                   </tr>
                   </tbody>
               </table>
           </div>
        </div>
    </div>
    <!-- 按钮触发模态框 -->
    <button class="btn btn-info btn-lg" data-toggle="modal" data-target="#head_path" onclick="butPath()">
        修改头像
    </button>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="head_path" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel1">
                        修改头像
                    </h4>
                </div>
                <div class="modal-body">
                    <form action="upload.jsp" method="post" enctype="multipart/form-data">

                        上传的图片:
                        <input type="file" name="pic">
                        <input type="submit" value="上传">

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="changePath()">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <!-- 按钮触发模态框 -->
    <button class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal" onclick="butInfo()">
                修改个人信息
    </button>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        修改个人信息
                    </h4>
                </div>
                <div class="modal-body">
                    <form role="form" id = "change">
                        员工编号: <input type="text" name = "emp_no" class="form-control" id = "emp_no" disabled><br>
                        员工姓名: <input type="text" name = "emp_name" class="form-control" id = "emp_name" disabled><br>
                        员工住址: <input type="text" name = "emp_addr" class="form-control" id = "emp_addr" required/><br>
                        员工邮箱: <input type="text" name = "emp_email" class="form-control" id = 'emp_email' required/><br>
                        联系方式: <input type="text" name = "emp_tel" class="form-control" id = 'emp_tel' required/>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="changeInfo()">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</div>
</body>
<script>
    message('./aboutMe.html');
    get_user_message('/me.jsp');
    account();
</script>
</html>

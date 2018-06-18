<%--
  Created by IntelliJ IDEA.
  User: zxw
  Date: 17-11-16
  Time: 下午7:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="../error.jsp"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>演出厅管理</title>
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

<!--导航-->

<div id="usermessage">
    <div id="header">
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <ul class="nav nav-tabs" id = "navbar">
                    <li class="nav navbar-nav navbar-right" id = "logout">
                        <a href=" ">
                        <span><i class="fa fa-sign-out" aria-hidden="true"></i></span>退出登录
                        </a>
                    </li>
                    <li class="nav navbar-nav navbar-right" id = "user"></li>
                    <li class="nav navbar-nav navbar-right" id = "name"></li>
                </ul>

            </div>
        </nav>
    </div>
</div>


<!--所有的演出厅-->
<div id="table">
    <table class="table table-striped table-hover" id = 'studio' onclick="studioRow(this)" >
        <caption style="text-align: center">那一年影院演出厅</caption>
        <tr class="warning" style="text-align: center">
            <td>演出厅名称</td>
            <td>座位行数</td>
            <td>座位列数</td>
            <td>演出厅详情</td>
            <td>演出厅状态</td>
        </tr>
        <tbody id="tbody" style="text-align: center">

        </tbody>
    </table>
    <div id="test1"></div>

</div>
<div style="margin:0px 30px;font-size: 18px;text-align: center">
    <button type = "button" class="btn btn-default navbar-btn"onclick="other_page(-1)"><</button>
    第<u id="now_page">1</u>页/<sanp id = "pages"></sanp>

    <button type = "button" class="btn btn-default navbar-btn"onclick="other_page(1)">></button>
    每页显示
    <select class="form-control" id="each_nums" style="width: 100px;display: inline-block"  onchange="reset_get()">
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="15">15</option>
    </select>

</div>
<li id="delete_now" style="display: none"></li>

<!--演出厅的增删改查以及座位管理-->
<div style="float: right">
    <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
            <input id="studio_name" type="text" class="form-control" placeholder="查找演出厅">
        </div>
    </form>
    <button type = "button" class="btn btn-default navbar-btn" onclick="reset_get()"><i class="fa fa-search" aria-hidden="true"></i>查找</button>
    <button type="button" class="btn btn-default navbar-btn" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus" aria-hidden="true"></i> 添加演出厅</button>
    <button type="button" class="btn btn-default navbar-btn" id = 'deleteStudio' onclick="removeStudio()"><i class="fa fa-minus" aria-hidden="true"></i> 删除演出厅</button>
    <button type="button" class="btn btn-default navbar-btn" id="changeStudio" onclick="change()" data-toggle="modal" data-target="#myModals" ><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 修改演出厅</button>
    <button type="button" class="btn btn-default navbar-btn" id = "seat" onclick="seat()"><i class="fa fa-cog" aria-hidden="true" ></i>座位管理</button>

</div>

<!--演出厅添加信息-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel" style="text-align: center">
                    添加演出厅
                </h4>
            </div>
            <div class="modal-body">
                <form role="form">
                    演出厅名称: <input type="text" name = "studioName" id="studioName" class="form-control" placeholder="请输入演出厅名称" onblur="check()" required/>
                    <span id = 'err'></span>

                    座位行数: <input type="text" name = "sateRow" id="sateRow" class="form-control" placeholder="请输入演出厅中座位的行数" onblur="check1()"required/>
                    <span id = 'err1'></span>

                    座位列数: <input type="text" name = "sateCol" id = "sateCol" class="form-control" placeholder="请输入演出厅的列数" onblur="check2()" required/>
                    <span id = 'err2'></span>

                    演出厅详情: <input type="text" name="studioIntroduction" id = "studioIntroduction" class="form-control" placeholder="请输入演出厅简介"/>


                    <%--演出厅状态: <input type="text" name="studioState" value="可用" id = "studioState" class="form-control" onblur="check3()" required/>
<span id = 'err3'></span>
--%>
                </form>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary " onclick="addStudio()" data-dismiss="modal">提交更改</button>
                </div>
            </div>
        </div>
    </div>
</div>


<!--修改演出厅信息-->
<div class="modal fade" id = "myModals" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="ModalLabel" style="text-align: center">
                    修改演出厅信息
                </h4>
            </div>
            <div class="modal-body">
                <form role="form" id = "change">
                    <%--演出厅id: <input type="text" name = "studioNo" class="form-control" id = "studio_no" disabled>
--%>
                    演出厅名称: <input type="text" name = "studioName"  class="form-control" id = "changeName" onblur="check4()"required/><span id = 'err4'></span>

                    座位行数: <input type="text" name = "sateRow"  class="form-control" id = "changeRow" onblur="check5()" required/><span id = 'err5'></span>

                    座位列数: <input type="text" name = "sateCol"  class="form-control" id = "changeCol" onblur="check6()" required/><span id = 'err6'></span>

                    演出厅状态：<select class="form-control" name="orange" id="changeInt">
                    <option value="normal" id = 'normal'>已生成座位</option>
                    <option value="broken" id = 'broken' >已损坏</option>
                </select>

                    演出厅简介: <input type="text" name="studioIntroduction"  class="form-control" id = 'changeState'/>
                </form>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary " data-dismiss="modal" onclick="putStudio()">提交更改</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="error" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="alert alert-warning">
            <%--< a href="#" class="close" data-dismiss="alert">--%>
            <%--&times;--%>
            <%--</ a>--%>
            <strong>警告</strong><p id = 'waring'></p >
        </div>
    </div><!-- /.modal -->
</div>

</body>
<script>
    getStudioCount();
    get_studio();
    get_user_message('/me.jsp');

</script>
</html>
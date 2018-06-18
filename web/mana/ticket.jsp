<%--
  Created by IntelliJ IDEA.
  User: wangjing
  Date: 18-6-5
  Time: 上午10:23
  To change this template use File | Settings | File Templates
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="false" errorPage="/error.jsp" %>
<html lang="en">
<head>
    <title>票</title>
    <meta http-equiv="Content-Type" content="text/html"; charset="utf-8">
    <link rel="stylesheet" type="text/css" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/user.css">
    <script src="/static/javascript/ticket.js"></script>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/static/javascript/readmessge.js"></script>
    <script src="/static/javascript/userMessage.js"></script>
    <script src="/static/javascript/message.js"></script>
    <link rel="stylesheet" href="/static/css/me.css">
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

<!--票的信息-->
<div id="table">
    <table class="table table-striped" id="loginTicketr" onclick="ticketRow(this)">
        <caption style="...">那一年影院票务</caption>
        <tr class="waring">
            <td>票编号</td>
            <td>座位编号</td>
            <td>演出厅编号</td>
            <td>票价</td>
            <td>票状态</td>
            <td>票的锁定时间</td>
        </tr>
        <tbody id="tbody">

        </tbody>
    </table>
</div>

<div style="...">
    <button class="btn" onclick="other_page(-1)"><</button>
    第<u id="now_page">1</u>页
    <button class="btn" onclick="other_page(1)">></button>
    每页显示
    <select class="form-control" id="each_nums" onchange="reset_get()" style="width: 100px;display: inline-block">
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="15">15</option>
    </select>
</div>
<li id="delete_now" style="display: none"></li>

<!--票的增改删查-->
<div>
    <from class="navbar-form navbar-left" role="search">
        <div class="form-group">
            <input type="text" class="form-control" id = "ticket_no" placeholder="查找票相关信息">
        </div>
    </from>
    <button class="btn btn-default" ><i class="fa fa-search" aria-hidden="true" ></i>查找</button>
    <button class="btn btn-default navbar-btn" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus" aria-hidden="true"></i> 添加可用票</button>
    <button class="btn btn-default navbar-btn" id = 'deleteStudio' onclick="removeTicket()"><i class="fa fa-minus" aria-hidden="true"></i> 删除无用票</button>
    <button class="btn btn-default navbar-btn" id="changeStudio" onclick="change()"  ><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 修改票信息</button>
</div>

<!--添加可用票信息-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel" style="text-align: center">
                    添加可用票信息
                </h4>
            </div>
            <div class="modal-body">
                <form role="form">
                    票编号：<input type="text" name = "ticketid" id="ticket_id" class="form-control"  pattern="^.{2,20}$" oninvalid="setCustomValidity('2-20个字符');"  placeholder="请输入添加的票编号" required/><br>
                    座位编号：<input type="text" name = "seatid" id="seat_id" class="form-control"  pattern="^.{2,20}$" oninvalid="setCustomValidity('2-20个字符');"  placeholder="请输入添加的座位编号" required/><br>
                    演出厅编号：
                    <div class="form-group">
                        <select class="form-control" id = 'num_1'>
                            <option value = 'studio1'>演出厅1</option>
                            <option value = 'studio2'>演出厅2</option>
                            <option value="studio3">演出厅3</option>
                        </select>
                    </div>
                    票价：<input type="text" name = "sateCol" id = "price" class="form-control" placeholder="请输入票价" required/><br>
                    票状态：
                    <div class="form-group">
                        <select class="form-control" id = 'status'>
                            <option value = 'status1'>待售</option>
                            <option value = 'status2'>已售出</option>
                            <option value="status3">锁定</option>
                        </select>
                    </div>
                    票锁定时间：<input type="text" name = "sateCol" id = "lock_time" class="form-control" placeholder="请输入票价" required/><br>
                </form>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary" id = 'add' data-dismiss="modal" onclick="addUser()">提交</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!--修改票信息-->
<div class="modal fade" id="myModals" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="ModalLabel" style="text-align: center">
                    修改票信息
                </h4>
            </div>
            <div class="modal-body">
                <form role="form" id="change">
                    修改票编号：<input type="text" name = "ticketid" id="changeticket_id" class="form-control"  pattern="^.{2,20}$" oninvalid="setCustomValidity('2-20个字符');"  placeholder="请输入添加的票编号" required/><br>
                    修改座位编号：<input type="text" name = "seatid" id="changeseat_id" class="form-control"  pattern="^.{2,20}$" oninvalid="setCustomValidity('2-20个字符');"  placeholder="请输入添加的座位编号" required/><br>
                    修改演出厅编号：
                    <div class="form-group">
                        <select class="form-control" id = 'num_2'>
                            <option value = 'studio1'>演出厅1</option>
                            <option value = 'studio2'>演出厅2</option>
                            <option value="studio3">演出厅3</option>
                        </select>
                    </div>
                    修改票价：<input type="text" name = "sateCol" id = "changeprice" class="form-control" placeholder="请输入票价" required/><br>
                    修改票状态：
                    <div class="form-group">
                        <select class="form-control" id = 'changestatus'>
                            <option value = 'status1'>待售</option>
                            <option value = 'status2'>已售出</option>
                            <option value="status3">锁定</option>
                        </select>
                    </div>
                    修改票锁定时间：<input type="text" name = "sateCol" id = "changelock_time" class="form-control" placeholder="请输入票价" required/><br>
                </form>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary" id = 'changeadd' data-dismiss="modal" onclick="addUser()">提交</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!--报错提示框-->
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
    get_user_message('/me.jsp');
    get_ticket();
</script>
</html>

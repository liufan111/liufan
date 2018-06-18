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
    <title>售票</title>
    <meta http-equiv="Content-Type" content="text/html"; charset="utf-8">
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
    <table class="table table-striped" id="sale" onclick="saleRow(this)">
        <caption style="...">那一年影院票务</caption>
        <tr class="waring">
            <td>销售单编号</td>
            <td>售票员编号</td>
            <td>出售时间</td>
            <td>票价</td>
            <td>找零</td>
            <td>销售单类型</td>
            <%--1,销售单;-1退票单--%>
            <td>销售单状态</td>
            <%--0,待付款;1，已付款--%>
        </tr>
        <tbody id="tbody">

        </tbody>
    </table>
</div>

<div style="...">
    <button class="btn" onclick="other_page(-1)"><</button>
        第<u id="now_page">1</u>页/<sanp id = "pages"></sanp>
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
            <input type="text" class="form-control" id = "sale_no" placeholder="查找销售相关信息">
        </div>
    </from>
    <button class="btn btn-default" ><i class="fa fa-search" aria-hidden="true" onclick="reset_get()"></i>查找</button>
    <button class="btn btn-default navbar-btn" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus" aria-hidden="true"></i> 添加销售信息</button>
    <button class="btn btn-default navbar-btn" id = 'deleteSale' onclick="removeSale()"><i class="fa fa-minus" aria-hidden="true"></i> 删除销售信息</button>
    <button class="btn btn-default navbar-btn" id="changeSale" onclick="change()"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 修改销售信息</button>
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
                    添加售票信息
                </h4>
            </div>
            <div class="modal-body">
                <form role="form">
                    销售单编号：<input type="text" name = "saletid" id="sale_ID" class="form-control"  pattern="^.{2,20}$" oninvalid="setCustomValidity('2-20个字符');"  placeholder="请输入销售单编号" required/><br>
                    售票员编号：<input type="text" name = "empid" id="emp_id" class="form-control"  pattern="^.{2,20}$" oninvalid="setCustomValidity('2-20个字符');"  placeholder="请输入售票员编号" required/><br>
                    售票时间：<input type="text" name = "saletime" id = "sale_time" class="form-control" placeholder="请输入售票时间" required/><br>
                    票价：<input type="text" name = "saleprive" id = "sale_payment" class="form-control" placeholder="请输入票价" required/><br>
                    找零：<input type="text" name = "salechanges" id = "sale_change" class="form-control" placeholder="请输入找零" required/><br>
                    销售单类型：
                    <div class="form-group">
                        <select class="form-control" id = 'sale_type'>
                            <option value = 'type1'>销售单</option>
                            <option value = 'type2'>退票单</option>
                        </select>
                    </div>
                    销售单状态：
                    <div class="form-group">
                        <select class="form-control" id = 'sale_status'>
                            <option value = 'status1'>待付款</option>
                            <option value = 'status2'>已付款</option>
                        </select>
                    </div>
                </form>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary" id = 'add' data-dismiss="modal" onclick="addSale()">提交</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!--修改销售信息-->
<div class="modal fade" id="myModals" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="ModalLabel" style="text-align: center">
                    修改销售信息
                </h4>
            </div>
            <div class="modal-body">
                <form role="form" id="change">
                    修改销售单编号：<input type="text" name = "saleid2" id="a_sale_ID" class="form-control"  pattern="^.{2,20}$" oninvalid="setCustomValidity('2-20个字符');"  placeholder="请输入销售单编号" required/><br>
                    修改售票员编号：<input type="text" name = "empid2" id="a_emp_id" class="form-control"  pattern="^.{2,20}$" oninvalid="setCustomValidity('2-20个字符');"  placeholder="请输入售票员编号" required/><br>
                    修改售票时间：<input type="text" name = "saletime2" id = "a_sale_time" class="form-control" placeholder="请输入售票时间" required/><br>
                    修改票价：<input type="text" name = "saleprice2" id = "a_sale_payment" class="form-control" placeholder="请输入票价" required/><br>
                    修改找零：<input type="text" name = "salechanges2" id = "a_sale_change" class="form-control" placeholder="请输入找零" required/><br>
                    修改销售单类型：
                    <div class="form-group">
                        <select class="form-control" id = 'a_sale_type'>
                            <option value = 'type1'>销售单</option>
                            <option value = 'type2'>退票单</option>
                        </select>
                    </div>
                    修改销售单状态：
                    <div class="form-group">
                        <select class="form-control" id = 'a_sale_status'>
                            <option value = 'status1'>待付款</option>
                            <option value = 'status2'>已付款</option>
                        </select>
                    </div>
                </form>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary" id = 'changeadd' data-dismiss="modal" onclick="updateSale()">提交</button>
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
    getSaleCount();
    get_sale();
</script>
</html>

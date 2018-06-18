<%--
  Created by IntelliJ IDEA.
  User: wyr
  Date: 18-6-14
  Time: 下午7:59
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: minpan
  Date: 2018/6/6
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="utf-8"  isErrorPage="false" errorPage="../error.jsp" contentType="text/html" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>查看演出计划</title>
    <link rel="stylesheet" type="text/css" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <%--<link rel="stylesheet" type="text/css" href="/static/css/schedule.css">--%>
    <link rel="stylesheet" type="text/css" href="/static/css/user.css">
    <script src="/static/javascript/playRoot.js"></script>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/static/javascript/userMessage.js"></script>
    <script src="/static/javascript/readmessge.js"></script>
    <script src="/static/My97DatePicker/WdatePicker.js"></script>
    <%--<script src="/static/javascript/message.js"></script>--%>
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


<!--所有的演出计划-->
<div id="table">
    <table class="table table-striped table-hover" id = 'schedule' onclick="scheduleRow(this)" >
        <caption style="text-align: center">正在上映……</caption>
        <tr class="warning">
            <td>演出计划ID</td>
            <td>演出厅</td>
            <td>剧目名称</td>
            <td>演出时间</td>
            <td>票价</td>
            <td>选座购票</td>
        </tr>
        <tbody id="tbody">

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

<!--演出计划的增删改查-->
<div style="float: right">
    <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
            <input id="play_id" type="text" class="form-control" placeholder="查找剧目演出">
        </div>
    </form>
    <button type = "button" class="btn btn-default navbar-btn" onclick="reset_get()"><i class="fa fa-search" aria-hidden="true"></i>查找</button>
    <%--<button type="button" class="btn btn-default navbar-btn" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus" aria-hidden="true"></i> 添加演出计划</button>--%>
    <%--<button type="button" class="btn btn-default navbar-btn" id = 'deleteSchedule' onclick="removeSchedule()"><i class="fa fa-minus" aria-hidden="true"></i> 删除演出计划</button>--%>
    <%--<button type="button" class="btn btn-default navbar-btn" id="changeSchedule"  onclick="change()"  data-toggle="modal" data-target="#myModals" ><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 修改演出计划</button>--%>


</div>

<!--演出计划添加信息-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel" style="text-align: center">
                    添加演出计划
                </h4>
            </div>
            <div class="modal-body" id="modal-body">
                <form role="form" >
                    <%--剧目名称: <input type="text" name = "studioName" id="studioName" class="form-control" placeholder="请输入演出厅名称" onblur="check()" required/><br><span id = 'err'></span><br>--%>
                    演出厅:
                    <div class="form-group">
                        <select class="form-control"id="studio_name" >
                            <%-- <option>1号厅</option>
                             <option>2号厅</option>--%>
                            <%-- <option>3号厅</option>
                             <option>4号厅</option>--%>
                        </select>
                    </div>

                    剧目名称：
                    <div class="form-group">
                        <select class="form-control" id="play_name" >
                            <%-- <option></option>
                             <option></option>
                             <option></option>
                             <option></option>--%>
                        </select>
                    </div>

                    演出时间:<input id="sched_time" class="Wdate" type="text" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true,minDate:'%y-%M-%d',firstDayOfWeek:1})"><br>
                    <%--<input type="text" class="form-control" name="sched_time" id="sched_time" placeholder="yyyy-MM-dd HH:mm:ss"/><br>--%>
                    <%--票价: <input type="text" name="sched_ticket_price" id = "sched_ticket_price" class="form-control" placeholder="票价"/><br>--%>
                </form>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary " onclick="addSchedule()" data-dismiss="modal">提交更改</button>
                </div>
            </div>
        </div>
    </div>
</div>


<!--修改演出计划-->
<div class="modal fade" id = "myModals" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="ModalLabel" style="text-align: center">
                    修改演出计划
                </h4>
            </div>
            <div class="modal-body">
                <form role="form" id="change" >
                    演出计划id:<input type="text" name = "sched_id" class="form-control" id = "change_id"  disabled ><br>

                    演出厅:
                    <div class="form-group">
                        <select class="form-control" id="change_studio"   >
                            <%--<option>5号厅</option>--%>
                            <%--<option>Your</option>
                            <option>Options</option>
                            <option>Here!</option>--%>
                        </select>
                    </div>
                    剧目名称：
                    <div class="form-group">
                        <select class="form-control" id="change_play">
                            <%--<option>南安那</option>--%>
                            <%--<option>3</option>
                            <option>Options</option>
                            <option>Here!</option>--%>
                        </select>
                    </div>

                    <%--演出时间:<input type="text" class="form-control" name="time" id="change_time" placeholder="yyyy-MM-dd HH:mm:ss:m">--%>
                    演出时间:<input id="change_time" class="Wdate" type="text" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true,minDate:'%y-%M-%d',firstDayOfWeek:1})"><br>
                    票价: <input type="text" name="sched_ticket_price" id = "change_price" class="form-control"  placeholder="票价" disabled/><br>
                </form>

                </form>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary " data-dismiss="modal" onclick="putSchedule()">提交更改</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!--<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#error"></button>-->

<div class="modal fade" id="error" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="alert alert-warning">
            <%--<a href="#" class="close" data-dismiss="alert">
                &times;
            </a>--%>
            <strong>警告</strong><p id = 'waring'></p>
        </div>
    </div><!-- /.modal -->
</div>



</body>
<script>
    /* laydate.render({
         elem: '#time'
         ,type: 'datetime'
     });*/
    get_schedule();
    getScheduleCount();
    get_user_message('/me.jsp');

</script>

</html>

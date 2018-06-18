<!--<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="/error.jsp"%>-->

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ticket</title>
    <meta http-equiv="Content-Type" content="text/html"; charset="utf-8">
    <link rel="stylesheet" type="text/css" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/seat.css">
    <link rel="stylesheet" type="text/css" href="/static/css/user.css">
    <script src="/static/javascript/s_seat.js"></script>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/static/javascript/readmessge.js"></script>
    <script src="/static/javascript/message.js"></script>
    <script src="/static/javascript/userMessage.js"></script>
    <link rel="stylesheet" href="/static/css/me.css">
    <script language="javascript" type="text/javascript" src="/static/javascript/My97DatePicker/WdatePicker.js"></script>
    <style>
        body{margin:0; padding:0;}
        ul#wimoban_nav{padding-left:50px; margin-bottom:10px; border-bottom:2px solid #ccc; overflow:hidden; _zoom:1;}
        ul#wimoban_nav li{float:left; display:inline; margin:10px;}
        ul#wimoban_nav li a{display:block; font-size:16px;}
        ul#wimoban_nav li a,#wimoban_p,#wimoban_p a{color:#000; font-family:"微软雅黑";}
        ul#wimoban_nav li a:hover,#wimoban_p a:hover{color:red;}
        #wimoban_p{text-align:center; font-size:14px; clear:both;}
        /*#myModal{*/
            /*z-index: 1;*/
        /*}*/
        /*#myModal2{*/
            /*z-index: 1000;*/
        /*}*/
    </style>
</head>

<body>

<!--座位管理-->
<jsp:include page="/me.jsp"></jsp:include>
<div>

    <!--对座位的管理操作-->
    <div id = "panter" class="pingmu">
        <div id = "screen">
            <p>屏幕</p>
            <img src="/static/image/rectangle.png" alt="">
        </div>
        <div id = "seat"></div>
    </div>
    <!--图标说明-->
    <div id = "icon">
        <ul>
            <li class="active">图标说明</li>
            <li>待售 <img src="/static/image/blue.png" alt=""></li>
            <li>已售 <img src="/static/image/white.png" alt=""></li>
            <li>锁定 <img src="/static/image/yellow.png" alt=""></li>
            <li>拒售<img src="/static/image/bad.png" alt=""></li>
        </ul>
    </div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" id = 'modal'>
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    生成订单
                </h4>
            </div>
            <div class="modal-body">
                <p id = 'title'></p>
                <form role="form">
                    职员ID<input type="text" id="emp_id" placeholder="请输入员工ID" onblur="" width="150" required/><span id = 'err'></span><br><br>
                    操作日期<input id="d421" class="Wdate" type="text" width="150" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true,minDate:'%y-%M-%d',firstDayOfWeek:1})"><br><br>
                    售出票价<input type="text" width="150" id="sale_payment" placeholder="请输入票价" onblur="" required/><span id = 'err2'></span><br><br>
                    找零<input type="text" width="150" id="sale_change" placeholder="请输入找零" onblur="" required/><span id = 'err3'></span><br><br>
                    <div class="form-group">
                        订单类型<select class="form-control" id ="sale_type">
                            <option value="type1">销售单</option>
                            <option value="type2">退票单</option>
                        </select>
                        订单状态<select class="form-control" id ="sale_status">
                            <option value="status1">待付款</option>
                            <option value="status2">已付款</option>
                        </select>
                        票的状态<select class="form-control" id = 'ticketState'>
                        <option value = 'active'>待售</option>
                        <option value = 'nactive'>已售</option>
                        <option value="lock">锁定</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
                <button type="button" class="btn btn-primary" id = 'sure' data-dismiss="modal">
                    确认
                </button>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    aa();
    //getTicketStatus();
    //createState();
</script>
</html>

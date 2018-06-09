<!--<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="error.jsp"%>-->

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>座位管理</title>
    <!--<link rel="stylesheet" type="text/css" href="./Bootstrap/bootstrap.css">-->
    <link rel="stylesheet" type="text/css" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/seat.css">
    <!--<script src="./jquery/jquery.js"></script>-->
    <script src="/static/javascript/seat.js"></script>
    <!--<script src="./javascript/seatState.js"></script>-->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/static/javascript/readmessge.js"></script>
    <script src="/static/javascript/message.js"></script>
    <script src="/static/javascript/userMessage.js"></script>
    <link rel="stylesheet" href="/static/css/me.css">
</head>
<body>
<!--座位管理-->
<jsp:include page="/me.jsp"></jsp:include>
<div class="row container">
    <!--需要操作的演出厅-->
    <div class="col-md-3">
        <ul class="nav nav-pills nav-stacked" id = "left">
            <%--<li><a href="#">1号演出厅</a></li>--%>
            <%--<li><a href="#">2号演出厅</a></li>--%>
            <%--<li><a href="#">3号演出厅</a></li>--%>
            <%--<li><a href="#">4号演出厅</a></li>--%>
        </ul>
    </div>

    <!--对座位的管理操作-->
    <div class="col-md-6">
        <div id = "screen">
            <p>屏幕</p>
            <img src="/static/image/rectangle.png" alt="">
        </div>
        <div id = "seat" ></div>

    </div>
    <div class="col-md-3">
        <!--图标说明-->
        <div id = "icon">
            <ul>
                <li class="active">图标说明</li>
                <li>选中座位 <img src="/static/image/select.png" alt=""></li>
                <li>座位正常 <img src="/static/image/seat.png" alt=""></li>
                <li>座位坏掉 <img src="/static/image/broken.png" alt=""></li>
                <li>改为过道 <img src="/static/image/aisle.png" alt=""></li>
            </ul>
        </div>
    </div>
</div>


<!--更改座位状态-->

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" id = 'modal'>
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    更改座位状态
                </h4>
            </div>
            <div class="modal-body">
                <p id = 'title'></p>
                <form role="form">
                    <div class="form-group">
                        <select class="form-control" id = 'seatState'>
                            <option value = 'active'>座位正常</option>
                            <option value = 'broken'>座位坏掉</option>
                            <option value = 'aisle'>改为过道</option>
                        </select>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id = 'update' data-dismiss="modal">
                    提交更改
                </button>
            </div>
        </div>
    </div>
</div>


</body>
<script>
    // studioSeat();
    createState()
</script>
</html>

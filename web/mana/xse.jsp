<%--
  Created by IntelliJ IDEA.
  User: sqlness
  Date: 18-6-4
  Time: 下午12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>销售额统计</title>
    <link rel="stylesheet" type="text/css" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/user.css">
    <script src="/static/javascript/employee.js"></script>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/static/BootStrap_DateTime_Picker/css/bootstrap-datetimepicker.min.css">
    <script src="/static/jquery/jquery.js"></script>
    <script src="/static/Bootstrap/bootstrap.js"></script>
    <script src="/static/BootStrap_DateTime_Picker/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/static/BootStrap_DateTime_Picker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/static/javascript/readmessge.js"></script>
    <script src="/static/javascript/message.js"></script>
    <script src="/static/javascript/userMessage.js"></script>
    <script src="/static/javascript/xse.js"></script>
    <script src="/static/javascript/echarts.js"></script>
    <link rel="stylesheet" href="/static/css/me.css">

    <!- #include virtual="./me.html" ->
</head>
<style>
    .tt{
        /*position: absolute;*/

    }
</style>
<body>
<jsp:include page="/me.jsp"></jsp:include>
<body>

<div class="container">

    <h1>统计销售额</h1>

    <div class="row">

        <div class="col-md-3" style="background-color: #dedef8;box-shadow:
         inset 1px -1px 1px #444, inset -1px 1px 1px #444;">
            <h4>过滤器</h4>
            <p>在这里选择要统计的信息</p>

            <form role="form">
                <div class="form-group">
                    <label>影片名</label>
                    <select id="movie" style="width:180px;" class="form-control">
                        <option value="" selected>*</option>
                        <option value="1">狼与野兽</option>
                        <option value="2">神风突击</option>
                        <option value="3">海豹特工</option>
                    </select>
                    <br>
                    <label>售票员</label>
                    <select id="people" style="width:180px;" class="form-control">
                        <option value="0" selected>*</option>
                        <option value="1">张三</option>
                        <option value="2">李四</option>
                        <option value="3">王五</option>
                    </select>
                    <br>
                    <label>时间段</label>
                    <br>
                    <div class="input-group date form_date col-md-8 tt" data-date="" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                        <input onblur="check_time(this)" id="start" class="form-control" type="text" value="" readonly>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    </div>至

                    <div class="input-group date form_date col-md-8" data-date="" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                        <input onblur="check_time(this)" id="end" class="form-control" size="40" type="text" value="" readonly>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    </div>
                    <br>
                    <div style="align-content: right">

                        <button type="button" class="btn btn-default btn-sm" onclick="search()">
                            <span class="glyphicon glyphicon-search"></span> Search
                        </button>
                    </div>
                </div>
            </form>
        </div>

        <div class="col-md-9" style="background-color: #dedef8;box-shadow:
         inset 1px -1px 1px #444, inset -1px 1px 1px #444;">
            <ul id="myTab" class="nav nav-tabs">
                <li class="active"><a href="#bing" data-toggle="tab">影片</a>
                </li>
                <li><a href="#tiao" data-toggle="tab">售票员</a></li>
            </ul>
            <div id="myTabContent" class="tab-content">
                <div class="tab-pane fade in active" style="overflow: hidden" id="bing">

                    <div id="bing1" style="width: 350px;height:367px;float: left"></div>

                    <div id="bing2" style="width: 350px;height:367px;float: left;margin-left: 20px;"></div>
                </div>
                <div class="tab-pane fade" id="tiao">
                    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                    <div id="main" style="width: 600px;height:367px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var User_id;
    var Play_id;

    getpeople();
    getmovie();
    //日期控件
    $('.form_date').datetimepicker({
        format: 'yyyy-mm-dd',
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
    });



</script>
</html>

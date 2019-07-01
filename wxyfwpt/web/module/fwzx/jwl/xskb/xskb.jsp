<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.controller.login.WptUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>js-lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/seclect.css"/>
    <link rel="stylesheet" href="<%=Constant.server_name%>module/fwzx/jwl/xskb/css/xskb.css">
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/alert/common.css"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/Timetable-master/Timetables.min.js"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>

</head>
<body style="background-color: #f3f3f3">
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="titledddiv">
    <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png" onclick="javascript:window.location.replace(document.referrer)"/>

    <p class="titleName">学生课表</p>
</div>
<div class="selectDiv">
    <form class="layui-form" action="">
        <div class="layui-input-block" style="width: 100%;margin: 2% 0%;">
            <select name="interest" id="xskb_zc" lay-filter="xskb_zc">
            </select>
        </div>
    </form>
</div>
<div id="coursesTable"></div>
<%--弹窗--%>
<jsp:include page="/common/alert.jsp"></jsp:include>
</body>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=Constant.server_name%>module/fwzx/jwl/xskb/js/xskb.js"></script>
</html>
<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>js-lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/seclect.css"/>
    <link rel="stylesheet" href="<%=Constant.server_name%>module/fwzx/jwl/jskb/css/jskb.css">
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/alert/common.css"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/Timetable-master/Timetables.min.js"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>

</head>
<body>
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="titledddiv">
    <p class="titleName">查学生课表</p>
</div>
<div class="selectDiv">
    <form class="layui-form">
        <div class="layui-input-block">
            <select name="interest" id="jskb_zc" lay-filter="jskb_zc">
            </select>
        </div>
    </form>
</div>
<div id="coursesTable"></div>
<%--弹窗--%>
<jsp:include page="/common/alert.jsp"></jsp:include>
</body>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=Constant.server_name%>module/fwzx/jwl/jskb/js/jskb.js"></script>
</html>
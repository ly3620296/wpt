<%@ page import="gka.resource.Constant" %>
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
    <link rel="stylesheet" href="<%=Constant.server_name%>module/fwzx/jwl/cdkb/css/cdkb.css">
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/alert/common.css"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/Timetable-master/Timetables.min.js"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>

</head>
<body style="background-color: #f3f3f3">
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="titledddiv">
    <p class="titleName">教室课表</p>
</div>
<div class="selectDiv">
    <div class="layui-form" lay-filter="cdkb_zc">
        <div class="layui-input-block">
            <select name="interest" id="cdkb_zc" lay-filter="cdkb_zc_se">
            </select>
        </div>
    </div>
    <div class="layui-form" lay-filter="cdkb_cdlb">
        <div class="layui-input-block">
            <select name="interest" id="cdkb_cdlb" lay-filter="cdkb_cdlb_se">
                <option value="">请选择（教室类别）</option>
            </select>
        </div>
    </div>
    <div class="layui-form" lay-filter="cdkb_cd">
        <div class="layui-input-block">
            <select name="interest" id="cdkb_cd" lay-filter="cdkb_cd_se">
                <option value="">请选择（教室）</option>
            </select>
        </div>
    </div>
</div>
<div id="coursesTable"></div>
<%--弹窗--%>
<jsp:include page="/common/alert.jsp"></jsp:include>
</body>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=Constant.server_name%>module/fwzx/jwl/cdkb/js/cdkb.js"></script>
</html>
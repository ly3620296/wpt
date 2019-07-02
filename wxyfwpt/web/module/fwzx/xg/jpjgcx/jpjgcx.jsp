<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.controller.login.WptUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>js-lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/seclect.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title></title>

</head>
<body style="background:#f3f3f3">
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
%>
<div class="ccj">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png" onclick="javascript:window.location.replace(document.referrer)"/>
        <p class="titleName">奖评结果查询</p>
    </div>

    <div class="titleccj" style="height: 76px" id="ttt">
        <img class="img" src="<%=Constant.server_name%>img/ccd-jpjgcx.png"/>

        <p style="top:48%"><span>姓名：<%=wptUser.getXm()%></span> <span
                style="margin-left: 5%;">学号：<%=wptUser.getZh()%></span></p>

        <p style="top:77%"><span>专业：<%=wptUser.getZymc()%></span> <span
                style="margin-left: 5%;">班级：<%=wptUser.getBjmc()%></span></p>
    </div>
    <table border="0" cellspacing="" cellpadding="">
        <thead>
        <tr>
            <th>学年学期</th>
            <th>项目名称</th>
            <th>项目金额</th>
            <th>项目类型</th>
        </tr>
        </thead>
        <tbody id="wpt_jpjgcx_table">

        </tbody>
    </table>
</div>

<%--弹窗--%>
<%--<jsp:include page="/common/alert.jsp"></jsp:include>--%>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script src="<%=Constant.server_name%>module/fwzx/xg/jpjgcx/js/jpjgcx.js" charset="utf-8"></script>
</body>
</html>


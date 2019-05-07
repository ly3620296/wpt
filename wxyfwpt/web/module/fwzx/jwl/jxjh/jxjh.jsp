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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/alert/common.css"/>
    <script src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title>教学计划查询</title>

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
        <p class="titleName">教学计划查询</p>
    </div>

    <div class="selectDiv">
        <form class="layui-form">
            <div class="layui-input-block">
                <select name="interest" id="ccj_xq" lay-filter="xnxq_filter">
                </select>
            </div>
        </form>
    </div>
    <div class="titleccj" id="ttt">
        <img class="img" src="<%=Constant.server_name%>img/jxjh-top.jpg"/>

        <p><span>姓名：<%=wptUser.getXm()%></span> <span style="margin-left: 5%;">学号：<%=wptUser.getZh()%></span></p>

    </div>
    <table border="0" cellspacing="" cellpadding="">
        <thead>
        <tr>
            <th colspan="2"><p style="width: 6em">课程名</p></th>
            <th>学分</th>
            <th>学时</th>
            <th>课程性质</th>
            <th colspan="2">考核方式</th>
        </tr>
        </thead>
        <tbody id="wpt_cj_table">

        </tbody>
    </table>
</div>

<%--弹窗--%>
<jsp:include page="/common/alert.jsp"></jsp:include>

<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script src="<%=Constant.server_name%>module/fwzx/jwl/jxjh/js/jxjh.js" charset="utf-8"></script>
</body>
</html>


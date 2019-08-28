<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.controller.login.WptUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/new_app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/swiper.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>js-lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.min.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title>首页</title>
</head>
<body>
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
    String role = wptUser.getJsdm();
    String roleName = "学号";
    if (role == null || "".equals(role)) {
        role = "02";
    }
    if (role.equals("01")) {
        roleName = "工号";
    }
%>
<div class="app">
    <div class="swiper-container swiper-container-horizontal">
        <div class="swiper-wrapper">
            <div class="swiper-slide">
                <a><img src="<%=Constant.server_name%>img/top-app.jpg"/></a>
            </div>
            <div class="swiper-slide">
                <a><img src="<%=Constant.server_name%>img/top-app2.jpg"/></a>
            </div>
            <div class="swiper-slide">
                <a><img src="<%=Constant.server_name%>img/top-app4.jpg"/></a>
            </div>
            <div class="swiper-slide">
                <a><img src="<%=Constant.server_name%>img/top-app5.jpg"/></a>
            </div>
        </div>
        <div class="swiper-pagination"></div>
    </div>

    <div class="MessageBorder">
        <div class="peopleMessage">

            <img class="xing" src="<%=Constant.server_name%>img/people-new.jpg"/>

            <div class="peopleText">
                <p class="p1" style="font-weight: bold;"><%=wptUser.getXm()%>
                </p>

                <p class="p1"><span><%=wptUser.getZymc()%></span> | <span><%=wptUser.getJgmc()%></span></p>

                <p class="num"><%=roleName%>：<%=wptUser.getZh()%>
                </p>
            </div>

        </div>
    </div>


    <div class="tcDiv">

        <div class="tclist">
            <div class="tc">
                <div class="name">
                    <p>我的常用</p>
                    <a class="btnbj" href="javascript:void(0)" id="bj">编辑</a>
                </div>
                <ul id="wpt_main">
                </ul>
            </div>


        </div>
    </div>
    <div style="display: block;">
        <div class="tc">
            <div class="name">
                <p>校内公告</p>
                <a class="btnbj" href="javascript:void(0)" id="gd">更多</a>
            </div>
        </div>
        <ul class="gg-list" id="gg">
            <li id="gg1">
                <p class="title"><i class="fa fa-bullhorn"></i>辽宁机电职业技术学院新生运动服采购成交公告</p>

                <p class="cont">受辽宁机电职业技术学院委托，辽宁金昌建设工程咨询有限公司对辽宁机电职</p>

                <p class="time">2019-07-17</p>
            </li>
            <li id="gg2">
                <p class="title"><i class="fa fa-bullhorn"></i>辽宁机电职业技术学院新生军训服采购成交公告</p>

                <p class="cont">受辽宁机电职业技术学院委托，辽宁金昌建设工程咨询有限公司对辽宁机电</p>

                <p class="time">2019-07-17</p>
            </li>
            <li id="gg3">
                <p class="title"><i class="fa fa-bullhorn"></i>辽宁机电职业技术学院工作服采购成交公告</p>

                <p class="cont">受辽宁机电职业技术学院委托，辽宁金昌建设工程咨询有限公司对辽宁</p>

                <p class="time">2019-07-17</p>
            </li>
        </ul>
    </div>
</div>

<div class="footer-nav" style="display: block;">
    <ul class="footer-menu" id="wpt_foot">
        <li lay-href="module/main/main.jsp">
            <a>
                <i class="fa fa-home  fa-lg "></i>
            </a>

            <p>首页</p>
        </li>
        <li lay-href="module/fwzx/fwzxapp.jsp">
            <a>
                <i class="fa  fa-archive"></i>
            </a>

            <p>服务中心</p>
        </li>
        <li lay-href="module/msg/msg.jsp">
            <i class="fa fa-bell"></i>

            <p>消息</p>
        </li>
        <li lay-href="module/my/my.jsp">
            <i class="fa fa-user"></i>

            <p>我的</p>
        </li>
    </ul>
</div>

<script src="<%=Constant.server_name%>js-lib/swiper/swiper-3.4.2.min.js" type="text/javascript"
        charset="utf-8"></script>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script src="<%=Constant.server_name%>module/main/main.js" type="text/javascript"></script>
</body>
</html>



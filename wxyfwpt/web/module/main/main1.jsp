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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/swiper.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
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
    if (role == null || "".equals(role)) {
        role = "02";
    }
%>
<div class="app">
    <div class="swiper-container swiper-container-horizontal">
        <div class="swiper-wrapper">
            <div class="swiper-slide">
                <a><img src="<%=Constant.server_name%>img/barent2.jpg"/></a>
            </div>

            <div class="swiper-slide">
                <a><img src="<%=Constant.server_name%>img/banner.jpg"/></a>
            </div>

        </div>
        <div class="swiper-pagination"></div>
    </div>


    <div class="peopleMessage">
        <img class="xing" src="<%=Constant.server_name%>img/icon_houqin.png"/>

        <div class="peopleText">
            <p class="p1"><%=wptUser.getXm()%>
            </p>
            <%
                if (role.equals("02")) {
            %>
            <p class="p1"><%=wptUser.getZymc()%>
            </p>
            <%
                }
            %>
            <p class="p2"><%=wptUser.getJgmc()%>
            </p>

            <p class="p2"><%=wptUser.getZh()%>
            </p>
        </div>
    </div>


    <div class="tcDiv">
        <div class="tclist">
            <div class="tc">
                <div class="name">
                    <p>我的常用</p>
                    <a href="javascript:void(0)" class="btnbj" id="bj">编辑</a>
                </div>
                <ul id="wpt_main">

                </ul>
            </div>
        </div>
    </div>


    <div class="tc">
        <div class="name">
            <p>公告</p>
            <a href="javascript:void(0)" class="btnbj" id="gd">更多</a>
        </div>
    </div>
    <ul class="gg-list">
        <li>
            <p class="title">四六级考试</p>

            <p class="cont">四六级考试四六级考试四六级考试四六级考试</p>

            <p class="time">2017-05-02</p>
        </li>
        <li>
            <p class="title">四六级考试</p>

            <p class="cont">四六级考试四六级考试四六级考试四六级考试</p>

            <p class="time">2017-05-02</p>
        </li>
        <li>
            <p class="title">四六级考试</p>

            <p class="cont">四六级考试四六级考试四六级考试四六级考试</p>

            <p class="time">2017-05-02</p>
        </li>
    </ul>

</div>
</div>
<%--<jsp:include page="/common/foot.jsp"></jsp:include>--%>
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
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script src="<%=Constant.server_name%>module/main/main.js" type="text/javascript"></script>

</body>
</html>



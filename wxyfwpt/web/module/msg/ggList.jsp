<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/message.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/seclect.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>module/msg/gg_demo/table.css"/>
    <script src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/swiper/swiper-3.4.2.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title></title>
</head>
<body style="background: #f3f3f3;">
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    String pageSource = request.getParameter("pageSource");
    if (pageSource != null) {
        if (pageSource.equals("main")) {
            pageSource = Constant.server_name + "module/main/main.jsp";
        } else if (pageSource.equals("my")) {
            pageSource = Constant.server_name + "module/my/my.jsp";
        }
    }
%>
<div class="message">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
             onclick="javascript:window.location.replace('<%=pageSource%>')"/>

        <p class="titleName">公告</p>
    </div>
    <div class="messagediv">
        <div class="divggleft">
            <ul>
                <li id="gg1">
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">辽宁机电职业技术学院新生运动服采购成交公告</p>

                        <p class="cont">受辽宁机电职业技术学院委托，辽宁金昌建设工程咨询有限公司对辽宁机电职</p>

                        <p class="time">2019-07-17</p>
                    </div>
                </li>
                <li id="gg2">
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">辽宁机电职业技术学院新生军训服采购成交公告</p>

                        <p class="cont">受辽宁机电职业技术学院委托，辽宁金昌建设工程咨询有限公司对辽宁机电</p>

                        <p class="time">2019-07-17</p>
                    </div>
                </li>
                <li id="gg3">
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">辽宁机电职业技术学院工作服采购成交公告</p>

                        <p class="cont">受辽宁机电职业技术学院委托，辽宁金昌建设工程咨询有限公司对辽宁</p>

                        <p class="time">2019-07-17</p>
                    </div>
                </li>
                <li id="gg4">
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">辽宁机电职业技术学院信息安全等级保护测评项目的采购公告</p>

                        <p class="cont">辽宁金昌建设工程咨询有限公司 受 辽宁机电职业技术学院 委托</p>

                        <p class="time">2019-7-16</p>
                    </div>
                </li>
                <li id="gg5">
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">辽宁机电职业技术学院财务工资模块升级及服务项目采购成交公告</p>

                        <p class="cont">辽宁机电职业技术学院对财务工资模块升级及服务项目进行了竞争性磋商，现将结果公告如下</p>

                        <p class="time">2019-7-12</p>
                    </div>
                </li>
                <li id="gg6">
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">2#、3#宿舍楼生活环境和公共场所升级改造工程招标公告</p>

                        <p class="cont">辽宁金昌建设工程咨询有限公司受辽宁机电职业技术学院的委托</p>

                        <p class="time">2019-7-11</p>
                    </div>
                </li>
                <li id="gg7">
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">辽宁机电职业技术学院2019年学生公寓被褥等床上用品采购结果公告</p>

                        <p class="cont">大连市机电设备招标有限责任公司对辽宁机电职业技术学院2019年学生公寓被</p>

                        <p class="time">2019-7-10</p>
                    </div>
                </li>
                <li id="gg8">
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">辽宁机电职业技术学院财务工资模块升级及服务项目采购公告</p>

                        <p class="cont">辽宁机电职业技术学院对财务工资模块升级及服务项目进行采购，现欢迎</p>

                        <p class="time">2019-7-8</p>
                    </div>
                </li>
                <li id="gg9">
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">辽宁机电职业技术学院工作服采购的采购公告</p>

                        <p class="cont">辽宁金昌建设工程咨询有限公司受辽宁机电职业技术学院委托，对 辽宁</p>

                        <p class="time">2019-7-5</p>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<script>
    $("a").on("click", function () {
        window.location.replace(wpt_serverName + "module/msg/ggDetails.jsp");
    })

    $("#gg1").on("click", function () {
        window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails1.jsp");
    })
    $("#gg2").on("click", function () {
        window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails2.jsp");
    })
    $("#gg3").on("click", function () {
        window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails3.jsp");
    })
    $("#gg4").on("click", function () {
        window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails4.jsp");
    })
    $("#gg5").on("click", function () {
        window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails5.jsp");
    })
    $("#gg6").on("click", function () {
        window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails6.jsp");
    })
    $("#gg7").on("click", function () {
        window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails7.jsp");
    })
    $("#gg8").on("click", function () {
        window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails8.jsp");
    })
    $("#gg9").on("click", function () {
        window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails9.jsp");
    })
</script>
</body>
</html>
<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/my.css"/>

    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <script src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/swiper/swiper-3.4.2.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title>首页</title>

</head>
<body>
<div class="my">
    <div class="head">
        <div class="people">
            <img class="icon" src="<%=Constant.server_name%>img/icon_houqin.png"/>
            <p class="name">李小狗</p>
            <ul class="">
                <li>
                    <p class="p1">周课程</p>
                    <p>15节</p>
                </li>
                <li>
                    <p class="p1">周活动</p>
                    <p>5次</p>
                </li>
                <li>
                    <p class="p1">我的考试</p>
                    <p>55分</p>
                </li>
            </ul>
        </div>
    </div>


    <ul class="tableTabul">
        <li>
            <i class="fa fa-calendar   fa-lg"></i><span>我的课程</span>
        </li>
        <li>
            <i  class="fa fa-bar-chart  fa-lg"></i><span>我的考试</span>
        </li>
        <li>
            <i  class="fa fa-bullhorn   fa-lg"></i><span>我的通知</span>
        </li>
    </ul>
</div>


<div class="footer-nav" style="display: block;">
    <ul class="footer-menu">
        <li>
            <a>
                <i class="fa fa-home  fa-lg active"></i>
            </a>
            <p>首页</p>
        </li>
        <li>
            <a>
                <i class="fa  fa-archive"></i>
            </a>
            <p>服务中心</p>
        </li>
        <li>
            <i class="fa fa-bell"></i>
            <p>消息</p>
        </li>
        <li>
            <i class="fa fa-user"></i>
            <p>我的</p>
        </li>
    </ul>
</div>


<%--<script src="layui/layui.js" charset="utf-8"></script>--%>
<jsp:include page="/common/foot.jsp"></jsp:include>
</body>
</html>
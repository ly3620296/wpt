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
            <ul id="gg">
                <%--<li id="gg1">--%>
                <%--<img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>--%>

                <%--<div class="left">--%>
                <%--<i class="fa fa-bullhorn"></i>--%>
                <%--</div>--%>
                <%--<div class="right">--%>
                <%--<p class="title">辽宁机电职业技术学院新生运动服采购成交公告</p>--%>

                <%--<p class="cont">受辽宁机电职业技术学院委托，辽宁金昌建设工程咨询有限公司对辽宁机电职</p>--%>

                <%--<p class="time">2019-07-17</p>--%>
                <%--</div>--%>
                <%--</li>--%>
            </ul>
        </div>
    </div>
</div>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script src="<%=Constant.server_name%>module/msg/gg.js" type="text/javascript"></script>

</body>
</html>
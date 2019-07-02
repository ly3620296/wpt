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
                <li>
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">四六级考试</p>

                        <p class="time">2017-05-02</p>

                        <p class="time">四六级考试四六级考试四六级考试四六级考试详细信息详细信息详细信息
                            <a href="javascript:void(0)"><span class="more">【详细信息】</span></a></p>

                        <p class="people">李老师</p>
                    </div>
                </li>
                <li>
                    <img class="top-border" src="<%=Constant.server_name%>img/top-border.png"/>

                    <div class="left">
                        <i class="fa fa-bullhorn"></i>
                    </div>
                    <div class="right">
                        <p class="title">四六级考试</p>

                        <p class="time">2017-05-02</p>

                        <p class="time">四六级考试四六级考试四六级考试四六级考试详细信息详细信息详细信息
                            <a href="javascript:void(0)"><span class="more">【详细信息】</span></a></p>

                        <p class="people">李老师</p>
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

</script>
</body>
</html>
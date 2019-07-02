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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <script src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/swiper/swiper-3.4.2.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title>我的消息</title>
</head>
<body style="background-color: #f3f3f3">
<div class="message">
    <ul class="nav">
        <li class="navbarOne liactive">已读消息</li>
        <li class="navbarTwo">未读消息</li>
    </ul>
    <div class="messagediv">
        <div class="divggleft">
            <ul>
                <li>
                    <div class="left">
                        <i class="fa fa-envelope-open-o"></i>
                    </div>
                    <div class="right">
                        <p class="title">四六级考试</p>

                        <p class="time">2017-05-02</p>

                        <p class="time">四六级考试四六级考试四六级考试四六级考试详细信息详细信息详细信息
                            <a href="javascript:void(0)"><span class="more">【详细信息】</span></a></p>
                        <!--<p class="people">李老师</p>-->
                    </div>

                </li>
                <li>
                    <div class="left">
                        <i class="fa fa-envelope-open-o"></i>
                    </div>
                    <div class="right">
                        <p class="title">四六级考试</p>

                        <p class="time">2017-05-02</p>

                        <p class="time">四六级考试四六级考试四六级考试四六级考试详细信息详细信息详细信息
                            <a href="javascript:void(0)"><span class="more">【详细信息】</span></a></p>
                        <!--<p class="people">李老师</p>-->
                    </div>

                </li>

            </ul>
        </div>
        <div class="divggright" style="display: none;">
            <ul>
                <li>
                    <div class="left">
                        <i class="fa fa-envelope-o"></i>
                    </div>
                    <div class="right">
                        <p class="title">四六级考试</p>

                        <p class="time">2017-05-02</p>

                        <p class="time">四六级考试四六级考试四六级考试四六级考试四六级考试四六级考试四六级考试四六级考试四六级考试四六级考试四六级考试四六级考试
                            <a href="javascript:void(0)"><span class="more">【详细信息】</span></a></p>
                    </div>

                </li>
            </ul>
        </div>
    </div>
</div>
<script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    $(function () {
        $(".navbarOne").click(function () {
            $(this).attr("class", "liactive").siblings().removeAttr("class", "liactive")
            $(".divggleft").show();
            $(".divggleft").siblings().hide()
        })
        $(".navbarTwo").click(function () {
            $(this).attr("class", "liactive").siblings().removeAttr("class", "liactive")
            $(".divggright").show();
            $(".divggright").siblings().hide()
        })
        $("a").on("click", function () {
            window.location.replace(wpt_serverName + "module/msg/details.jsp");
        })
    })
</script>
<jsp:include page="/common/foot.jsp"></jsp:include>
</body>
</html>
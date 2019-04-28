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
    <script src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/swiper/swiper-3.4.2.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title>首页</title>

</head>
<body>
<div class="message">
    <ul class="nav">
        <li class="navbarOne liactive">已读消息</li>
        <li class="navbarTwo">未读消息</li>
    </ul>
    <div class="messagediv">
        <div class="divggleft">
            <ul>
                <li>
                    <p class="title">四六级考试</p>
                    <p class="time">2017-05-02</p>
                    <p class="time">四六级考试四六级考试四六级考试四六级考试</p>
                    <p class="people">李老师</p>
                </li>
                <li>
                    <p class="title">四六级考试</p>
                    <p class="time">2017-05-02</p>
                    <p class="time">四六级考试四六级考试四六级考试四六级考试</p>
                    <p class="people">李老师</p>
                </li>
                <li>
                    <p class="title">四六级考试</p>
                    <p class="time">2017-05-02</p>
                    <p class="time">四六级考试四六级考试四六级考试四六级考试</p>
                    <p class="people">李老师</p>
                </li>
            </ul>
        </div>
        <div class="divggright" style="display: none;">
            <ul>
                <li>
                    <p class="title">四六级考试adadadad</p>
                    <p class="time">2017-05-02</p>
                    <p class="time">四六级考试四六级考试四六级考试四六级考试</p>
                    <p class="people">李老师</p>
                </li>
                <li>
                    <p class="title">四六级考试</p>
                    <p class="time">2017-05-02</p>
                    <p class="time">四六级考试四六级考试四六级考试四六级考试</p>
                    <p class="people">李老师</p>
                </li>
                <li>
                    <p class="title">四六级考试</p>
                    <p class="time">2017-05-02</p>
                    <p class="time">四六级考试四六级考试四六级考试四六级考试</p>
                    <p class="people">李老师</p>
                </li>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="/common/foot.jsp"></jsp:include>
<script type="text/javascript">
    $(function(){
        $(".nav li").click(function(){
            $(this).addClass("liactive");
            $(this).siblings().removeClass("liactive")
        })
        $(".navbarOne").click(function(){
            $(".divggleft").show();
            $(".divggleft").siblings().hide()
        })
        $(".navbarTwo").click(function(){
            $(".divggright").show();
            $(".divggright").siblings().hide()
        })
    })
</script>
</body>
</html>
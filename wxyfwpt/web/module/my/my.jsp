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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/my.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <title></title>
</head>
<body style="background: #f3f3f3;">
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
%>
<div class="my">
    <div class="tophref">
        <%--<i class="fa fa-cog fa-lg"></i>--%>
        <%--<i class="fa fa-commenting-o fa-lg"></i>--%>
    </div>
    <div class="first">
        <img class="icon" src="<%=Constant.server_name%>img/wx-top-icon.png"/>

        <div class="right">
            <p class="name"><%=wptUser.getXm()%>
            </p>
        </div>
    </div>
    <div class="head">
        <div class="people">
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
        <ul class="tableTabul" style="margin-top: 0;">
            <li>
                <span>个人信息</span>
                <i class="righticon fa fa-angle-right fa-lg"></i>
            </li>
        </ul>
    </div>

    <ul class="tableTabul">
        <li>
            <span>我的课程</span>
            <i class="righticon fa fa-angle-right fa-lg"></i>
        </li>
        <li>
            <span>我的考试</span>
            <i class="righticon fa fa-angle-right fa-lg"></i>
        </li>
        <li>
            <span>我的申请</span>
            <i class="righticon fa fa-angle-right fa-lg"></i>
        </li>

        <li style="margin-top: 3%;">
            <span>绑定微信</span>
            <i class="righticon fa fa-angle-right fa-lg"></i>
        </li>
        <li>
            <span>公告</span>
            <i class="righticon fa fa-angle-right fa-lg"></i>
        </li>
        <li>
            <span>修改密码</span>
            <i class="righticon fa fa-angle-right fa-lg"></i>
        </li>

        <li style="text-align: center;margin-top: 3%;color: #076cec;">
            <span id="logOut">退出登录</span>
        </li>
    </ul>
</div>
<jsp:include page="/common/foot.jsp"></jsp:include>
</body>

<script type="text/javascript">
    $(function () {
        $("#logOut").on("click", function () {
//            window.location.href = wpt_serverName + "logout";
            window.location.replace(wpt_serverName + "logout")
        })
    })
</script>
</html>





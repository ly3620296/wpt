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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/title.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/gzcx.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>js-lib/layui/css/layui.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
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
<div class="gzcx">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png" onclick="javascript:window.location.replace(document.referrer)"/>
        <p class="titleName">一卡通余额</p>
    </div>

    <div class="divname">
        <img class="leftimg" src="<%=Constant.server_name%>img/icon_houqin.png"/>

        <div class="right">
            <p>姓名：<%=wptUser.getXm()%>
            </p>

            <p>工号：<%=wptUser.getZh()%>
            </p>
        </div>
    </div>

    <p class="price" id="yue">余额0.00元 </p>

    <div class="sflistDiv">
        <p class="time">消费记录</p>

        <div id="xfjl" class="layui-collapse" lay-filter="xfjl" lay-accordion>
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">-15元<span class="time-text">2015-02-02</span></h2>

                <div class="layui-colla-content">
                    <ul class="textlist">

                        <li>
                            <p class="lefttext">商户</p>

                            <p class="righttext">食堂</p>
                        </li>
                        <li>
                            <p class="lefttext">交易类型</p>

                            <p class="righttext">支出</p>
                        </li>
                        <li>
                            <p class="lefttext">余额</p>

                            <p class="righttext">20.00元</p>
                        </li>

                    </ul>
                </div>
            </div>
            <%--<div class="layui-colla-item">--%>
            <%--<h2 class="layui-colla-title">-15元<span class="time-text">2015-02-02</span></h2>--%>

            <%--<div class="layui-colla-content">--%>
            <%--<ul class="textlist">--%>

            <%--<li>--%>
            <%--<p class="lefttext">商户</p>--%>

            <%--<p class="righttext">食堂</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p class="lefttext">交易类型</p>--%>

            <%--<p class="righttext">支出</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p class="lefttext">余额</p>--%>

            <%--<p class="righttext">20.00元</p>--%>
            <%--</li>--%>

            <%--</ul>--%>
            <%--</div>--%>
            <%--</div>--%>

        </div>
    </div>
</div>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script src="<%=Constant.server_name%>module/fwzx/yktl/yktxfjl/js/yktlcx.js" charset="utf-8"></script>
</body>
</html>

































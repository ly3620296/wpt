<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, initial-scale=1.0, user-scalable=no">
    <title>登录</title>
    <%--<link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>--%>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/index.css"/>
</head>

<body class="login-bg">
<div class="login-contain">
    <div class="login-header">
        <p>欢迎登录</p>
    </div>
    <div class="login-logo" align="center">
        <img src="img/bj.jpg" alt="">

        <p>吉林司法警官职业技术学院</p>
    </div>
    <div class="form-group">
        <div class="form-item">
            <label></label>
            <input type="text" id="account" placeholder="学号/工号" value="" maxlength="20"/>
        </div>
        <div class="form-item">
            <label></label>
            <input type="password" id="password" value="" placeholder="密码"/>
        </div>
    </div>
</div>
<div class="button-group">
    <button class="login-btn" id="login">登录</button>
    <p class="righttext" style="text-align: right;margin-top: 15px;color: #ffffff;" id="wjmm">忘记密码？</p>
</div>
</div>
</body>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/wpt/index.js"></script>
</html>
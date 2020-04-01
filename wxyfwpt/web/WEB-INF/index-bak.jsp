<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <style>
        body {
            background: #0b1129;
        }
    </style>
    <title></title>
</head>
<body>
<div class="login" style="position: relative;">
    <img style="width: 100%;" src="<%=Constant.server_name%>img/loginbj-bj.jpg"/>
    <ul>
        <li>
            <input type="text" id="account" placeholder="学号/工号" value="" maxlength="20"/>
        </li>
        <li>

            <input type="password" id="password" value="" placeholder="密码"/>
        </li>

        <li style="text-align: right;">
            <span id="wjmm">忘记密码？</span>

        </li>
        <li>
            <a href="javascript:void(0)" id="login">登录</a>
        </li>
    </ul>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/wpt/index.js"></script>
</body>
</html>

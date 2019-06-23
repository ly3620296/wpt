<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <style>
        body{
            background: #f5f5f7;
        }
    </style>
    <title>身份认证</title>
</head>
<body>
<div class="login">
    <img style="width: 100%;" src="<%=Constant.server_name%>img/shool.jpg"/>
    <ul>
        <li>
            <label for="account">帐号</label>
            <input type="number" id="account" placeholder="学号/工号" value="20183519" maxlength="20"/>
        </li>
        <li>
            <label for="password">密码</label>
            <input type="password" id="password" value="211910" placeholder="密码"/>
        </li>

        <li style="background: #f5f5f7;text-align: right;">
            <span id="wjmm">忘记密码？</span>

        </li>
        <li>
            <a id="login">登录</a>
        </li>
    </ul>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/wpt/index.js"></script>

</body>
</html>

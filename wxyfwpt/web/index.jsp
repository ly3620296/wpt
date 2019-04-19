<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <title></title>
</head>
<body>
<div class="login">
    <img style="width: 100%;" src="<%=Constant.server_name%>img/shool.jpg"/>
    <ul>
        <li>
            <label for="account">帐号</label>
            <input type="number" id="account" placeholder="学号/工号" value="20093518" maxlength="20"/>
        </li>
        <li>
            <label for="password">密码</label>
            <input type="password" id="password" value="" placeholder="密码"/>
        </li>

        <li style="background: #e2e2e2;text-align: right;">
            <span>忘记密码？</span>

        </li>
        <li>
            <a id="login">登录</a>
        </li>
    </ul>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
<script>
    var loginObj = {
        loginInfo: {
            account: "",
            password: ""
        },
        loginValidate: function () {
            this.loginInfo.account = $("#account").val();
            this.loginInfo.password = $("#password").val();
            if (!this.loginInfo.account) {
                layer.tips('请输入账号', '#account', {
                    tips: [1, '#F68A4C'],
                    tipsMore: false,
                    time: 2000
                });
                return false;
            }

            if (!this.loginInfo.password) {
                layer.tips('请输入密码', '#password', {
                    tips: [3, '#F68A4C'],
                    tipsMore: false,
                    time: 2000
                });
                return false;
            }

            return true;
        },
        login: function () {
            var loginRes = this.loginValidate();
            var loadIndex;
            if (loginRes) {
                $.ajax({
                    url: wpt_serverName + "login/loginValidate",
                    type: 'post',
                    dataType: 'json',
                    data: {"account": loginObj.loginInfo.account, "password": loginObj.loginInfo.password},
                    timeout: 10000,
                    beforeSend: function () {
                        loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                    },
                    success: function (data) {
                        if (data) {
                            var code = data.return_code;
                            var msg = data.return_msg;
                            if (code == "0") {
                                window.location.href = wpt_serverName + "/app.html";
                            } else {
                                layer.msg(msg, {anim: 6, time: 2000});
                            }

                        }
                    }
                    ,
                    complete: function () {
                        layer.close(loadIndex);
                    }

                })
            }
        }

    }

    $(function () {
        $("#login").on("click", function () {
            loginObj.login();
        })
    })

</script>
</body>
</html>

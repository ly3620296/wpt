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
                            window.location.href = wpt_serverName + "module/main/main.jsp";
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
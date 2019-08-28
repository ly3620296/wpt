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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/title.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.min.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title></title>
</head>
<body style="background-color: #ececec">
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="titledddiv">
    <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
         onclick="javascript:window.location.replace(document.referrer)"/>

    <p class="titleName">修改密码</p>
</div>
<div class="login loginremmber">
    <ul>
        <li>
            <label for="oldpwd">原密码</label>
            <input type="password" id="oldpwd"/>
        </li>
        <li>
            <label for="newpwd">新密码</label>
            <input type="password" id="newpwd"/>
        </li>
        <li>
            <label for="repwd">再次输入</label>
            <input type="password" id="repwd"/>
        </li>


        <li>
            <a href="javascript:void(0)" id="btn_sure">确定</a>
        </li>
    </ul>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
<script>
    var wpt_passwd = {
        loadIndex: "",
        info: {
            oldpwd: "",
            newpwd: "",
            repwd: ""
        },
        validate: function () {
            var reg2 = /([a-zA-Z0-9!@#$%^&*()_]){8,18}/;
            var reg3 = /[a-zA-Z]+/;
            var reg4 = /[0-9]+/;
            var oldpwd = $("#oldpwd").val();

            if (oldpwd.length < 6) {
                layer.tips('密码长度不能小于6', '#oldpwd', {
                    tips: [1, '#F68A4C'],
                    tipsMore: false,
                    time: 1500
                });
                return false;
            }

            var newpwd = $("#newpwd").val();
            if (newpwd.length < 8) {
                layer.tips('密码长度不能小于8', '#newpwd', {
                    tips: [1, '#F68A4C'],
                    tipsMore: false,
                    time: 1500
                });
                return false;
            }

            if (!reg2.test(newpwd)) {
                layer.tips('长度在8-18位', '#newpwd', {
                    tips: [1, '#F68A4C'],
                    tipsMore: false,
                    time: 1500
                });
                return false;
            } else if (!reg3.test(newpwd)) {
                layer.tips('需含有字母', '#newpwd', {
                    tips: [1, '#F68A4C'],
                    tipsMore: false,
                    time: 1500
                });
                return false;
            } else if (!reg4.test(newpwd)) {
                layer.tips('需含有数字', '#newpwd', {
                    tips: [1, '#F68A4C'],
                    tipsMore: false,
                    time: 1500
                });
                return false;
            }
            var repwd = $("#repwd").val();

            if (repwd != newpwd) {
                layer.tips('两次密码不一致', '#repwd', {
                    tips: [1, '#F68A4C'],
                    tipsMore: false,
                    time: 1500
                });
                return false;
            }
            wpt_passwd.info.newpwd = newpwd;
            wpt_passwd.info.repwd = repwd;
            wpt_passwd.info.oldpwd = oldpwd;
            return true;
        },
        passwd: function () {
            if (!wpt_passwd.validate()) {
                return;
            }

            $.ajax({
                url: wpt_serverName + "passwd/UpdatePasswdCon",
                type: 'post',
                dataType: 'json',
                data: {oldpwd: wpt_passwd.info.oldpwd, newpwd: wpt_passwd.info.newpwd, repwd: wpt_passwd.info.repwd},
                timeout: 10000,
                beforeSend: function () {
                    layer.ready(function () {
                        wpt_passwd.loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                    })
                },
                success: function (data) {
                    if (data) {
                        var code = data.returnInfo.return_code;
                        var msg = data.returnInfo.return_msg;
                        if (code == "0") {
                            layer.msg("修改成功", {anim: 5, time: 2000, icon: 1, shade: [0.2, '#393D49']}, function () {
                                window.location.replace(wpt_serverName + "logout")
                            });
                        } else {
                            layer.msg(msg, {anim: 6, time: 2000});
                        }

                    }
                },
                error: function (data, status) {
                    if (status == "timeout") {
                        layer.msg("请求超时，请稍后重试!", {anim: 6, time: 2000});
                    }

                }
                ,
                complete: function () {
                    layer.close(wpt_passwd.loadIndex);
                }
            })
        }
    }

    $(function () {
        $("#btn_sure").on("click", function () {
            wpt_passwd.passwd();
        })
    })
</script>
</body>
</html>


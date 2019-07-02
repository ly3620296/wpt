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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/common.css"/>
    <title></title>
</head>
<body style="background-color: #ececec;">
<div class="login">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
             onclick="javascript:window.location.replace('/wpt')"/>

        <p class="titleName">忘记密码-step1</p>
    </div>
    <ul>
        <li>
            <label for="name">姓名</label>
            <input type="text" name="" id="name" placeholder="姓名" value="李岩"/>
        </li>
        <li>
            <label for="idCard">身份证</label>
            <input type="text" name="" id="idCard" maxlength="18" placeholder="身份证号" value="220403199004273114"/>
        </li>
        <li>
            <label for="xh">账号</label>
            <input type="number" name="" id="xh" placeholder="学号/工号" value="20173518"/>
        </li>

        <li>
            <a id="btn_confirm" href="javascript:void(0)">确定</a>
        </li>
    </ul>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
<script type="text/javascript">
    var wpt_wjmmqzjy = {
        loadIndex: "",
        info: {
            name: "",
            idCard: "",
            xh: ""
        },
        validate: function () {    //参数校验
            var name = $("#name").val();
            var idCard = $("#idCard").val();
            var xh = $("#xh").val();
            var idCardRegular = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            if (!name) {
                layer.tips('请输入姓名', '#name', {
                    tips: [1, '#F68A4C'],
                    tipsMore: false,
                    time: 1500
                });
                return false;
            }
            if (!idCardRegular.test(idCard)) {
                layer.tips('请输入合法身份证号', '#idCard', {
                    tips: [1, '#F68A4C'],
                    tipsMore: false,
                    time: 1500
                });
                return false;
            }
            if (!xh) {
                layer.tips('请输入学号', '#xh', {
                    tips: [1, '#F68A4C'],
                    tipsMore: false,
                    time: 1500
                });
                return false;
            }
            wpt_wjmmqzjy.info.name = name;
            wpt_wjmmqzjy.info.idCard = idCard;
            wpt_wjmmqzjy.info.xh = xh;
            return true;
        },

        checkInfo: function () {
            if (!wpt_wjmmqzjy.validate()) {
                return;
            }
            $.ajax({
                url: wpt_serverName + "passwd/wjmmValidate",
                type: 'post',
                dataType: 'json',
                data: {name: wpt_wjmmqzjy.info.name, idCard: wpt_wjmmqzjy.info.idCard, xh: wpt_wjmmqzjy.info.xh},
                timeout: 10000,
                beforeSend: function () {
                    layer.ready(function () {
                        wpt_wjmmqzjy.loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                    })
                },
                success: function (data) {
                    if (data) {
                        var code = data.returnInfo.return_code;
                        var msg = data.returnInfo.return_msg;
                        if (code == "0") {
//                            window.location.href = wpt_serverName + "module/wjmm/xgmm.jsp";
                            window.location.replace(wpt_serverName + "module/wjmm/xgmm.jsp");
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
                    layer.close(wpt_wjmmqzjy.loadIndex);
                }
            })

        }
    }

    $(function () {
        $("#btn_confirm").on("click", function () {
            wpt_wjmmqzjy.checkInfo();
        })
    })

</script>
</body>
</html>


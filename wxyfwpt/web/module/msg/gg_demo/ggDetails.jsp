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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/seclect.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="table.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title></title>
</head>
<body>
<%
    String ggId = request.getParameter("ggId");
%>
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="messageMore">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
             onclick="javascript:window.location.replace(document.referrer)"/>

        <p class="titleName">详细信息</p>
    </div>
    <div class="first">
        <p class="titleT" id="t_title"></p>
    </div>
    <div class="contT" id="t_cont">

    </div>
    <div class="footT" id="t_foot">
        <p></p>

        <p></p>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript">
    var wpt_DetailMsg;
    layui.use(['form', 'layer'], function () {
        var form = layui.form;
        var layer = layui.layer;
        var $ = layui.jquery;
        var loadIndex;
        wpt_DetailMsg = {
            loadMsg: function () {
                $.ajax({
                    url: wpt_serverName + "xxts/gg/detail",
                    type: 'post',
                    dataType: 'json',
                    timeout: 10000,
                    data: {ggId: '<%=ggId%>'},
                    beforeSend: function () {
                        layer.ready(function () {
                            loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                        })
                    },
                    success: function (data) {
                        if (data) {
                            var code = data.returnInfo.return_code;
                            var msg = data.returnInfo.return_msg;
                            if (code == "0") {
                                var ggXx = data.ggXx;
                                wpt_DetailMsg.initGg(ggXx);
                            } else {
                                layer.msg(msg, {anim: 6, time: 2000});
                            }
                        }
                    },
                    complete: function () {
                        layer.close(loadIndex);
                    }
                })
            },
            initGg: function (ggXx) {
                $("#t_title").html(ggXx.G_TITLE);
                $("#t_cont").html(ggXx.G_TEXT);
                $("#t_foot").html("<p>" + ggXx.X_NAME + "</p> <p>" + ggXx.G_TIME + "</p>");
            }
        }
        wpt_DetailMsg.loadMsg();
    });
</script>
</html>
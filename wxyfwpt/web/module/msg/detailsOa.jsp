<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/message.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/seclect.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title></title>

</head>
<%
    String xxid = request.getParameter("xxid");
    String type = request.getParameter("channel");
%>
<body style="background-color: #f3f3f3">
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="messageMore">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
             onclick="javascript:window.location.replace(document.referrer)"/>

        <p class="titleName">详细信息</p>
    </div>
    <ul class="listText">
        <li>
            <p class="titleT">[OA]通知</p>

            <p>尊敬的老师、同学您好:</p>

            <div id="ttk">

            </div>
        </li>
    </ul>
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
                    url: wpt_serverName + "xxts/ttk/wdOa",
                    type: 'post',
                    dataType: 'json',
                    timeout: 10000,
                    data: {xxid: '<%=xxid%>'},
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
                                var ttkXx = data.ttkXx;
                                wpt_DetailMsg.initTtk(ttkXx);
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
            getTtk: function () {
                $.ajax({
                    url: wpt_serverName + "xxts/ttk/ydOa",
                    type: 'post',
                    dataType: 'json',
                    timeout: 10000,
                    data: {xxid: '<%=xxid%>'},
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
                                var ttkXx = data.ttkXx;
                                wpt_DetailMsg.initTtk(ttkXx)

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
            initTtk: function (ttkXx) {
                var xx = '<p style="text-indent: 2em;" >' + ttkXx.CONTENT + '</p>' +
                        '<p class="timeT">' + ttkXx.TIME + '</p>';
                $("#ttk").html(xx);
            }

        }
        var type = '<%=type%>';
        if (type == "0") {
            wpt_DetailMsg.loadMsg();
        } else {
            wpt_DetailMsg.getTtk();
        }
    })
</script>
</html>
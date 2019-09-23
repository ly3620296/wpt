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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>js-lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title>我的消息</title>
</head>
<body style="background-color: #f3f3f3">
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="message">
    <ul class="nav">
        <li class="navbarOne">已读消息</li>
        <li class="navbarTwo liactive">未读消息</li>
    </ul>
    <div class="messagediv">
        <div class="divggleft" style="display: none;">
            <ul id="yd">

            </ul>
        </div>
        <div class="divggright">
            <ul id="wd">

                </li>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="/common/foot_nojq.jsp"></jsp:include>
</body>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript">

    var wpt_msg;
    layui.use(['form', 'element', 'layer'], function () {
        var form = layui.form;
        var layer = layui.layer;
        var $ = layui.jquery;
        var loadIndex;
        wpt_msg = {
            myHref: function (type, ttkxxid, xxType) {
                if (xxType == 0) {
                    window.location.replace(wpt_serverName + 'module/msg/details.jsp?channel=' + type + '&ttkxxid=' + ttkxxid);
                }
                else if (xxType == 1) {
                    window.location.replace(wpt_serverName + 'module/msg/detailsTsjy.jsp?channel=' + type + '&tsjyxxid=' + ttkxxid);
                }
            },
            initFoot: function () {
                $("#wpt_foot>li").on("click", function () {
                    var my_li = $(this);
                    var lay_href = my_li.attr("lay-href");
                    if (lay_href) {
                        window.location.replace(wpt_serverName + lay_href);
                    }
                })
                var url = window.location.href;
                if (url.search("module/fwzx/fwzxapp.jsp") != -1) {
                    $(".footer-menu li").eq(1).find("i").addClass("active");
                } else if (url.search("module/msg/msg.jsp") != -1) {
                    $(".footer-menu li").eq(2).find("i").addClass("active");
                } else if (url.search("module/my/my.jsp") != -1) {
                    $(".fa-user").addClass("active");
                } else {
                    $(".footer-menu li").eq(0).find("i").addClass("active");
                }
            },
            initClick: function () {
                $(".navbarOne").click(function () {
                    $(this).attr("class", "liactive").siblings().removeAttr("class", "liactive")
                    $(".divggleft").show();
                    $(".divggleft").siblings().hide()
                })
                $(".navbarTwo").click(function () {
                    $(this).attr("class", "liactive").siblings().removeAttr("class", "liactive")
                    $(".divggright").show();
                    $(".divggright").siblings().hide()
                })
            },
            loadMsg: function () {
                $.ajax({
                    url: wpt_serverName + "xxts/ttk",
                    type: 'post',
                    dataType: 'json',
                    timeout: 10000,
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
                                wpt_msg.initMsg(data.ttkXxList);
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
            initMsg: function (ttkXxList) {
                var ydHtml = "";
                var wdHtml = "";
                for (var i in ttkXxList) {
                    var ttkxx = ttkXxList[i];
                    var ydOrwd = ttkxx.V_STATUS;
                    if (ydOrwd == "0") {
                        wdHtml += '<li>' +
                        '<div class="left">' +
                        '<i class="fa fa-envelope-o"></i>' +
                        '</div>' +
                        '<div class="right">' +
                        '<p class="title">' + (ttkxx.V_TYPE == 0 ? '调停课通知' : '图书归还通知') + '</p>' +
                        '<p class="time">' + ttkxx.KCMC +
                        '<a href="javascript:void(0)"  onclick= "wpt_msg.myHref(0,\'' + ttkxx.TTKXX_ID + '\',\'' + ttkxx.V_TYPE + '\')"><span class="more">【详细信息】</span></a>' +
                        '<span>' + ttkxx.D_TIPS_DATE + '</span></p>' +
                        '</div>' +
                        '</li>';
                    } else if (ydOrwd == "1") {
                        ydHtml += '<li>' +
                        '<div class="left">' +
                        '<i class="fa fa-envelope-open-o"></i>' +
                        '</div>' +
                        '<div class="right">' +
                        '<p class="title">' + (ttkxx.V_TYPE == 0 ? '调停课通知' : '图书归还通知') + '</p>' +
                        '<p class="time">' + ttkxx.KCMC +
                        '<a href="javascript:void(0)" onclick= "wpt_msg.myHref(1,\'' + ttkxx.TTKXX_ID + '\',\'' + ttkxx.V_TYPE + '\')"><span class="more">【详细信息】</span></a>' +
                        '<span>' + ttkxx.D_TIPS_DATE + '</span></p>' +
                        '</div>' +
                        '</li>';
                    }
                }

                $("#yd").html(ydHtml);
                $("#wd").html(wdHtml);
            }
        }
        wpt_msg.initFoot();
        wpt_msg.loadMsg();
        wpt_msg.initClick();

    })
</script>
</html>
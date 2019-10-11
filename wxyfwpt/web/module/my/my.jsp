<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.controller.login.WptUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/my.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <title></title>
</head>
<body style="background: #f3f3f3;">
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
    String openId = wptUser.getOpenId();
    if (openId == null) {
        openId = "";
    }
%>
<div class="my">
    <div class="tophref">
        <%--<i class="fa fa-cog fa-lg"></i>--%>
        <%--<i class="fa fa-commenting-o fa-lg"></i>--%>
    </div>
    <div class="first">
        <img class="icon" src="<%=Constant.server_name%>img/wx-top-icon.png"/>

        <div class="right">
            <p class="name"><%=wptUser.getXm()%>
            </p>
        </div>
    </div>
    <div class="head">
        <div class="people">
            <ul class="">
                <%--<li>--%>
                <%--<p class="p1">周课程</p>--%>

                <%--<p>15节</p>--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--<p class="p1">周活动</p>--%>

                <%--<p>5次</p>--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--<p class="p1">我的考试</p>--%>

                <%--<p>55分</p>--%>
                <%--</li>--%>

                <li>
                    <p class="p1"></p>

                    <p></p>
                </li>
                <li>
                    <p class="p1"></p>

                    <p></p>
                </li>
                <li>
                    <p class="p1"></p>

                    <p></p>
                </li>
            </ul>
        </div>
        <ul class="tableTabul" style="margin-top: 0;" id="grxx">
            <li>
                <span>个人信息</span>
                <i class="righticon fa fa-angle-right fa-lg"></i>
            </li>
        </ul>
    </div>

    <ul class="tableTabul">
        <%--<li>--%>
        <%--<span>我的课程</span>--%>
        <%--<i class="righticon fa fa-angle-right fa-lg"></i>--%>
        <%--</li>--%>
        <%--<li>--%>
        <%--<span>我的考试</span>--%>
        <%--<i class="righticon fa fa-angle-right fa-lg"></i>--%>
        <%--</li>--%>
        <%--<li>--%>
        <%--<span>我的申请</span>--%>
        <%--<i class="righticon fa fa-angle-right fa-lg"></i>--%>
        <%--</li>--%>
        <li style="margin-top: 3%;display: none" id="bd">
            <span>绑定微信</span>
            <i class="righticon fa fa-angle-right fa-lg"></i>
        </li>
        <li style="margin-top: 3%; display: none" id="jcbd">
            <span>解除绑定</span>
            <i class="righticon fa fa-angle-right fa-lg"></i>
        </li>
        <%--<li id="gg">--%>
            <%--<span>公告</span>--%>
            <%--<i class="righticon fa fa-angle-right fa-lg"></i>--%>
        <%--</li>--%>
        <li id="xgmm">
            <span>修改密码</span>
            <i class="righticon fa fa-angle-right fa-lg"></i>
        </li>

        <li style="text-align: center;margin-top: 3%;color: #076cec;">
            <span id="logOut">退出登录</span>
        </li>
    </ul>
</div>
<jsp:include page="/common/foot_nojq.jsp"></jsp:include>
</body>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript">
    var wpt_my;
    layui.use('form', function () {
        var layer = layui.layer;
        var $ = layui.jquery;
        var loadIndex;
        wpt_my = {
            init: function () {
                if ('<%=openId%>' == "") {
                    $("#bd").show();
                    $("#jcbd").hide();
                } else {
                    $("#bd").hide();
                    $("#jcbd").show();
                }
            },
            bindFoot: function () {
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
            bindCommon: function () {
                $("#logOut").on("click", function () {
                    window.location.replace(wpt_serverName + "logout?channel=wx")
                })
                $("#grxx").on("click", function () {
                    window.location.replace(wpt_serverName + "module/my/grxx/grxx.jsp")
                })
                $("#gg").on("click", function () {
                    window.location.replace(wpt_serverName + "module/msg/ggList.jsp?pageSource=my");
                })

                $("#xgmm").on("click", function () {
                    window.location.replace(wpt_serverName + "module/wjmm/wdxgmm.jsp");
                })
                $("#jcbd").on("click", function () {
                    wpt_my.bindOrUnbindOpenId("unbind");
                })
                $("#bd").on("click", function () {
                    wpt_my.bindOrUnbindOpenId("bind");
                })
            },

            bindOrUnbindOpenId: function (type) {
                $.ajax({
                    url: wpt_serverName + type,
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
                                if (type == "bind") {
                                    $("#bd").hide();
                                    $("#jcbd").show();
                                    layer.msg("绑定成功", {time: 1000});
                                } else {
                                    $("#bd").show();
                                    $("#jcbd").hide();
                                    layer.msg("解除绑定", {time: 1000});
                                }

                            } else {
                                layer.msg(msg, {anim: 6, time: 2000});
                            }
                        }
                    },
                    error: function () {
                    },
                    complete: function () {
                        layer.close(loadIndex);
                    }
                })
            }
        }
        wpt_my.init();
        wpt_my.bindFoot();
        wpt_my.bindCommon();
    })
</script>
</html>





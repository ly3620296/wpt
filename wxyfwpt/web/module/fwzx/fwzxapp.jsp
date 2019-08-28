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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/swiper.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title>服务中心</title>
</head>
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
    String role = wptUser.getJsdm();
    if (role == null || "".equals(role)) {
        role = "01";
    }
%>
<body>
<div class="app">
    <div class="tcDiv">
        <div class="tclist" id="tc_list">

        </div>
    </div>
</div>
<div class="footer-nav" style="display: block;">
    <ul class="footer-menu" id="wpt_foot">
        <li lay-href="module/main/main.jsp">
            <a>
                <i class="fa fa-home  fa-lg "></i>
            </a>

            <p>首页</p>
        </li>
        <li lay-href="module/fwzx/fwzxapp.jsp">
            <a>
                <i class="fa  fa-archive"></i>
            </a>

            <p>服务中心</p>
        </li>
        <li lay-href="module/msg/msg.jsp">
            <i class="fa fa-bell"></i>

            <p>消息</p>
        </li>
        <li lay-href="module/my/my.jsp">
            <i class="fa fa-user"></i>

            <p>我的</p>
        </li>
    </ul>
</div>
</body>
</html>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script>
    var wpt_fwzx;
    layui.use('form', function () {
        var loadIndex;
        var layer = layui.layer;
        var $ = layui.jquery;
        wpt_fwzx = {
            bindLi: function () {
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
            initFwzx: function () {
                $.ajax({
                    url: wpt_serverName + "fwzx",
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
                            if (code == "0000") {
                                var menuParents = data.menuParents;
                                var menuHtml = "";
                                for (var parIndex in menuParents) {
                                    var menuParent = menuParents[parIndex];
                                    var htmlPar = '<div class="tc">' +
                                            '<div class="name">' +
                                            '<p>' + menuParent.menu_name + '</p>' +
                                            '</div>';
                                    var menuChilds = menuParent.menuChildList;
                                    var htmlChild = '<ul>';
                                    for (var childIndex in menuChilds) {
                                        var menuChild = menuChilds[childIndex];

                                        var menu_url = menuChild.menu_url;

                                        var menu_img = menuChild.menu_img;
                                        if (menu_img) {
                                            menu_img = wpt_serverName + menu_img;
                                        }
                                        htmlChild += '<li lay-href="' + menu_url + '">' +
                                        '<img src="' + menu_img + '">' +
                                        '<p>' + menuChild.menu_name + '</p>' +
                                        '</li>';
                                    }
                                    if (menuParent.menuChildList.length == 0) {
                                        htmlChild += '<li>' +
                                        '<img src="' + wpt_serverName + 'img/default.png">' +
                                        '<p></p>' +
                                        '</li>';
                                    }
                                    var htmlChildEnd = '</ul>';
                                    var htmlParEnd = "</div>";
                                    menuHtml = menuHtml + htmlPar + htmlChild + htmlChildEnd + htmlParEnd;
                                }
                                $("#tc_list").html(menuHtml);
                                wpt_fwzx.bindLay();
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
            bindLay: function () {
                $("#tc_list ul>li").on("click", function () {
                    var my_li = $(this);
                    var lay_href = my_li.attr("lay-href");
                    if (lay_href) {
                        if (window.location.pathname == wpt_serverName + "module/fwzx/fwzxapp.jsp") {
                            window.location.replace(wpt_serverName + lay_href + "?pageSource=fwzx");
                        } else {
                            window.location.replace(wpt_serverName + lay_href);
                        }
                    }
                })
            }
        }
        wpt_fwzx.bindLi();
        wpt_fwzx.initFwzx();
    })
</script>



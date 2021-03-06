<%@ page import="gka.controller.login.WptUserInfo" %>
<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="my.css"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <title></title>
</head>
<body>
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
%>
<div class="peopleMessage">
    <div class="topimgdiv">
        <img class="topimg" src="<%=Constant.server_name%>img/grxx-xqtop.png">
        <img class="peopleimg" src="<%=Constant.server_name%>img/wx-top-icon.png">

        <p class="name"><%=wptUser.getXm()%>
        </p>

        <p class="number">账号:<%=wptUser.getZh()%>
        </p>

        <p class="card"><%=wptUser.getZzmm()%>
        </p>

        <ul>
            <li>
                <p>出生日期</p>

                <p><%=wptUser.getCsrq()%>
                </p>
            </li>
            <li>
                <p>性别</p>

                <p><%=wptUser.getXb()%>
                </p>
            </li>
            <li>
                <p class="p1">民族</p>

                <p class="p2"><%=wptUser.getMz()%>
                </p>
            </li>
        </ul>
    </div>

    <div class="listdiv">
        <p class="nametitle">个人信息</p>
        <ul>
            <li>证件类型：<%=wptUser.getZjlx()%>
            </li>
            <li>证件号码：<%=wptUser.getZjhm()%>
            </li>
            <li>联系电话：<%=wptUser.getLxdh()%>
            </li>
            <li>邮箱地址：<%=wptUser.getYx()%>
            </li>
            <li id="szqs">
            </li>
        </ul>
    </div>

    <div class="listdiv">
        <p class="nametitle">学籍信息</p>
        <ul>
            <li>机构名称：<%=wptUser.getJgmc()%>
            </li>
            <li>专业名称：<%=wptUser.getZymc()%>
            </li>
            <li>班级名称：<%=wptUser.getBjmc()%>
            </li>
            <li>年纪名称：<%=wptUser.getNjmc()%>
            </li>
            <li>学&#12288;&#12288;制：<%=wptUser.getXz()%>
            </li>
            <li>是否在校：<%=wptUser.getSfzx()%>
            </li>
            <li>学籍状态：<%=wptUser.getXjzt()%>
            </li>
            <li>报道注册标记：<%=wptUser.getBdzc()%>
            </li>
            <li>专业方向名称：<%=wptUser.getZyfxmc()%>
            </li>
            <li>专业方向名称：<%=wptUser.getZyfxmc()%>
            </li>
        </ul>
    </div>
</div>
<a class="tijiao" href="javascript:void(0)" id="back">返回</a>
</body>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript">
    var wpt_my;
    layui.use('form', function () {
        var layer = layui.layer;
        var $ = layui.jquery;
        var loadIndex;
        wpt_my = {
            initGrxx: function () {
                $.ajax({
                    url: wpt_serverName + "my/grxx",
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
                                var ldmc = data.ld.LDMC == null ? "" : data.ld.LDMC;
                                var qsh = data.ld.QSH == null ? "" : data.ld.QSH + "室";
                                var cwh = data.ld.CWH == null ? "" : data.ld.CWH + "号床";
                                $("#szqs").html("所在寝室：" + ldmc + qsh + cwh)
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
            },
            bindCli: function () {
                $("#back").on("click", function () {
                    window.location.replace(wpt_serverName + "module/my/my.jsp")
                })
            }
        }

        wpt_my.initGrxx();
        wpt_my.bindCli();
    })
</script>
</html>

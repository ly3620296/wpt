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
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/title.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/gzcx.css"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title></title>
    <style type="text/css">
        .layui-layer-loading0 {
            margin-left: 45%;
        }
    </style>
</head>
<body style="background: #f3f3f3;">
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
    String pageSource = request.getParameter("pageSource");
    if (pageSource != null) {
        if (pageSource.equals("fwzx")) {
            pageSource = Constant.server_name + "module/fwzx/fwzxapp.jsp";
        } else if (pageSource.equals("main")) {
            pageSource = Constant.server_name + "module/main/main.jsp";
        }

    }
%>
<div class="gzcx">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
             onclick="javascript:window.location.replace('<%=pageSource%>')"/>

        <p class="titleName">工资查询</p>
    </div>
    <div class="divname">
        <img class="leftimg" src="<%=Constant.server_name%>img/icon_jiaofei.png"/>

        <div class="right">
            <p>姓名：<%=wptUser.getXm()%>
            </p>

            <p>工号：<%=wptUser.getZh()%>
            </p>
        </div>
    </div>
    <div class="sflistDiv">
        <p class="time">工资条</p>

        <div class="layui-collapse" lay-accordion="" id="myMsg">

        </div>
    </div>
</div>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use(['element', 'layer'], function () {
        var loadIndex;
        var element = layui.element;
        var layer = layui.layer;
        var zh = "<%=wptUser.getZh()%>"
        if (zh == undefined || zh == "" || zh == null) {
            layer.alert('未获取到账号信息!', function (index) {
                layer.closeAll()
            });
            return false;
        }
        $.ajax({
            url: wpt_serverName + "gzcx/list",
            type: 'post',
            dataType: 'json',
            data: {zh: zh},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    var list = data.OUT_DATA.list;
                    if (list.length > 0) {
                        var html = '';
                        for (var i = 0; i < list.length; i++) {
                            html += '<div class="layui-colla-item"> ' +
                            '<a href="table.jsp?id=' + list[i].ID + '"><h2 class="layui-colla-title">' + list[i].BT + '</h2></a> </div>'
                        }
                        $("#myMsg").html(html)
                    } else {
                        layer.alert("暂无您的工资信息!", function (index) {
                            layer.closeAll()
                        });
                    }
                } else {
                    layer.alert(data.RETURN_MSG, function (index) {
                        layer.closeAll()
                    });
                }
            },
            complete: function () {
                layer.close(loadIndex);
            }
        })
    });
</script>
</body>

</html>







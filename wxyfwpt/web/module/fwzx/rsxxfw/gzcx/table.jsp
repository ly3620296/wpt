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
        .tablelist{
            width: 100%;

        }
        .tablelist td{
            font-size: 14px;
            text-align: center;
            padding: 8px 0;
            background: #ffffff;
            border-bottom: 2px solid #e4eff5;
        }
        .tablelist td:first-child{
            text-align: center;
            /* text-indent: 2em; */
            width: 40%;
            background: #e9f8ff;
            border-bottom: 2px solid #fff;
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
%>
<div class="gzcx">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
             onclick="javascript:window.location.replace(document.referrer)"/>

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
        <p class="time">工资条明细</p>

        <div class="layui-collapse" lay-accordion="" id="myMsg">
            <table border="1" cellspacing="1" cellpadding="1" id="table" class="tablelist">

            </table>
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
        var id = getURLParameter("id");
        if (id == undefined || id == "" || id == null) {
            layer.alert('未获取到ID!', function (index) {
                parent.closeAll()
            });
            return false;
        }
        $.ajax({
            url: wpt_serverName + "gzcx/table",
            type: 'post',
            dataType: 'json',
            data: {id: id, zh: zh},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    var headList = data.OUT_DATA.headList;
                    var bodyList = data.OUT_DATA.bodyList;
                    var html = '';
                    if (headList != null && bodyList != null) {
                        html += '<tr><td>工号</td><td>' + bodyList.YHM + '</td></tr>'
                        html += '<tr><td>姓名</td><td>' + bodyList.XM + '</td></tr>'
                        for (var i = 0; i < headList.length; i++) {
                            html += '<tr><td>' + headList[i].ZDMS + '</td><td>' + (bodyList[(headList[i].ZD).toUpperCase()] == null ? "" : bodyList[(headList[i].ZD).toUpperCase()]) + '</td></tr>'
                        }
                        $("#table").html(html)
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
    function getURLParameter(name) {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null;
    }
</script>
</body>

</html>







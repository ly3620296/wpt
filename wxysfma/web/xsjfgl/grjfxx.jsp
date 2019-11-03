<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.controller.login.WptMaUserInfo" %>
<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/myCommon.css">
    <style>
        .zoomImage {
            object-fit:cover;
            max-width: 180px !important;
        }
    </style>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<jsp:include page="/login/xsauth.jsp"></jsp:include>
<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
%>
<div class="layui-fluid">
    <div>
        <fieldset class="layui-elem-field layui-field-title"
                  style="margin-top: 20px;border-color: #009688 !important;">
            <legend>个人信息</legend>
        </fieldset>
    </div>
</div>
<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-card-body">
                <div class="layui-form">
                    <table class="layui-table">
                        <colgroup>
                            <col width="15%">
                            <col width="25%">
                            <col width="15%">
                            <col width="25%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th colspan="5" style="background-color: #d3d8de">
                                <div align="center">
                                    学生个人信息
                                </div>
                            </th>
                        </tr>
                        </thead>
                        <tbody align="center">
                        <tr >
                            <td style="background-color: #eef9fb">姓名</td>
                            <td><%=userInfo.getXm()%>
                            </td>
                            <td style="background-color: #eef9fb">学号/考生号</td>
                            <td><%=userInfo.getZh()%>
                            </td>
                            <td rowspan="4" style="width: 500px;">
                                <img src="<%=Constant.server_name%>img/tx.jpg"  class="zoomImage"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #eef9fb">年级</td>
                            <td><%=userInfo.getNjmc()%>
                            </td>
                            <td style="background-color: #eef9fb">身份证号</td>
                            <td><%=userInfo.getZjhm()%>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #eef9fb">学院名称</td>
                            <td><%=userInfo.getJgmc()%>
                            </td>
                            <td style="background-color: #eef9fb">专业名称</td>
                            <td><%=userInfo.getZymc()%>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #eef9fb">班级名称</td>
                            <td><%=userInfo.getBjmc()%>
                            </td>
                            <td style="background-color: #eef9fb"></td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="layui-fluid">
    <div>
        <fieldset class="layui-elem-field layui-field-title"
                  style="margin-top: 20px;border-color: #009688 !important;">
            <legend>缴费信息</legend>
        </fieldset>
    </div>
</div>

<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-card-body">
                <div class="layui-form">
                    <table class="layui-table" id="jfjl-table">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script>
    layui.use(['form', 'layer', 'table'], function () {
        var jQuery = layui.jquery
                , form = layui.form
                , layer = layui.layer
                , table = layui.table
                , $ = layui.jquery
        var loadIndex;
        var wpt_grjfxx = {
            init: function () {
                $.ajax({
                    url: wpt_serverName + "xsjfgl/grjfxx/title",
                    type: 'post',
                    dataType: 'json',
                    timeout: 10000,
                    beforeSend: function () {
                        loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                    },
                    success: function (data) {
                        if (data.code == "0") {
                            wpt_grjfxx.initTab(data.titles);
                        } else {
                            layer.msg(data.msg, {anim: 6, time: 2000});
                        }
                    },
                    complete: function () {
                        layer.close(loadIndex);
                    }
                })
            },
            initTab: function (titles) {
                var col = titles.length + 2;
                var cols2 = [{title: "学年", field: "XN", align: "center"}, {
                    title: "交费合计",
                    field: "YSHJ",
                    align: "center"
                }];
                for (var i = 0; i < titles.length; i++) {
                    if (titles[i].SFBX == "1") {
                        cols2[i + 2] = {title: titles[i].JFXMMC, field: titles[i].JFXMID, align: "center"};
                    } else {
                        cols2[i + 2] = {title: titles[i].JFXMMC + "（选交）", field: titles[i].JFXMID, align: "center"};
                    }

                }
                table.render({
                    elem: '#jfjl-table'  //容器id
                    , cols: [[   //表头
                        {title: "已缴费用信息", colspan: col, align: "center", style: 'background-color: blue;'}
                    ], cols2]
                    , url: wpt_serverName + 'xsjfgl/grjfxx?' //数据接口地址
                    , title: '用户表'
//                    , page: true //开启分页
                    , loading: true
//                    , even: true  //隔行换色 默认false
                    , done: function (res, curr, count) { //加载完回调
                        $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'})
                    },
                    id: 'userTableReload'
                });
            }

        }
        wpt_grjfxx.init();

    });


</script>
</body>
</html>
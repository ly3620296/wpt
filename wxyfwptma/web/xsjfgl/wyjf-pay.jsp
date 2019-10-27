<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/10/24 0024
  Time: 下午 8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <style>
        .layui-table-view .layui-form-checkbox.layui-btn-disabled[lay-skin=primary] i, .layui-table-view .layui-form-checkbox.layui-checkbox-disbaled[lay-skin=primary] i {
            background-color: #e9e9e9;
            border-color: #e9e9e9;
        }
    </style>
</head>
<jsp:include page="/login/xsauth.jsp"></jsp:include>
<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
    String xn = request.getParameter("xn");
%>
<body>
<div style="padding: 10px;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div class="layui-form">
                        <table class="layui-table">
                            <thead>
                            <tr>
                                <th colspan="4" style="background-color: #eef9fb">
                                    <div align="center">
                                        订单信息
                                    </div>
                                </th>
                            </tr>
                            </thead>
                            <tbody align="center">
                            <tr>
                                <td style="background-color: #eef9fb">交费学年</td>
                                <td><%=xn%>
                                </td>
                                <td style="background-color: #eef9fb">姓名</td>
                                <td><%=userInfo.getXm()%>
                                </td>
                            </tr>
                            <tr>
                                <td style="background-color: #eef9fb">学号/考生号</td>
                                <td><%=userInfo.getZh()%>
                                </td>
                                <td style="background-color: #eef9fb">身份证号</td>
                                <td><%=userInfo.getZjhm()%>
                                </td>
                            </tr>
                            <tr>
                                <td style="background-color: #eef9fb">年级</td>
                                <td><%=userInfo.getNjmc()%>
                                </td>
                                <td style="background-color: #eef9fb">学院名称</td>
                                <td><%=userInfo.getJgmc()%>
                                </td>
                            </tr>
                            <tr>
                                <td style="background-color: #eef9fb">专业名称</td>
                                <td><%=userInfo.getZymc()%>
                                </td>
                                <td style="background-color: #eef9fb">班级名称</td>
                                <td><%=userInfo.getBjmc()%>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-md7">
            <div class="layui-card">
                <div class="layui-card-body">
                    <table class="layui-table" id="jfjl" lay-filter="jfjl-fil">
                    </table>
                    <button type="button" class="layui-btn layui-btn-fluid" id="tjdd">提交订单</button>
                </div>
            </div>
        </div>
        <div class="layui-col-md5">
            <div class="layui-card">
                <div class="layui-card-header">卡片面板</div>
                <div class="layui-card-body" id="qrcode">
                    结合 layui 的栅格系统<br>
                    轻松实现响应式布局
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<%--var qrcode = new QRCode(document.getElementById("qrcode"), {--%>
<%--width: 132,--%>
<%--height: 132--%>
<%--});--%>
<%--qrcode.makeCode(url);--%>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script>
    layui.use(['form', 'layer', 'table'], function () {
        var jQuery = layui.jquery
                , form = layui.form
                , layer = layui.layer
                , table = layui.table
                , $ = layui.jquery
        var loadIndex;
        var cacheBj = 0;
        var wpt_wyjfPay = {
            initTab: function () {
                table.render({
                    elem: '#jfjl'  //容器id
                    , cols: [[   //表头
                        {title: "本次交费明细信息", colspan: 3, align: "center", style: 'background-color: blue;'}
                    ], [{
                        title: "<font color='red'>说明：选交项目，不想交时请取消勾选。</font>",
                        colspan: 3,
                        align: "center",
                        style: 'background-color: blue;'
                    }], [{type: 'checkbox', fixed: 'left', LAY_CHECKED: true}, {
                        title: "收费项目",
                        field: "xmmc",
                        align: "center",
                        totalRowText: "本次交费合计："
                    }, {
                        title: "收费金额", field: "jfje", align: "center", totalRow: true
                    }
                    ]]
                    , url: wpt_serverName + 'xsjfgl/wyjf/wjfjl?xn=' + '<%=xn%>' //数据接口地址
                    , title: '用户表'
                    , loading: true
                    , totalRow: true
                    , done: function (res, curr, count) {
                        //加载完回调
                        $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'});
                        var state = "";
                        for (var i in res.data) {
                            var sfbx = res.data[i].sfbx;
                            if (sfbx == "1") {
                                // checkbox 根据条件设置不可选中
                                $('tr[data-index=' + i + '] input[type="checkbox"]').prop('disabled', true);
                                $('tr[data-index=' + i + '] input[type="checkbox"]').next().addClass('layui-btn-disabled');
                                cacheBj += parseInt(res.data[i].jfje);
                            }
                        }

                    },
                    id: 'userTableReload'
                });
            },

            myOn: function () {
                $("#tjdd").bind("click", function () {
                    alert($(".layui-table-total td[data-key='1-2-2'] div").html());
                })

                table.on('checkbox(jfjl-fil)', function (obj) {
                    if (obj.type == "one") {
                        var currTr = obj.data.jfje
                        var curr = $(".layui-table-total td[data-key='1-2-2'] div").html();
                        if (obj.checked) {
                            $(".layui-table-total td[data-key='1-2-2'] div").html((parseFloat(curr) + parseFloat(currTr)).toFixed(2));
                        } else {
                            $(".layui-table-total td[data-key='1-2-2'] div").html((parseFloat(curr) - parseFloat(currTr)).toFixed(2));
                        }
                    } else if (obj.type == "all") {
                        var checkStatus = table.checkStatus('userTableReload');
                        var checkData = checkStatus.data;
                        var tot = 0;
                        if (checkData) {
                            var curr = $(".layui-table-total td[data-key='1-2-2'] div").html()
                            for (var i = 0; i < checkData.length; i++) {
                                tot += parseInt(checkData[i].jfje);
                            }
                            if (checkData.length == 0) {
                                $(".layui-table-total td[data-key='1-2-2'] div").html(parseFloat(cacheBj).toFixed(2));
                            } else {
                                $(".layui-table-total td[data-key='1-2-2'] div").html(parseFloat(tot).toFixed(2));
                            }
                        }
                    }
                });
            }

        }
        wpt_wyjfPay.initTab();
        wpt_wyjfPay.myOn();

    });
</script>
</body>
</html>

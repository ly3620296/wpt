<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/myCommon.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <style>
        .layui-btn-disabled-my, .layui-btn-disabled-my:active, .layui-btn-disabled-my:hover {
            border: 1px solid #e6e6e6;
            background-color: #e28888;
            color: #c7bfbf;
            cursor: not-allowed;
            opacity: 1;
        }

        .layui-table-view .layui-form-checkbox.layui-btn-disabled[lay-skin=primary] i, .layui-table-view .layui-form-checkbox.layui-checkbox-disbaled[lay-skin=primary] i {
            background-color: #e9e9e9;
            border-color: #e9e9e9;
        }

        .layui-card-body .gray {

            width: 100%;
            padding: 20px 0;
            overflow: hidden;
            background: #e6e6e6;
            color: #333;
        }

        .layui-card-body .gray .left {
            margin-left: 20px;
            line-height: 30px;
            float: left;
        }

        .layui-card-body .gray .right {
            margin-right: 20px;
            line-height: 30px;
            float: right;
        }

        .layui-card-body .graycenter {
            width: 90%;
            margin: 10px 5%;
            border: 1px solid #e6e6e6;
            padding: 10px 5px;
            position: relative;
        }

        .layui-card-body {
            padding: 10px 0;
        }

        .layui-card-body .graycenter .img1 {
            width: 100px;
            margin-left: 10px;
            vertical-align: middle;
        }

        .layui-card-body .graycenter .img2 {
            width: 50px;
            margin-left: 10px;
            vertical-align: middle;
        }

        .layui-card-body .bottoom {
            width: 100%;
            text-align: center;
        }

        .layui-card-body .bottoom #qrcode {
            display: inline-block;
            /*margin-top: 20px;*/
        }

        .layui-card-body .bottoom img {
            width: 200px;
            display: inline-block;
            margin-top: 6px;

        }

        .layui-card-body .graycenter span {
            position: absolute;
            top: 14px;
            right: 15px;
        }

        .caca {
            padding-bottom: 32px;
            margin-top: 10px !important;
        }
    </style>
</head>
<jsp:include page="/login/lsauth.jsp"></jsp:include>
<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
    userInfo = new WptMaXSUserInfo();
    String xn = request.getParameter("xn");
    String type = request.getParameter("type");
    String order_no = request.getParameter("order_no");
    String zh = request.getParameter("zh");
%>
<body>
<input type="hidden" value="0" id="hideIf">

<div style="padding: 10px;" id="print">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body">
                    <%--<div class="layui-form">--%>
                    <table class="layui-table" id="myInfo">
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
                        <tr style="display: none" id="bh-zt" width="20%">
                            <td style="background-color: #eef9fb">订单编号</td>
                            <td id="ly-bh" width="30%"></td>
                            <td style="background-color: #eef9fb" width="20%">订单状态</td>
                            <td id="ly-zt" width="30%"></td>
                        </tr>
                        <tr>
                            <td style="background-color: #eef9fb">交费学年</td>
                            <td><%=xn%>
                            </td>
                            <td style="background-color: #eef9fb">姓名</td>
                            <td id="xm">
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #eef9fb">学号/考生号</td>
                            <td><%=zh%>
                            </td>
                            <td style="background-color: #eef9fb">身份证号</td>
                            <td id="sfzh">
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #eef9fb">年级</td>
                            <td id="nj">
                            </td>
                            <td style="background-color: #eef9fb">学院名称</td>
                            <td id="xymc">
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #eef9fb">专业名称</td>
                            <td id="zymc">
                            </td>
                            <td style="background-color: #eef9fb">班级名称</td>
                            <td id="bjmc">
                            </td>
                        </tr>
                        <tr style="display: none" id="je-sj">
                            <td style="background-color: #eef9fb">支付金额</td>
                            <td id="ly-je"></td>
                            <td style="background-color: #eef9fb">支付时间</td>
                            <td id="ly-sj"></td>
                        </tr>
                        </tbody>
                    </table>
                    <%--</div>--%>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header" style="font-weight: bold;font-size: 15px!important;">本次交费明细信息</div>
                <div class="layui-card-body">
                    <table class="layui-table" id="jfjl" lay-filter="jfjl-fil">
                    </table>
                    <button type="button" class="layui-btn layui-btn-fluid" id="myPrint">关&nbsp;&nbsp;闭</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/qrcode.min.js"></script>
<script>
    layui.use(['form', 'layer', 'table'], function () {
        var jQuery = layui.jquery
                , form = layui.form
                , layer = layui.layer
                , table = layui.table
                , $ = layui.jquery
        var loadIndex;
        var cacheBj = 0;
//        var qrcode = new QRCode(document.getElementById("qrcode"), {
//            width: 200,
//            height: 200
//        });

        var wpt_wyjfPay = {
            initTab: function () {
                var myType = '<%=type%>';
                var url = "";
                var cols1 = "";
                if (myType == "qx") {
                    url = wpt_serverName + 'lsjfgl/tjcx/xsddcx/qxInfo?order_no=' + '<%=order_no%>' + '&xn=' + '<%=xn%>'+ '&zh=' + '<%=zh%>';
                    cols1 = "本次交费明细信息";
                } else if (myType == "cg") {
                    url = wpt_serverName + 'lsjfgl/tjcx/xsddcx/cgInfo?order_no=' + '<%=order_no%>' + '&xn=' + '<%=xn%>'+ '&zh=' + '<%=zh%>';
                    cols1 = "本次交费明细信息";
                }
                table.render({
                    elem: '#jfjl'  //容器id
                    , cols: [[   //表头
                        {title: cols1, colspan: 2, align: "center", style: 'background-color: blue;'}
                    ], [
                        {title: "收费项目", field: "xmmc", align: "center", totalRowText: "本次交费合计："},
                        {title: "收费金额", field: "jfje", align: "center", totalRow: true},
                    ]]
                    , url: url
                    , title: '用户表'
                    , loading: true
                    , totalRow: true
                    , done: function (res, curr, count) {
                        //加载完回调
                        $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'});
                        var state = "";
                        if ('<%=type%>' == 'qx') {
                            for (var i in res.data) {
                                $('tr[data-index=' + i + '] input[type="checkbox"]').prop('disabled', true);
                                $('tr[data-index=' + i + '] input[type="checkbox"]').next().addClass('layui-btn-disabled');
                            }
                            wpt_wyjfPay.my_show(res, '<%=type%>')
                        } else if ('<%=type%>' == 'cg') {
                            for (var i in res.data) {
                                $('tr[data-index=' + i + '] input[type="checkbox"]').prop('disabled', true);
                                $('tr[data-index=' + i + '] input[type="checkbox"]').next().addClass('layui-btn-disabled');

                            }
                            wpt_wyjfPay.my_show(res, '<%=type%>')
                        }

                    },
                    id: 'userTableReload'
                });
            },
            my_show: function (res, type) {
                if (type == "qx") {
                    $("#ly-bh").html("<%=order_no%>")
                    $("#ly-zt").html("<font color='red'>已取消</font>");
                    $("#xm").html(res.userInfo.XM);
                    $("#sfzh").html(res.userInfo.ZJHM);
                    $("#nj").html(res.userInfo.NJMC);
                    $("#xymc").html(res.userInfo.JGMC);
                    $("#zymc").html(res.userInfo.ZYMC);
                    $("#bjmc").html(res.userInfo.BJMC);
                    $("#bh-zt").show();
                    $("#je-sj").show();
                } else if (type == "cg") {
                    $("#ly-bh").html("<%=order_no%>")
                    $("#ly-zt").html("<font color='red'>支付成功</font>");
                    $("#xm").html(res.userInfo.XM);
                    $("#sfzh").html(res.userInfo.ZJHM);
                    $("#nj").html(res.userInfo.NJMC);
                    $("#xymc").html(res.userInfo.JGMC);
                    $("#zymc").html(res.userInfo.ZYMC);
                    $("#bjmc").html(res.userInfo.BJMC);
                    $("#bh-zt").show();
                    $("#ly-je").html(res.total_fee);
                    $("#ly-sj").html(res.time_end);
                    $("#je-sj").show()
                }

            },

            myOn: function () {
                $("#myPrint").bind("click", function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                })

            }

        }
        wpt_wyjfPay.initTab();
        wpt_wyjfPay.myOn();


    });
</script>
</body>
</html>

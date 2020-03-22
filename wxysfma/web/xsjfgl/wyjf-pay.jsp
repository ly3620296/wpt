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
            padding-bottom: 22px;
            margin-top: 10px !important;
        }
    </style>
</head>
<jsp:include page="/login/xsauth.jsp"></jsp:include>
<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
    String xn = request.getParameter("xn");
    String type = request.getParameter("type");
    String order_no = request.getParameter("order_no");
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
                            <tr style="display: none" id="bh-zt">
                                <td style="background-color: #eef9fb">订单编号</td>
                                <td id="ly-bh"></td>
                                <td style="background-color: #eef9fb">订单状态</td>
                                <td id="ly-zt"></td>
                            </tr>
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
                            <tr style="display: none" id="je-sj">
                                <td style="background-color: #eef9fb">支付金额</td>
                                <td id="ly-je"></td>
                                <td style="background-color: #eef9fb">支付时间</td>
                                <td id="ly-sj"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-md7">
            <div class="layui-card">
                <div class="layui-card-header" style="font-weight: bold;font-size: 15px!important;">本次交费明细信息</div>
                <div class="layui-card-body">
                    <table class="layui-table" id="jfjl" lay-filter="jfjl-fil">
                    </table>
                </div>
                <button type="button" class="layui-btn layui-btn-fluid" id="tjdd">提交订单</button>
            </div>
        </div>
        <div class="layui-col-md5">
            <div class="layui-card">
                <div class="layui-card-header" style="font-weight: bold;font-size: 15px!important;">收银台</div>
                <div class="layui-card-body">
                    <div class="gray">
                        <p><span class="left" id="ddh"></span></p>

                        <%--<p><span class="left" id="yfje"></span></p>--%>

                    </div>
                    <div class="graycenter">

                        <img class="img1" src="<%=Constant.server_name%>img/WePayLogo.png" alt=""/>
                        <img class="img2" src="<%=Constant.server_name%>img/tj.png" alt=""/>
                        <span class="num" id="yfje-zf"></span>
                    </div>
                    <div class="bottoom">
                        <div>
                            <div id="qrcode"></div>
                        </div>
                        <img src="<%=Constant.server_name%>img/ss.png" alt="" class="caca"/>
                    </div>

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
        var qrcode = new QRCode(document.getElementById("qrcode"), {
            width: 200,
            height: 200
        });

        var wpt_wyjfPay = {
            initTab: function () {
                var myType = '<%=type%>';
                var url = "";
                if (myType == "zf") {
                    url = wpt_serverName + 'xsjfgl/wyjf/wjfjl?xn=' + '<%=xn%>';
                } else if (myType == "jxzf") {
                    url = wpt_serverName + 'xsjfgl/wyjf/jxWjfjl?order_no=' + '<%=order_no%>' + '&xn=' + '<%=xn%>';
                } else if (myType == "qx") {
                    url = wpt_serverName + 'xsjfgl/wyjf/jxWjfjl?order_no=' + '<%=order_no%>' + '&xn=' + '<%=xn%>';
                }
                table.render({
                    elem: '#jfjl'  //容器id
//                            [   //表头
//                    {title: "本次交费明细信息", colspan: 3, align: "center", style: 'background-color: blue;'}
//                            ],
                    , cols: [[{
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
                    , url: url
                    , title: '用户表'
                    , loading: true
                    , totalRow: true
                    , done: function (res, curr, count) {
                        if (res.code == 0) {
                            //加载完回调
                            $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'});
                            var state = "";
                            if ('<%=type%>' == 'zf') {
                                for (var i in res.data) {
                                    var sfbx = res.data[i].sfbx;
                                    var jfje = res.data[i].jfje;
                                    if (sfbx == "1") {
                                        // checkbox 根据条件设置不可选中
                                        $('tr[data-index=' + i + '] input[type="checkbox"]').prop('disabled', true);
                                        $('tr[data-index=' + i + '] input[type="checkbox"]').next().addClass('layui-btn-disabled');
                                        cacheBj += parseInt(res.data[i].jfje);
                                    }
                                    if (jfje == "0") {
                                        $('tr[data-index=' + i + '] input[type="checkbox"]').prop('disabled', true);
                                        $('tr[data-index=' + i + '] input[type="checkbox"]').next().addClass('layui-btn-disabled');
                                    }
                                }
                                wpt_wyjfPay.myOn();
                                wpt_wyjfPay.tj();
                            } else if ('<%=type%>' == 'jxzf') {
                                for (var i in res.data) {
                                    $('tr[data-index=' + i + '] input[type="checkbox"]').prop('disabled', true);
                                    $('tr[data-index=' + i + '] input[type="checkbox"]').next().addClass('layui-btn-disabled');
                                    $("#tjdd").addClass('layui-btn-disabled-my');
                                }
                                qrcode.makeCode(res.code_url);

                                wpt_wyjfPay.my_show(res)

                            } else if ('<%=type%>' == 'qx') {
                                for (var i in res.data) {
                                    $('tr[data-index=' + i + '] input[type="checkbox"]').prop('disabled', true);
                                    $('tr[data-index=' + i + '] input[type="checkbox"]').next().addClass('layui-btn-disabled');
                                    $("#tjdd").html("取消订单");
                                    $("#tjdd").addClass("layui-btn-danger");
                                }
                                wpt_wyjfPay.qx();
                                wpt_wyjfPay.my_show(res)
                            }
                        } else {
                            layer.msg(res.msg, {
                                anim: 6, time: 4000, shade: [0.2, '#393D49'], end: function () {
                                    parent.location.reload();
                                }
                            });
                        }

                    },
                    id: 'userTableReload'
                });
            },
            tj_show: function (res) {
                $("#ddh").html("订单编号：" + res.oreder_no);
//                $("#yfje").html(" 应付金额：￥" + res.money);
                $("#yfje-zf").html("应付金额：￥" + res.money);
                $("#ly-bh").html(res.oreder_no)
                $("#ly-zt").html("<font color='red'>待支付</font>");
                $("#bh-zt").show();
                $("#je-sj").show();

            },
            my_show: function (res) {
                $("#ddh").html("订单编号：" + res.order_no);
//                $("#yfje").html(" 应付金额：￥" + res.total_fee);
                $("#yfje-zf").html("应付金额：￥" + res.total_fee);
                $("#ly-bh").html(res.order_no)
                $("#ly-zt").html("<font color='red'>" + res.order_state + "</font>");
                $("#bh-zt").show();
                $("#je-sj").show();
            },
            tj: function () {
                $("#tjdd").bind("click", function () {
                    var currJe = $(".layui-table-total td[data-key='1-1-2'] div").html();
                    if (currJe == "0.00" || currJe == null || currJe == undefined || currJe == 0) {
                        layer.alert('请至少选择一项收费项目！', {icon: 7, title: "温馨提示"});
                        return;
                    }
                    layer.confirm(' 确定提交当前订单【' + '<%=xn%>' + '】学年、费用总额【' + currJe + '元】？', {
                        title: "温馨提示",
                        btn: ['确定', '返回'] //按钮
                    }, function () {

                        var checkData = table.checkStatus('userTableReload').data;
                        var tableCach = table.cache.userTableReload;
                        var xmidArr = new Array();
                        if (checkData.length > 0) {
                            for (var i = 0; i < checkData.length; i++) {
                                var sfbx = checkData[i].sfbx;
                                var jfje = checkData[i].jfje;
                                if (sfbx == "0") {
                                    if (jfje > 0) {
                                        xmidArr.push(checkData[i].xmid);
                                    }
                                }
                            }
                        }

                        for (var i = 0; i < tableCach.length; i++) {
                            var sfbx = tableCach[i].sfbx;
                            var jfje = tableCach[i].jfje;
                            if (sfbx == "1") {
                                if (jfje > 0) {
                                    xmidArr.push(tableCach[i].xmid);
                                }
                            }

                        }

                        $.ajax({
                            url: wpt_serverName + "xsjfgl/wyjfDd/zfXzf",
                            type: 'post',
                            dataType: 'json',
                            data: {xmid: xmidArr, sfxn: '<%=xn%>'},
                            timeout: 10000,
                            beforeSend: function () {
                                loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                            },
                            success: function (data) {
                                if (data) {
                                    var code = data.returnInfo.return_code;
                                    var msg = data.returnInfo.return_msg;
                                    wpt_wyjfPay.changePar();
                                    if (code == "0") {
                                        layer.msg('订单填写成功，订单号【' + data.oreder_no + '】。', {
                                            icon: 1,
                                            shade: [0.3, '#000']
                                        });
                                        qrcode.makeCode(data.code_url);
                                        $("#tjdd").attr('disabled', "true");
                                        $("#tjdd").addClass('layui-btn-disabled-my');
                                        wpt_wyjfPay.tj_show(data);
                                        wpt_wyjfPay.initOrderPull(data.oreder_no)
                                    } else {
                                        layer.msg(msg, {
                                            anim: 6, time: 4000, end: function () {
                                                parent.location.reload();
                                            }
                                        });
                                    }
                                }
                            },
                            complete: function () {
                                layer.close(loadIndex);
                            }
                        })
                    }, function () {

                    });

                })
            },
            initOrderPull: function (order_no) {
                var intVe = setInterval(function () {
                    $.ajax({
                        url: wpt_serverName + "xsjfgl/wheel",
                        type: 'post',
                        dataType: 'json',
                        data: {order_no: order_no},
                        timeout: 10000,
                        success: function (data) {
                            if (data.code == "2") {
                                clearInterval(intVe);
                                layer.alert('付款成功', {
                                    icon: 6, title: '温馨提示', end: function () {
                                        parent.location.reload();
                                    }
                                })
                            }
                        },
                        complete: function () {
                            layer.close(loadIndex);
                        }
                    })
                }, 5000);

            },
            changePar: function () {
                parent.document.getElementById("my_status").value = "1";
            },
            qx: function () {
                $("#tjdd").bind("click", function () {
                    layer.confirm('确定要取消当前订单【' + '<%=order_no%>' + '】吗？取消后不可恢复。', {
                        title: "温馨提示",
                        btn: ['取消', '返回'] //按钮
                    }, function () {
                        $.ajax({
                            url: wpt_serverName + "xsjfgl/wyjfDd/closeOrder",
                            type: 'post',
                            dataType: 'json',
                            data: {order_no: '<%=order_no%>'},
                            timeout: 10000,
                            beforeSend: function () {
                                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                            },
                            success: function (data) {
                                wpt_wyjfPay.changePar();
                                if (data.returnInfo.return_code == "0") {
                                    $("#tjdd").html('返回');
                                    $("#tjdd").unbind();
                                    $("#tjdd").bind('click', function () {
                                        var index = parent.layer.getFrameIndex(window.name)
                                        parent.layer.close(index);
                                    });
                                    layer.msg('订单号【' + '<%=order_no%>' + '】已取消！', {icon: 1, shade: [0.3, '#000']});
                                    $("#ly-zt").html("<font color='red'>已取消</font>");
                                } else {
                                    layer.msg(data.returnInfo.return_msg, {
                                        anim: 6, time: 2000, end: function () {
                                            parent.location.reload();
                                        }
                                    });


                                }
                            },
                            complete: function () {
                                layer.close(loadIndex);
                            }
                        })
                    }, function () {

                    });

                })
            },
            myOn: function () {
                table.on('checkbox(jfjl-fil)', function (obj) {
                    if (obj.type == "one") {
                        var currTr = obj.data.jfje
                        var curr = $(".layui-table-total td[data-key='1-1-2'] div").html();
                        if (obj.data.sfbx == "0") {
                            if (obj.checked) {
                                $(".layui-table-total td[data-key='1-1-2'] div").html((parseFloat(curr) + parseFloat(currTr)).toFixed(2));
                            } else {
                                $(".layui-table-total td[data-key='1-1-2'] div").html((parseFloat(curr) - parseFloat(currTr)).toFixed(2));
                            }
                        }
                    } else if (obj.type == "all") {
                        var checkStatus = table.checkStatus('userTableReload');
                        var checkData = checkStatus.data;
                        var tot = 0;
                        if (checkData) {
                            for (var i = 0; i < checkData.length; i++) {
                                tot += parseInt(checkData[i].jfje);
                            }
                            if (checkData.length == 0) {
                                $(".layui-table-total td[data-key='1-1-2'] div").html(parseFloat(cacheBj).toFixed(2));
                            } else {
                                $(".layui-table-total td[data-key='1-1-2'] div").html(parseFloat(tot).toFixed(2));
                            }
                        }
                    }
                });
            }

        }
        wpt_wyjfPay.initTab();


    });
</script>
</body>
</html>

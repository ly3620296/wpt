<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/myCommon.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <%--<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>--%>
</head>
<body>
<input type="hidden" id="my_status" value="0">
<jsp:include page="/login/xsauth.jsp"></jsp:include>
<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
    if (userInfo == null) {
%>
<script>
    if (!!(window.attachEvent && !window.opera)) {
        document.execCommand("stop");
    }
    else {
        window.stop();
    }
    parent.location.reload();
    //    window.location.href = wpt_serverName+"login/xslogin.jsp";
</script>
<%
} else {
%>

<div class="layui-fluid">
    <div>
        <fieldset class="layui-elem-field layui-field-title"
                  style="margin-top: 20px;border-color: #009688 !important;">
            <legend>我要交费</legend>
        </fieldset>
    </div>
</div>

<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-card-body">
                <div class="layui-form">
                    <table class="layui-table" id="jfjl-table" lay-filter="jfFilter">
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
            <legend>未完成订单</legend>
        </fieldset>
    </div>
</div>
<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-card-body">
                <div class="layui-form">
                    <table class="layui-table" id="jfjl-order-table" lay-filter="wzfOrder">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="barDemo">
    <a class="layui-btn  layui-btn-normal layui-btn-sm" lay-event="jf">交费</a>
</script>
<script type="text/html" id="barDemo1">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="zf">支付</a>
    <a class="layui-btn layui-btn-danger layui-btn-sm" lay-event="qx">取消</a>
</script>

<script type="text/html" id="barDemo2">
    <a class="layui-btn layui-btn-danger layui-btn-sm" lay-event="qx">取消</a>
</script>
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
                    url: wpt_serverName + "xsjfgl/wyjf/title",
                    type: 'post',
                    dataType: 'json',
                    timeout: 10000,
                    beforeSend: function () {
                        loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                    },
                    success: function (data) {
                        if (data.code == "0") {
                            wpt_grjfxx.initTab(data.titles);

                            wpt_grjfxx.initOrderTab(data.noPayOrderInfo);
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
                var col = titles.length + 3;
                var cols2 = [{title: "学年", field: "XN", align: "center"}, {
                    title: "交费合计（元）",
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
                cols2[titles.length + 2] = {fixed: 'right', title: '操作', align: 'center', toolbar: '#barDemo'};
                table.render({
                    elem: '#jfjl-table'  //容器id
                    , cols: [
                        [{
                            title: "学院：<%=userInfo.getJgmc()%>，&nbsp;专业：<%=userInfo.getZymc()%>，&nbsp;班级：<%=userInfo.getBjmc()%>，&nbsp;姓名：<%=userInfo.getXm()%>，&nbsp;学号：<%=userInfo.getZh()%>，&nbsp;身份证号：<%=userInfo.getZjhm()%>",
                            colspan: col,
                            align: "center"
                        }],
                        [   //表头
                            {title: "应交费用信息<font color='red'>（温馨提示：选交收费项目，可以不交）</font>", colspan: col, align: "center"}
                        ], cols2]
                    , url: wpt_serverName + 'xsjfgl/wyjf' //数据接口地址
                    , title: '用户表'
//                    , page: true //开启分页
                    , loading: true
//                    , even: true  //隔行换色 默认false
                    , done: function (res, curr, count) { //加载完回调
                        $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'})
                    },
                    id: 'userTableReload'
                });
            },
            initOrderTab: function (noPayOrderInfo) {
                var dataArr = new Array();

                var barType="#barDemo1";
                if (noPayOrderInfo) {
                    if(noPayOrderInfo.payType=="JSAPI"){
                        barType="#barDemo2";
                    }
                    dataArr.push(noPayOrderInfo);
                }

                table.render({
                    elem: '#jfjl-order-table'  //容器id
                    , cols: [[
                        {
                            title: "<font color='red'>未完成订单(有未完成订单时不能下单，订单下错可取消订单重新下单。)</font>",
                            colspan: 12,
                            align: "center"
                        }
                    ], [   //表头
                        {title: "订单编号", field: "ddbh", align: "center"},
                        {title: "交费学年", field: "jfxn", align: "center", width: "6%"},
                        {title: "下单时间", field: "xdsj", align: "center", width: "10%"},
                        {title: "订单合计（元）", field: "ddhj", align: "center", width: "8%"},
                        {title: "学院名称", field: "xymc", align: "center"},
                        {title: "专业名称", field: "zymc", align: "center"},
                        {title: "班级名称", field: "bjmc", align: "center"},
                        {title: "姓名", field: "xm", align: "center", width: "6%"},
                        {title: "身份证号", field: "sfzh", align: "center", width: "11%"},
                        {title: "学号/考生号", field: "xh", align: "center", width: "8%"},
                        {title: "状态", field: "zt", align: "center", width: "6%"},
                        {title: "操作", align: "center", toolbar: barType, fixed: "right", width: "15%"},
                    ]],
                    data: dataArr, //数据接口地址
                    title: '用户表',
                    page: false, //开启分页
                    loading: true,
                    done: function (res, curr, count) { //加载完回调
                        $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'})
                    },
                    id: 'userTableOrderReload'
                });
            }, isFlush: function () {
                if ($("#my_status").val() == 1) {
                    window.location.reload();
                }
//
            },
            listenTool: function () {
                //监听行工具事件
                table.on('tool(jfFilter)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                    var data = obj.data //获得当前行数据
                            , layEvent = obj.event; //获得 lay-event 对应的值
                    var xn = data.XN;
                    if (layEvent === 'jf') {
                        $.ajax({
                            url: wpt_serverName + "xsjfgl/wyjfDd/noPayOrder",
                            type: 'post',
                            dataType: 'json',
                            timeout: 10000,
                            beforeSend: function () {
                                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                            },
                            success: function (data) {
                                if (data.returnInfo.return_code == "0") {
                                    if (data.noPayOrder == "-1") {
                                        layer.open({
                                            type: 2,
                                            area: [parseInt(parent.$("#iframe_01").width()) * 0.9 + 'px', parseInt(parent.$("#iframe_01").height()) * 0.95 + 'px'],
                                            title: "交费订单",
                                            fixed: false, //不固定
                                            maxmin: true,
                                            content: wpt_serverName + 'xsjfgl/wyjf-pay.jsp?type=zf&xn=' + xn,
                                            end: function () {
                                                wpt_grjfxx.isFlush();
                                            }
                                        });
                                    } else {
                                        layer.alert('您当前有未完成订单暂时不可以继续交费，请先支付或取消未完成订单,订单号【<font color="#ff5621"><strong>' + data.noPayOrder + '</strong></font>】', {
                                            icon: 7,
                                            title: "温馨提示"
                                        });
                                    }
                                } else {
                                    layer.msg(data.returnInfo.return_msg, {anim: 6, time: 2000});
                                }
                            },
                            complete: function () {
                                layer.close(loadIndex);
                            }
                        })

                    }
                });

                //监听行工具事件
                table.on('tool(wzfOrder)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                    var data = obj.data //获得当前行数据
                            , layEvent = obj.event; //获得 lay-event 对应的值
                    var orderNo = data.ddbh;
                    var xn = data.jfxn
                    if (layEvent === 'zf') {
                        wpt_grjfxx.zfOrder(xn, orderNo);
                    } else if (layEvent === 'qx') {
                        wpt_grjfxx.closeOrder(xn, orderNo);
                    }
                });
            },
            closeOrder: function (xn, orderNo) {
                layer.open({
                    type: 2,
                    area: [parseInt(parent.$("#iframe_01").width()) * 0.9 + 'px', parseInt(parent.$("#iframe_01").height()) * 0.95 + 'px'],
                    title: "交费订单",
                    fixed: false, //不固定
                    maxmin: true,
                    content: wpt_serverName + 'xsjfgl/wyjf-pay.jsp?type=qx&order_no=' + orderNo + '&xn=' + xn,
                    end: function () {
                        wpt_grjfxx.isFlush();
                    }
                });

            },
            zfOrder: function (xn, order_no) {
                layer.open({
                    type: 2,
                    area: [parseInt(parent.$("#iframe_01").width()) * 0.9 + 'px', parseInt(parent.$("#iframe_01").height()) * 0.95 + 'px'],
                    title: "交费订单",
                    fixed: false, //不固定
                    maxmin: true,
                    content: wpt_serverName + 'xsjfgl/wyjf-pay.jsp?type=jxzf&order_no=' + order_no + '&xn=' + xn,
                    end: function () {
                        wpt_grjfxx.isFlush();
                    }
                });
            }

        }
        $(document).ready(function(){
            wpt_grjfxx.init();
            wpt_grjfxx.listenTool();
        })


    });

</script>
</body>
<%
    }
%>
</html>
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
        .layui-table-tool-temp {
            padding-right: 30px !important;
        }

        .myDef {
            float: right;
            top: 3px;
            position: relative;
            width: 26px;
            height: 26px;
            padding: 5px;
            line-height: 16px;
            margin-right: 10px;
            text-align: center;
            color: #333;
            border: 1px solid #ccc;
            cursor: pointer;
            -webkit-transition: .5s all;
            transition: .5s all;
        }
    </style>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<input type="hidden" id="my_status" value="0">
<jsp:include page="/login/xsauth.jsp"></jsp:include>

<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
%>

<div class="layui-fluid">
    <div>
        <fieldset class="layui-elem-field layui-field-title"
                  style="margin-top: 20px;border-color: #009688 !important;">
            <legend>订单查询</legend>
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
<script type="text/html" id="titleTpl">
    {{#  if(d.ORDER_STATE == 0){ }}
    <font color="red">待支付</font>
    {{#  } else if(d.ORDER_STATE == 1){ }}
    <font color="red">已关闭</font>
    {{#  } else if(d.ORDER_STATE == 2){ }}
    <font color="red">支付成功</font>
    {{#  } else if(d.ORDER_STATE == 3){ }}
    <font color="red">异常订单</font>
    {{#  } else if(d.ORDER_STATE == 4){ }}
    <font color="red">已关闭（老师）</font>
    {{#  } else if(d.ORDER_STATE == 5){ }}
    <font color="red">已退费</font>
    {{#  } else { }}
    <font color="red">未知类型</font>
    {{#  } }}
</script>
<script type="text/html" id="toolbarDemo">
    <font color='red'>有未完成订单时不能下单，订单下错可取消订单重新下单。</font>
    <div class="layui-inline myDef" title="刷新" id="refresh">
        <i class="layui-icon layui-icon-refresh" style="font-size: 16px; color: #1E9FFF;"></i>
    </div>
    <%--<a class="layui-btn layui-btn-normal layui-btn-sm" style="float: right" lay-event="jf">--%>

    <%--</a>--%>
</script>
<script type="text/html" id="barDemo1">
    {{#  if(d.ORDER_STATE == 0){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="zf">支付</a>
    <a class="layui-btn layui-btn-danger layui-btn-sm" lay-event="qx">取消</a>
    {{#  } else if(d.ORDER_STATE == 1){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-qx">订单详情</a>
    {{#  } else if(d.ORDER_STATE == 2){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-cg">订单详情</a>
    {{#  } else if(d.ORDER_STATE == 3){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-yc">订单详情</a>
    {{#  } else if(d.ORDER_STATE == 4){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-cg">订单详情</a>
    {{#  } else if(d.ORDER_STATE == 5){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-tf">订单详情</a>
    {{#  } else { }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-qx">订单详情</a>
    {{#  } }}

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
            initTab: function () {
                table.render({
                    elem: '#jfjl-table'  //容器id
                    , cols: [
                        [
                            {title: "订单编号", field: "ORDER_NO", align: "center", width: "10%", fixed: "left"},
                            {title: "交费学年", field: "SFXN", align: "center", sort: true, width: "8%"},
                            {title: "下单时间", field: "TIME_START", align: "center", sort: true, width: "10%"},
                            {title: "订单合计（元）", field: "TOTAL_FEE", align: "center", sort: true, width: "9%"},
                            {title: "支付金额（元）", field: "TOTAL_FEE_CALLBACK", align: "center", sort: true, width: "9%"},
                            {title: "学院名称", field: "JGMC", align: "center"},
                            {title: "专业名称", field: "ZYMC", align: "center"},
                            {title: "班级名称", field: "BJMC", align: "center"},
                            {title: "姓名", field: "XM", align: "center"},
                            {title: "身份证号", field: "ZJHM", align: "center"},
                            {title: "学号/考生号", field: "ZH", align: "center"},
                            {title: "状态", field: "ORDER_STATE", align: "center", templet: '#titleTpl', width: "7%"},
                            {title: "操作", align: "center", templet: "#barDemo1", width: "14%", fixed: "right"}
                        ]
                    ]
                    , url: wpt_serverName + 'xsjfgl/ddcx' //数据接口地址
                    , title: '用户表'
                    , height: window.screen.height - 350
                    , page: true //开启分页
                    , loading: true
                    , even: true  //隔行换色 默认false,
                    , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
                    , defaultToolbar: ['filter', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                        title: '提示'
                        , layEvent: 'LAYTABLE_TIPS'
                        , icon: 'layui-icon-tips'
                    }]
                    , done: function (res, curr, count) { //加载完回调
                        $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'})
                    },
                    id: 'userTableReload'
                });
            },
            closeOrderInfo: function (xn, orderNo, type) {
                layer.open({
                    type: 2,
                    area: [parseInt(parent.$("#iframe_02").width()) * 0.7 + 'px', parseInt(parent.$("#iframe_02").height()) * 0.9 + 'px'],
                    title: "交费订单",
                    fixed: false, //不固定
                    maxmin: true,
                    content: wpt_serverName + 'xsjfgl/ddxq_info.jsp?type=' + type + '&order_no=' + orderNo + '&xn=' + xn
                });

            },
            zfOrder: function (xn, order_no) {
                layer.open({
                    type: 2,
                    area: [parseInt(parent.$("#iframe_02").width()) * 0.9 + 'px', parseInt(parent.$("#iframe_02").height()) * 0.95 + 'px'],
                    title: "交费订单",
                    fixed: false, //不固定
                    maxmin: true,
                    content: wpt_serverName + 'xsjfgl/wyjf-pay.jsp?type=jxzf&order_no=' + order_no + '&xn=' + xn,
                    end: function () {
                        wpt_grjfxx.isFlush();
                    }
                });
            },
            closeOrder: function (xn, orderNo) {
                layer.open({
                    type: 2,
                    area: [parseInt(parent.$("#iframe_02").width()) * 0.9 + 'px', parseInt(parent.$("#iframe_02").height()) * 0.95 + 'px'],
                    title: "交费订单",
                    fixed: false, //不固定
                    maxmin: true,
                    content: wpt_serverName + 'xsjfgl/wyjf-pay.jsp?type=qx&order_no=' + orderNo + '&xn=' + xn,
                    end: function () {
                        wpt_grjfxx.isFlush();
                    }
                });

            }, isFlush: function () {
                if ($("#my_status").val() == 1) {
                    window.location.reload();
                }
//
            },
            listenTool: function () {
                $("#refresh").bind("click", function () {
                    window.location.reload();
                })
                //监听行工具事件
                table.on('tool(jfFilter)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                    var data = obj.data //获得当前行数据
                            , layEvent = obj.event; //获得 lay-event 对应的值
                    var orderNo = data.ORDER_NO;
                    var xn = data.SFXN
                    console.log(data.SFXN)
                    if (layEvent === 'ddxz-qx') {
                        wpt_grjfxx.closeOrderInfo(xn, orderNo, "qx");
                    } else if (layEvent === 'ddxz-cg') {
                        wpt_grjfxx.closeOrderInfo(xn, orderNo, "cg");
                    }  else if (layEvent === 'ddxz-tf') {
                        wpt_grjfxx.closeOrderInfo(xn, orderNo, "tf");
                    }  else if (layEvent === 'ddxz-yc') {
                        wpt_grjfxx.closeOrderInfo(xn, orderNo, "yc");
                    } else if (layEvent === 'zf') {
                        wpt_grjfxx.zfOrder(xn, orderNo);
                    } else if (layEvent === 'qx') {
                        wpt_grjfxx.closeOrder(xn, orderNo);
                    }
                });
            }
        }
        wpt_grjfxx.initTab();
        wpt_grjfxx.listenTool();

    });

</script>
</body>
</html>
<%@ page import="gka.resource.Constant" %>
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

        .layui-form-item .layui-inline {
            margin-bottom: 15px;
            margin-right: 150px;
        }

        .layui-form-item .layui-input-inline {
            width: 320px;
        }
    </style>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<input type="hidden" id="my_status" value="0">
<jsp:include page="/login/lsauth.jsp"></jsp:include>

<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
%>

<div class="layui-fluid">
    <div>
        <fieldset class="layui-elem-field layui-field-title"
                  style="margin-top: 20px;border-color: #009688 !important;">
            <legend>未缴费信息查询</legend>
        </fieldset>
    </div>
</div>

<div class="layui-fluid">
    <%--<div class="layui-row">--%>
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item" id="my-header">
                <div class="layui-inline">
                    <label class="layui-form-label">订单编号：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-ddbh" placeholder="订单编号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <%--<div class="layui-inline ">--%>
                    <%--<label class="layui-form-label">学年：</label>--%>

                    <%--<div class="layui-input-inline">--%>
                        <%--<input type="text" id="search-xn" placeholder="学年" autocomplete="off" class="layui-input">--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-inline">--%>
                    <%--<label class="layui-form-label">姓名：</label>--%>

                    <%--<div class="layui-input-inline">--%>
                        <%--<input type="text" id="search-xm" placeholder="姓名" autocomplete="off" class="layui-input">--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-inline">--%>
                    <%--<label class="layui-form-label">订单状态：</label>--%>

                    <%--<div class="layui-input-inline">--%>
                        <%--<select name="city" lay-verify="required" id="search-ddzt">--%>
                            <%--<option value=""></option>--%>
                            <%--<option value="0">待支付</option>--%>
                            <%--<option value="2">支付成功</option>--%>
                            <%--<option value="3">问题订单</option>--%>
                            <%--<option value="1">已取消</option>--%>
                            <%--<option value="4">教师取消</option>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-inline">--%>
                    <%--<label class="layui-form-label">学号：</label>--%>

                    <%--<div class="layui-input-inline">--%>
                        <%--<input type="text" id="search-xh" placeholder="请输入" autocomplete="off" class="layui-input">--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-inline">--%>
                    <%--<label class="layui-form-label">身份证号：</label>--%>

                    <%--<div class="layui-input-inline">--%>
                        <%--<input type="text" id="search-sfzh" placeholder="请输入" autocomplete="off" class="layui-input">--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-inline">--%>
                    <%--<label class="layui-form-label">支付时间：</label>--%>

                    <%--<div class="layui-input-inline" style="width: 133px;">--%>
                        <%--<input type="text" name="title" placeholder="开始时间" autocomplete="off" class="layui-input"--%>
                               <%--id="dateStart">--%>
                    <%--</div>--%>
                    <%--<div class="layui-input-inline" style="width: 15px">--%>
                        <%--至--%>
                    <%--</div>--%>
                    <%--<div class="layui-input-inline" style="width: 133px;">--%>
                        <%--<input type="text" name="title" placeholder="结束时间" autocomplete="off" class="layui-input"--%>
                               <%--id="dateEnd">--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="layui-inline" style="margin-left: 50px;">
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="search" id="my-search">
                        查询
                    </button>
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="reset" id="my-reset">
                        重置
                    </button>
                </div>
            </div>
        </div>
        <div class="layui-card-body">
            <div class="layui-form">
                <table class="layui-table" id="jfjl-table" lay-filter="jfFilter">
                </table>
            </div>
        </div>
    </div>
    <%--</div>--%>
</div>
<script type="text/html" id="titleTpl">
    {{#  if(d.ORDER_STATE == 0){ }}
    <font color="red">待支付</font>
    {{#  } else if(d.ORDER_STATE == 1){ }}
    <font color="red">已取消</font>
    {{#  } else if(d.ORDER_STATE == 2){ }}
    <font color="red">支付成功</font>
    {{#  } else if(d.ORDER_STATE == 3){ }}
    <font color="red">问题订单</font>
    {{#  } else if(d.ORDER_STATE == 4){ }}
    <font color="red">教师取消</font>
    {{#  } }}
</script>
<script type="text/html" id="toolbarDemo">
    <div class="layui-inline myDef" title="刷新" id="refresh">
        <i class="layui-icon layui-icon-refresh" style="font-size: 16px; color: #1E9FFF;"></i>
    </div>
    <%--<a class="layui-btn layui-btn-normal layui-btn-sm" style="float: right" lay-event="jf">--%>

    <%--</a>--%>
</script>
<script type="text/html" id="barDemo2">
    {{#  if(d.ORDER_STATE == 2){ }}
    {{d.TIME_END}}
    {{#  } else{ }}
    {{#  } }}
</script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script>
    layui.use(['form', 'layer', 'table', 'laydate'], function () {
        var jQuery = layui.jquery
                , form = layui.form
                , layer = layui.layer
                , table = layui.table
                , laydate = layui.laydate
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
                            {
                                title: "支付时间",
                                field: "TIME_END",
                                align: "center",
                                sort: true,
                                width: "10%",
                                templet: "#barDemo2"
                            },
                            {title: "订单合计（元）", field: "TOTAL_FEE", align: "center", sort: true, width: "9%"},
                            {title: "支付金额（元）", field: "TOTAL_FEE_CALLBACK", align: "center", sort: true, width: "9%"},
                            {title: "学院名称", field: "JGMC", align: "center"},
                            {title: "专业名称", field: "ZYMC", align: "center"},
                            {title: "班级名称", field: "BJMC", align: "center"},
                            {title: "姓名", field: "XM", align: "center"},
                            {title: "身份证号", field: "ZJHM", align: "center"},
                            {title: "学号/考生号", field: "ZH", align: "center"},
                            {title: "状态", field: "ORDER_STATE", align: "center", templet: '#titleTpl', width: "7%"}
                        ]
                    ]
                    , url: wpt_serverName + 'lsjfgl/tjcx/xsddcx' //数据接口地址
                    , title: '用户表'
                    , height: window.screen.height - 450
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
            closeOrderInfo: function (xn, orderNo, type, zh) {
                layer.open({
                    type: 2,
                    area: [parseInt(parent.$("#iframe_00").width()) * 0.7 + 'px', parseInt(parent.$("#iframe_00").height()) * 0.9 + 'px'],
                    title: "交费订单",
                    fixed: false, //不固定
                    maxmin: true,
                    content: wpt_serverName + 'lsjfgl/tjcx/xsddcx/ddxq_info.jsp?type=' + type + '&order_no=' + orderNo + '&xn=' + xn + '&zh=' + zh
                });

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
                    var zh = data.ZH
                    if (layEvent === 'ddxz-qx') {
                        wpt_grjfxx.closeOrderInfo(xn, orderNo, "qx", zh);
                    } else if (layEvent === 'ddxz-cg') {
                        wpt_grjfxx.closeOrderInfo(xn, orderNo, "cg", zh);
                    }
                });
            },
            initDate: function () {
                //初始化开始日期控件
                var start = laydate.render({
                    elem: '#dateStart',
                    max: new Date().toLocaleDateString(),
                    done: function (value, date, endDate) {
                        end.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date
                        }; //重置结束日期最小值
                    }
                });
                //初始化结束日期控件
                var end = laydate.render({
                    elem: '#dateEnd',
                    max: new Date().toLocaleDateString(),
                    done: function (value, date, endDate) {
                        start.config.max = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date
                        }; //重置开始日期最大值
                    }
                });
            },
            bindCli: function () {
                //重置
                $("#my-reset").bind("click", function () {
                    $("#my-header input").val("");
                    $("#my-header select").val("");
                })


                $("#my-search").bind("click", function () {
                    //获取用户名
                    var ddbh = $('#search-ddbh').val();
                    var xn = $('#search-xn').val();
                    var xm = $('#search-xm').val();
                    var ddzt = $('#search-ddzt').val();
                    var xh = $('#search-xh').val();
                    var sfzh = $('#search-sfzh').val();
                    var dateStart = $('#dateStart').val();
                    var dateEnd = $('#dateEnd').val();

                    //执行重载
                    table.reload('userTableReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        //根据条件查询
                        , where: {
                            ddbh: ddbh,
                            xn: xn,
                            xm: xm,
                            ddzt: ddzt,
                            xh: xh,
                            sfzh: sfzh,
                            dateStart: dateStart,
                            dateEnd: dateEnd
                        }
                    });
                })
            }
        }
        wpt_grjfxx.initTab();
        wpt_grjfxx.listenTool();
        wpt_grjfxx.initDate();
        wpt_grjfxx.bindCli();

    });

</script>
</body>
</html>
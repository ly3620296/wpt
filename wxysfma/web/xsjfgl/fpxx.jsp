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
    {{#  if(d.CODE == 'S0000'){ }}
    开票成功
    {{#  } else { }}
    <font color="red">开票失败</font>
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

<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script>
    layui.use(['form', 'layer', 'table'], function () {
        var jQuery = layui.jquery
                , form = layui.form
                , layer = layui.layer
                , table = layui.table
                , $ = layui.jquery

        var wpt_grjfxx = {
            initTab: function () {
                table.render({
                    elem: '#jfjl-table'  //容器id
                    , cols: [
                        [
                            {title: "交费学年", field: "XN", align: "center", fixed: "left"},
                            {title: "开票日期", field: "KPRQ", align: "center"},
                            {title: "缴费项目", field: "JFXM", align: "center"},
                            {title: "缴费合计（元）", field: "JFHJ", align: "center"},
                            {title: "电子票据代码", field: "EBILLCODE", align: "center", width: "13%"},
                            {title: "电子票据号码", field: "EBILLNO", align: "center", width: "10%"},
                            {title: "校验码", field: "CHECKCODE", align: "center", width: "10%"},
                            {title: "开票状态", field: "CODE", align: "center", templet: '#titleTpl', width: "7%"},
                            {
                                title: "操作", align: "center", templet: function () {
                                return '<a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ckfp">查看发票</a>';
                            }, fixed: "right"
                            }
                        ]
                    ]
                    , url: wpt_serverName + 'xsjfgl/fpgl' //数据接口地址
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
            isFlush: function () {
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
                    var layEvent = obj.event; //获得 lay-event 对应的值
                    if (layEvent === 'ckfp') {
                        layer.open({
                            type: 2,
//                            offset: 'l',
                            title: "查看发票",
                            area: ['70%', '90%'],
                            shade: 0,
                            fixed: true,
                            content: ' http://139.215.214.162:18001/billcheck/html/index.html'
                        });
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
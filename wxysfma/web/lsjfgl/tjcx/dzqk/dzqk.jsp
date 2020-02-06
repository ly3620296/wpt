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
            <legend>对账情况</legend>
        </fieldset>
    </div>
</div>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body">
            <div class="layui-form">
                <table class="layui-table" id="jfjl-table" lay-filter="jfFilter">
                </table>
            </div>
        </div>
    </div>
    <%--</div>--%>
</div>

<script type="text/html" id="toolbarDemo">
    <div class="layui-inline myDef" title="刷新" id="refresh">
        <i class="layui-icon layui-icon-refresh" style="font-size: 16px; color: #1E9FFF;"></i>
    </div>

</script>
<script type="text/html" id="barDemo1">
    {{#  if(d.XX == "无对账文件"){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-qx">导出对账</a>
    {{#  } else { }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-cg">导出对账</a>
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
                            {title: "对账时间", field: "DZSJ", align: "center", fixed: "left"},
                            {title: "对账信息", field: "XX", align: "center", templet: '#titleTpl'},
                            {title: "操作", align: "center", templet: "#barDemo1", fixed: "right"}
                        ]
                    ]
                    , url: wpt_serverName + 'lsjfgl/tjcx/dzqk' //数据接口地址
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
            listenTool: function () {
                $("#refresh").bind("click", function () {
                    window.location.reload();
                })
                //监听行工具事件
                table.on('tool(jfFilter)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                    var data = obj.data //获得当前行数据
                            , layEvent = obj.event; //获得 lay-event 对应的值
                    var dzsj = data.DZSJ;
                    if (layEvent === 'ddxz-qx') {
                        alert("暂无对账单!")
                    } else if (layEvent === 'ddxz-cg') {
//                        下载对账单
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
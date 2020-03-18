<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/myCommon.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/myCommon.css">
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
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item" id="my-header">
                <div class="layui-inline">
                    <label class="layui-form-label">账务日期：</label>

                    <div class="layui-input-inline" style="width: 133px;">
                        <input type="text" name="title" placeholder="开始时间" autocomplete="off" class="layui-input"
                               id="dateStart">
                    </div>
                    <div class="layui-input-inline" style="width: 15px">
                        至
                    </div>
                    <div class="layui-input-inline" style="width: 133px;">
                        <input type="text" name="title" placeholder="结束时间" autocomplete="off" class="layui-input"
                               id="dateEnd">
                    </div>
                </div>
                <div class="layui-inline" style="margin-left: 50px;">
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="search" id="my-search">
                        查询
                    </button>
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="reset" id="my-reset">
                        重置
                    </button>
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="export" id="my-export">
                        导出
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
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item" id="my-footer">
                <div class="layui-inline">
                    <label class="layui-form-label"
                           style="width: 200px;font-size: 20px;font-weight: 200">手动对账日期：</label>

                    <div class="layui-input-inline" style="width: 133px;">
                        <input type="text" name="title" placeholder="对账时间" autocomplete="off" class="layui-input"
                               id="myTime">
                    </div>
                    <div class="layui-input-inline" style="width: 15px">

                    </div>
                    <div class="layui-inline" style="margin-left: 50px;">
                        <button class="layui-btn layuiadmin-btn-list" lay-filter="export" id="my-handle">
                            确认对账
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <%--</div>--%>
    </div>
</div>
<script type="text/html" id="toolbarDemo">
    <div class="layui-inline myDef" title="刷新" id="refresh">
        <i class="layui-icon layui-icon-refresh" style="font-size: 16px; color: #1E9FFF;"></i>
    </div>
</script>
<script type="text/html" id="barDemo1">
    {{#  if(d.DZJG == "无对账文件"){ }}
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
                            {title: "账务日期", field: "DZSJ", align: "center", fixed: "left"},
                            {title: "状态", field: "XX", align: "center"},
                            {title: "对账结果", field: "DZJG", align: "center"},
                            {title: "订单已支付条数", field: "TS", align: "center"},
                            {title: "订单合计金额", field: "JE", align: "center"},
                            {title: "微信对账支付条数", field: "WXTS", align: "center"},
                            {title: "微信对账合计金额", field: "WXJE", align: "center"},
                            {title: "异常订单数量", field: "YCBS", align: "center"},
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
                        window.location.href = wpt_serverName + 'lsjfgl/tjcx/dzqk/export?dzsj=' + dzsj; //数据接口地址
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
                //初始化开始日期控件
                var myTime = laydate.render({
                    elem: '#myTime',
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

                $("#my-handle").bind("click", function () {
                    var myTime = $('#myTime').val();
                    if (myTime == "" || myTime == null) {
                        alert("请选择对账时间!")
                    } else {
                        $.ajax({
                            url: wpt_serverName + 'lsjfgl/tjcx/dzqk/sddz', //数据接口地址
                            type: 'post',
                            dataType: 'json',
                            data: {myTime: myTime},
                            timeout: 10000,
                            beforeSend: function () {
                                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                            },
                            success: function (data) {
                                if (data.RETURN_STATE == "SUCCESS") {
                                    layer.alert('对账成功!')
                                } else {
                                    layer.alert(data.RETURN_MSG)
                                }
                            },
                            complete: function () {
                                layer.close(loadIndex);
                            }
                        })
                    }
                })
                $("#my-export").bind("click", function () {
                    window.location.href = wpt_serverName + 'lsjfgl/tjcx/dzqk/exportAll' //数据接口地址
                })
                $("#my-search").bind("click", function () {
                    //获取用户名
                    var dateStart = $('#dateStart').val();
                    var dateEnd = $('#dateEnd').val();

                    //执行重载
                    table.reload('userTableReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        //根据条件查询
                        , where: {
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
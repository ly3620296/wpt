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
            <legend>学生订单查询</legend>
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
                <div class="layui-inline ">
                    <label class="layui-form-label">学年：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-xn" placeholder="学年" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">姓名：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-xm" placeholder="姓名" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">订单状态：</label>

                    <div class="layui-input-inline">
                        <select name="city" lay-verify="required" id="search-ddzt">
                            <option value=""></option>
                            <option value="0">待支付</option>
                            <option value="2">支付成功</option>
                            <option value="3">问题订单</option>
                            <option value="1">已取消</option>
                            <option value="4">教师取消</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">学号：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-xh" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">身份证号：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-sfzh" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">支付时间：</label>

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
<script type="text/html" id="barDemo1">
    {{#  if(d.ORDER_STATE == 1){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-qx">订单详情</a>
    {{#  } else if(d.ORDER_STATE == 2){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-cg">订单详情</a>
    {{#  } else if(d.ORDER_STATE == 3){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-cg">订单详情</a>
    {{#  } else if(d.ORDER_STATE == 4){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="ddxz-cg">订单详情</a>
    {{#  } }}
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
                            wpt_grjfxx.initTabTitles(data.titles);
                        } else {
                            layer.msg(data.msg, {anim: 6, time: 2000});
                        }
                    },
                    complete: function () {
                        layer.close(loadIndex);
                    }
                })
            },
            initTabTitles: function (titles) {
                var col = titles.length + 3;
                var cols2 = [
                    {title: "缴费学年", field: "XN",align: "center", width: "7%", fixed: "left"},
                    {title: "学号", field: "XN", align: "center"},
                    {title: "姓名", field: "XN", align: "center"},
                    {title: "性别", field: "XN", align: "center"},
                    {title: "入学年级", field: "XN", align: "center"},
                    {title: "学院名称", field: "XN", align: "center"},
                    {title: "专业名称", field: "XN", align: "center"},
                    {title: "班级名称", field: "XN", align: "center"},
                    {title: "交费合计",field: "YSHJ", align: "center"}
                ];
                for (var i = 0; i < titles.length; i++) {
                    if (titles[i].SFBX == "1") {
                        cols2[i + 9] = {title: titles[i].JFXMMC, field: titles[i].JFXMID, align: "center"};
                    } else {
                        cols2[i + 9] = {title: titles[i].JFXMMC + "（选交）", field: titles[i].JFXMID, align: "center"};
                    }

                }
                table.render({
                    elem: '#jfjl-table'  //容器id
                    , cols: [cols2]
                    , url: wpt_serverName + 'xsjfgl/wyjf' //数据接口地址
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
            listenTool: function () {
                $("#refresh").bind("click", function () {
                    window.location.reload();
                })
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
        wpt_grjfxx.init();
        wpt_grjfxx.listenTool();
        wpt_grjfxx.initDate();
        wpt_grjfxx.bindCli();
    });

</script>
</body>
</html>
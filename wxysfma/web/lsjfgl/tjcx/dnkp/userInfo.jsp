<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/myCommon.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/commonLs.css">
    <style>
        #my-header .layui-input-inline {
            width: 159px !important;

        }
        .layui-form-item .layui-inline {
            margin-right: 20px !important;
        }
    </style>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<input type="hidden" id="my_status" value="0">

<input type="hidden" id="select_status" value="0">
<jsp:include page="/login/lsauth.jsp"></jsp:include>

<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
    String xn = request.getParameter("xn");
%>


<div class="layui-fluid" style="padding: 0 0px">
    <%--<div class="layui-row">--%>
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item" id="my-header" style="margin-bottom: 0px;">
                <div class="layui-inline ">
                    <label class="layui-form-label">姓名：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-xm" placeholder="姓名" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label">身份证号：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-sfzh" placeholder="身份证号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">学院名称：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-xymc" placeholder="学院名称" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">专业名称：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-zymc" placeholder="专业名称" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">班级名称：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-bjmc" placeholder="班级名称" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <%--<div class="layui-inline">--%>
                <%--<label class="layui-form-label">入学年级：</label>--%>

                <%--<div class="layui-input-inline">--%>
                <%--<select lay-verify="required" id="search-nj">--%>
                <%--</select>--%>
                <%--</div>--%>
                <%--</div>--%>
                <div class="layui-inline my-cx">
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="search" id="my-search">
                        查询
                    </button>
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="reset" id="my-reset">
                        重置
                    </button>
                </div>
            </div>
        </div>
        <div class="layui-card-body" style="padding-top: 0px">
            <div class="layui-form">
                <table class="layui-table" id="jfjl-table" lay-filter="jfFilter" style="margin-top: 0px;">
                </table>
            </div>
        </div>
    </div>
    <%--</div>--%>
</div>


<script type="text/html" id="barDemo1">
    <button type="button" class="layui-btn  layui-btn-normal  layui-btn-sm" lay-event="xq">选择</button>
</script>
<script type="text/html" id="toolbarDemo">
    <font color='red'>当前学年：<%=xn%>
    </font>
    <div class="layui-inline myDef" title="刷新" id="refresh">
        <i class="layui-icon layui-icon-refresh" style="font-size: 16px; color: #1E9FFF;"></i>
    </div>
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
            initTabTitles: function () {
                var cols2 = [
                    {title: "姓名", field: "XM", sort: true, align: "center"},
                    {title: "学号", field: "XH", align: "center"},
                    {title: "性别", field: "XB", align: "center"},
                    {title: "身份证号", field: "SFZH", align: "center"},
                    {title: "学院名称", field: "XYMC", align: "center"},
                    {title: "专业名称", field: "ZYMC", align: "center"},
                    {title: "班级名称", field: "BJMC", align: "center"},
                    {title: "年级", field: "NJ", align: "center"},
                    {title: "操作", field: "NJ", align: "center", templet: "#barDemo1"}
                ];
                table.render({
                    elem: '#jfjl-table'  //容器id
                    , cols: [cols2]
                    , url: wpt_serverName + 'lsjfgl/dnkp/userInfo' //数据接口地址
                    , where: {xn: "<%=xn%>"}
                    , title: '用户表'
                    , height: window.screen.height - 450
                    , page: true //开启分页
                    , limit: 10
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
                    var xh = data.XH;
                    if (layEvent === 'xq') {
                        wpt_grjfxx.changePar(xh);
                    }
                });
            },
            changePar: function (xh) {
                parent.document.getElementById("my_xh").value = xh;
                var index = parent.layer.getFrameIndex(window.name)
                parent.layer.close(index);
            },
            bindCli: function () {
                //重置
                $("#my-reset").bind("click", function () {
                    var xm = $('#search-xm').val(""); //姓名
                    var sfzh = $('#search-sfzh').val(""); //入学年级
                    var xymc = $('#search-xymc').val("");  //学院名称
                    var zymc = $('#search-zymc').val("");  //专业名称
                    var bjmc = $('#search-bjmc').val("");  //班级名称
                })

                //重置
                $("#my-reset").bind("click", function () {
                    $("#my-header input").val("");
                    $("#my-header select").val("");
                })
                $("#my-search").bind("click", function () {
                    var xm = $('#search-xm').val(); //姓名
                    var sfzh = $('#search-sfzh').val(); //入学年级
                    var xymc = $('#search-xymc').val();  //学院名称
                    var zymc = $('#search-zymc').val();  //专业名称
                    var bjmc = $('#search-bjmc').val();  //班级名称

                    //执行重载
                    table.reload('userTableReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        //根据条件查询
                        , where: {
                            xm: xm,
                            sfzh: sfzh,
                            xymc: xymc,
                            zymc: zymc,
                            bjmc: bjmc
                        }
                    });
                })

                $('body').keyup(function (e) {
                    if (e.keyCode === 13) {
                        $('#my-search').click()
                    } else if (e.keyCode === 27) {
                        $('#my-reset').click()
                    }
                })
            }
        }
        wpt_grjfxx.initTabTitles();
        wpt_grjfxx.bindCli();
        wpt_grjfxx.listenTool();
    });

</script>
</body>
</html>
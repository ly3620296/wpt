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
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<input type="hidden" id="my_status" value="0">
<input type="hidden" id="select_status" value="0">
<jsp:include page="/login/lsauth.jsp"></jsp:include>

<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
%>

<div class="layui-fluid">
    <div>
        <fieldset class="layui-elem-field layui-field-title"
                  style="margin-top: 20px;border-color: #009688 !important;">
            <legend>应交费信息查询</legend>
        </fieldset>
    </div>
</div>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item" id="my-header">
                <div class="layui-inline">
                    <label class="layui-form-label">入学年级：</label>

                    <div class="layui-input-inline">
                        <select lay-verify="required" id="search-nj">
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">学号：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-xh" placeholder="学号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label">姓名：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-xm" placeholder="姓名" autocomplete="off" class="layui-input">
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
                    url: wpt_serverName + "lsjfgl/tjcx/yjfxx/title",
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
                    {title: "缴费学年", field: "XN", align: "center", width: "7%", sort: true, fixed: "left"},
                    {title: "学号", field: "XH", align: "center"},
                    {title: "姓名", field: "XM", align: "center", sort: true},
                    {title: "性别", field: "XB", align: "center"},
                    {title: "入学年级", field: "NJ", sort: true, align: "center"},
                    {title: "学院名称", field: "XYMC", align: "center"},
                    {title: "专业名称", field: "ZYMC", align: "center"},
                    {title: "班级名称", field: "BJMC", align: "center"},
                    {title: "交费合计", field: "YSHJ", align: "center"}
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
                    , url: wpt_serverName + 'lsjfgl/tjcx/yjfxx' //数据接口地址
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
                        if ($("#select_status").val() == 0) {
                            var rxnjList = res.rxnjList;
                            var optionsRxnj = "<option value='' selected> 请选择</option>";
                            for (var index in rxnjList) {
                                var rxnj = rxnjList[index].RXNJ;
                                //下拉选定位当前周
                                optionsRxnj += "<option value='" + rxnj + "'>" + rxnj + "</option>";
                            }
                            $("#search-nj").html(optionsRxnj);
                            form.render('select');
                            $("#select_status").val("1")
                        }
                        $("#refresh").bind("click", function () {
                            window.location.reload();
                        })
                    },
                    id: 'userTableReload'
                });
            },
            listenTool: function () {
//                $("#refresh").bind("click", function () {
//                    window.location.reload();
//                })
            },
            bindCli: function () {
                //重置
                $("#my-reset").bind("click", function () {
                    $("#my-header input").val("");
                    $("#my-header select").val("");
                })


                $("#my-search").bind("click", function () {
                    var nj = $('#search-nj').val(); //入学年级
                    var xh = $('#search-xh').val(); //学号
                    var xm = $('#search-xm').val(); //姓名
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
                            nj: nj,
                            xh: xh,
                            xm: xm,
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
        wpt_grjfxx.init();
        wpt_grjfxx.bindCli();
    });

</script>
</body>
</html>
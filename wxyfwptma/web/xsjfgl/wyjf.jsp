<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.controller.login.WptMaUserInfo" %>
<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <%--<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>--%>
</head>
<body>
<jsp:include page="/login/xsauth.jsp"></jsp:include>
<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
%>
<script type="text/html" id="barDemo">
    <a class="layui-btn  layui-btn-normal layui-btn-sm" lay-event="jf">交费</a>
</script>

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
                    url: wpt_serverName + "xsjfgl/grjfxx/title",
                    type: 'post',
                    dataType: 'json',
                    timeout: 10000,
                    beforeSend: function () {
                        loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                    },
                    success: function (data) {
                        if (data.code == "0") {
                            wpt_grjfxx.initTab(data.titles);
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
                    title: "交费合计",
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
                    , page: true //开启分页
                    , loading: true
//                    , even: true  //隔行换色 默认false
                    , done: function (res, curr, count) { //加载完回调
                        $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'})
                    },
                    id: 'userTableReload'
                });
            },
            listenTool: function () {
                //监听行工具事件
                table.on('tool(jfFilter)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                    var data = obj.data //获得当前行数据
                            , layEvent = obj.event; //获得 lay-event 对应的值
                    var xn = data.XN;
                    if (layEvent === 'jf') {
                        layer.open({
                            type: 2,
                            area: ['1150px', '830px'],
                            title: "交费订单",
                            fixed: false, //不固定
                            maxmin: true,
                            content: wpt_serverName + 'xsjfgl/wyjf-pay.jsp?xn='+xn
                        });
                    }
                });
            }

        }
        wpt_grjfxx.init();
        wpt_grjfxx.listenTool();

    });

</script>
</body>
</html>
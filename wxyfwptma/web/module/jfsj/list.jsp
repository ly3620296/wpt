<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/admin.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/myCommon.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <title>缴费时间管理</title>
</head>
<jsp:include page="/login/auth.jsp"></jsp:include>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-card">
            <div class="layui-card-body">
                <table class="layui-hide" id="demo" lay-filter="test"></table>
            </div>
        </div>
    </div>
</div>


<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
</script>
<script src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script>
    var loadIndex
    layui.use(['form', 'laypage', 'layer', 'table', 'element', 'slider', 'jquery'], function () {

        var laypage = layui.laypage //分页
                , layer = layui.layer //弹层
                , table = layui.table //表格
                , $ = layui.jquery
                , form = layui.form;
        table.render({
            elem: '#demo'  //容器id
            , cols: [[   //表头
                {type: 'checkbox', fixed: 'left', hide: true}
                , {field: 'START_TIME', align: 'center', title: '开始时间', fixed: 'left'}
                , {field: 'END_TIME', align: 'center', title: '结束时间'}
                , {align: "center", title: '操作', align: 'center', toolbar: '#barDemo'}
            ]]
            , url: wpt_serverName + 'jfsj/list' //数据接口地址
            , width: '700'
            , title: '用户表'
            , page: false //开启分页
            , toolbar: 'false'   //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            , defaultToolbar: []
            , totalRow: false //开启合计行
            , loading: true
            , even: true  //隔行换色 默认false
            , done: function (res, curr, count) { //加载完回调
                $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'})
            },
            id: 'userTableReload'
        });
        //监听行工具事件
        table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                    , layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'edit') {
                edit()
            }
        });
    });
    function edit() {
        layer.open({
            type: 2,
            skin: 'layui-layer-rim', //加上边框
            title: '编辑缴费时间',
            area: ['70%', '68%'], //宽高
            content: '/wptma/module/jfsj/edit.jsp',  //调到新增页面
            success: function (layero, index) {
                // 新iframe窗口的对象
                var iframeWin = layero.find('iframe')[0].contentWindow;
                // 重新渲染checkbox,select同理
                iframeWin.layui.form.render('checkbox');
            }
        })
    }
    function closeAll() {
        layer.closeAll();
        window.location.href = wpt_serverName + '/module/jfsj/list.jsp';
    }
</script>
</body>
</html>        
        
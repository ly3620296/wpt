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
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <title>工资维护</title>
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
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    <%--<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>--%>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
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
                {type: 'checkbox', fixed: 'left'}
                , {field: 'ID', title: 'ID', sort: true, fixed: 'left', hide: 'true'}
                , {field: 'BT', fixed: 'left', title: '标题'}
                , {field: 'TIME', fixed: 'left', title: '录入时间'}
                , {fixed: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
            ]]
            , url: wpt_serverName + 'gzwh/list' //数据接口地址
            , title: '用户表'
            , page: true //开启分页
            , toolbar: 'default'   //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            , defaultToolbar: []
            , totalRow: false //开启合计行
            , loading: true
            , even: true  //隔行换色 默认false
            , done: function (res, curr, count) { //加载完回调
            },
            id: 'userTableReload'
        });

        //根据条件查询表格数据重新加载
        var active = {
            reload: function () {
                //获取用户名
                var demoReload = $('#username');
                //执行重载
                table.reload('userTableReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    //根据条件查询
                    , where: {
                        username: demoReload.val()
                    }
                });
            }, show: function () {
                alert("11")
                $("#m_url_div").show()
            }
        };
        //监听头工具栏事件
        table.on('toolbar(test)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id)
                    , data = checkStatus.data; //获取选中的数据
            switch (obj.event) {
                case 'add':
                    func7();
                    break;
                case 'update':
                    break;
                case 'delete':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else {
                        layer.confirm('真的删除行么', function (index) {
                            layer.close(index);
                            var idArray = new Array();
                            for (var a = 0; a < data.length; a++) {
                                var my_id = data[a].ID;
                                idArray.push(my_id)
                            }
                            layer.close(index);
                            del(idArray)
                            //向服务端发送删除指令
                        });
                    }
                    break;
            }
        });

        //监听行工具事件
        table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                    , layEvent = obj.event; //获得 lay-event 对应的值

            var id = data.ID
            if (layEvent === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    layer.close(index);
                    var idArray = new Array();
                    idArray.push(id)
                    del(idArray)
                    //向服务端发送删除指令
                });
            }  else if (layEvent == 'detail') {
                detail(id)
            }
        });


        //分页
        laypage.render({
            elem: 'pageDemo' //分页容器的id
            , count: 100 //总页数
            , skin: '#1E9FFF' //自定义选中色值
            //,skip: true //开启跳页
            , jump: function (obj, first) {
                if (!first) {
                    layer.msg('第' + obj.curr + '页', {offset: 'b'});
                }
            }
        });
    });
    function edit(id) {
        layer.open({
            type: 2,
            skin: 'layui-layer-rim', //加上边框
            title: '编辑学院',
            area: ['70%', '68%'], //宽高
            content: '/wptma/module/gzwh/edit.jsp?id=' + id,  //调到新增页面
            success: function (layero, index) {
                // 新iframe窗口的对象
                var iframeWin = layero.find('iframe')[0].contentWindow;
                // 重新渲染checkbox,select同理
                iframeWin.layui.form.render('checkbox');
            }
        })
    }
    function detail(id) {
        layer.open({
            type: 2,
            skin: 'layui-layer-rim', //加上边框
            title: '查看工资',
            area: ['70%', '90%'], //宽高
            content: '/wptma/module/gzwh/table.jsp?id='+id,  //调到新增页面
            success: function (layero, index) {
                // 新iframe窗口的对象
                var iframeWin = layero.find('iframe')[0].contentWindow;
                // 重新渲染checkbox,select同理
                iframeWin.layui.form.render('checkbox');
            }
        })
    }

    function func7() {
        layer.open({
            type: 2,
            skin: 'layui-layer-rim', //加上边框
            title: '添加工资',
            area: ['70%', '90%'], //宽高
            content: '/wptma/module/gzwh/add.jsp',  //调到新增页面
            success: function (layero, index) {
                // 新iframe窗口的对象
//                var iframeWin = layero.find('iframe')[0].contentWindow;
//                // 重新渲染checkbox,select同理
//                iframeWin.layui.form.render('checkbox');
            }
        })
    }

    function closeAll() {
        layer.closeAll();
        window.location.href = wpt_serverName + '/module/gzwh/list.jsp';
    }

    function del(array) {
        $.ajax({
            url: wpt_serverName + "gzwh/del",
            type: 'post',
            dataType: 'json',
            data: {id: array},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    layer.alert('删除成功!', function (index) {
                        window.location.href = wpt_serverName + '/module/gzwh/list.jsp';
                    });
                } else {
                    layer.msg(data.RETURN_MSG, {anim: 6, time: 2000});
                }
            },
            complete: function () {
                layer.close(loadIndex);
            }
        })
    }

</script>
</body>
</html>        
        
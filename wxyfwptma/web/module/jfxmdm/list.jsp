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
    <title>缴费项目</title>
</head>
<jsp:include page="/login/auth.jsp"></jsp:include>
<body>
<div class="layui-form-item" style="margin-top: 3%;">
    <div class="layui-inline">
        <label class="layui-form-label">项目名称</label>

        <div class="layui-input-block">
            <input type="text" id="title" name="title" placeholder="请输入缴费项目名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-inline">
        <button class="layui-btn layuiadmin-btn-useradmin" lay-submit="" lay-filter="LAY-user-front-search"
                id="selectbyCondition" data-type="reload">
            <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
        </button>
    </div>
</div>
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
                , {field: 'ID', title: 'ID', hide: true}
                , {field: 'JFXMID', align: "center", title: '缴费项目代码', sort: true}
                , {field: 'JFXMMC', align: "center", title: '缴费项目名称'}

                , {
                    field: 'JFXMMC', align: "center", title: '是否启用欠费统计', templet: function (data) {
                        var my_type = "JFTJ_" + data.ID;
                        if (data.JFTJ == "1") {
                            return '<input type="checkbox" lay-text="开启|关闭" name="switch" lay-skin="switch" value="' + my_type + '" checked>';
                        } else {
                            return '<input type="checkbox" lay-text="开启|关闭" name="switch" lay-skin="switch" value="' + my_type + '">';
                        }
                    }
                }
                , {
                    field: 'SFBX', align: "center", title: '是否必缴', templet: function (data) {
                        var my_type = "SFBX_" + data.ID;
                        if (data.SFBX == "1") {
                            return '<input type="checkbox" lay-text="是|否" name="switch" lay-skin="switch" value="' + my_type + '" checked>';
                        } else {
                            return '<input type="checkbox" lay-text="是|否" name="switch" lay-skin="switch" value="' + my_type + '">';
                        }
                    }
                }, {
                    field: 'JFXMMC', align: "center", title: '是否启用', templet: function (data) {
                        var my_type = "SFQY_" + data.ID;
                        if (data.SFQY == "1") {
                            return '<input type="checkbox" lay-text="是|否" name="switch" lay-skin="switch" value="' + my_type + '" checked>';
                        } else {
                            return '<input type="checkbox" lay-text="是|否" name="switch" lay-skin="switch" value="' + my_type + '">';
                        }

                    }
                }
                , {fixed: 'right', align: "center", title: '操作', align: 'center', toolbar: '#barDemo'}
            ]]
            , url: wpt_serverName + 'jfxmdm/list' //数据接口地址
            , title: '用户表'
            , page: true //开启分页
            , toolbar: 'default'   //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            , defaultToolbar: []
            , totalRow: false //开启合计行
            , loading: true
            , even: true  //隔行换色 默认false
            , done: function (res, curr, count) { //加载完回调
                $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'})
            },
            id: 'userTableReload'
        });

        form.on('switch()', function (data) {
            var my_val = data.value;
            var my_type = my_val.split("_")[0];
            var my_id = my_val.split("_")[1];
            var my_status = data.elem.checked ? "1" : "0";
            update_status(my_type, my_id, my_status);

        });
        //根据条件查询表格数据重新加载
        var active = {
            reload: function () {
                //获取用户名
                var demoReload = $('#title');
                //执行重载
                table.reload('userTableReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    //根据条件查询
                    , where: {
                        JFXMMC: demoReload.val()
                    }
                });
            }, show: function () {
//                $("#m_url_div").show()
            }
        };

        //点击搜索按钮根据用户名称查询
        $('#selectbyCondition').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //监听头工具栏事件
        table.on('toolbar(test)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id)
                    , data = checkStatus.data; //获取选中的数据
            switch (obj.event) {
                case 'add':
                    add();
                    break;
                case 'update':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        edit(checkStatus.data[0].ID);
                    }
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
            var id = data.ID;
            if (layEvent === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    layer.close(index);
                    var idArray = new Array();
                    idArray.push(id)
                    del(idArray)
                    //向服务端发送删除指令
                });
            } else if (layEvent === 'edit') {
                edit(id)
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

    function add() {
        layer.open({
            type: 2,
            skin: 'layui-layer-lan', //加上边框
            title: '添加缴费项目代码',
            area: ['40%', '45%'], //宽高
            content: '/wptma/module/jfxmdm/add.jsp',  //调到新增页面
            success: function (layero, index) {
                // 新iframe窗口的对象
                var iframeWin = layero.find('iframe')[0].contentWindow;
                // 重新渲染checkbox,select同理
                iframeWin.layui.form.render('checkbox');
            }
        })
    }

    function del(array) {
        $.ajax({
            url: wpt_serverName + "jfxmdm/del",
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
                        window.location.href = wpt_serverName + '/module/jfxmdm/list.jsp';
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
    function edit(id) {
        layer.open({
            type: 2,
            skin: 'layui-layer-lan', //加上边框
            title: '编辑缴费项目代码',
            area: ['40%', '45%'], //宽高
            content: '/wptma/module/jfxmdm/edit.jsp?id=' + id,  //调到新增页面
            success: function (layero, index) {
                // 新iframe窗口的对象
                var iframeWin = layero.find('iframe')[0].contentWindow;
                // 重新渲染checkbox,select同理
                iframeWin.layui.form.render('checkbox');
            }
        })
    }

    function update_status(my_type, my_id, my_status) {
        $.ajax({
            url: wpt_serverName + "jfxmdm/updateStatus",
            type: 'post',
            dataType: 'json',
            data: {my_id: my_id, my_type: my_type, my_status: my_status},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {

            },
            complete: function () {
                layer.close(loadIndex);
            }
        })
    }
    function closeAll() {
        layer.closeAll();
        window.location.href = wpt_serverName + '/module/jfxmdm/list.jsp';
    }
</script>
</body>
</html>        
        
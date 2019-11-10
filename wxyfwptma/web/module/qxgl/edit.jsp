<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-v2.5.4/css/layui.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-v2.5.4/layui.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<jsp:include page="/login/auth.jsp"></jsp:include>
<form class="layui-form" action="" lay-filter="example">
    <div class="layui-form-item">
        <label class="layui-form-label">权限名称</label>

        <div class="layui-input-block">
            <input style="display: none" type="text" name="q_id" lay-filter="q_id" value="">
        </div>
        <div class="layui-form-mid layui-word-aux" style="margin-top: -36px;" id="q_name_text"></div>
    </div>
    <div id="test12" class="demo-tree-more" style="margin-left: 55px;"></div>

    <div class="layui-form-item">
        <div class="layui-input-block" style="margin-top: 24px;">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use(['tree', 'util', 'form'], function () {
        var form = layui.form;
        var tree = layui.tree
                , util = layui.util
        //按钮事件
        util.event('lay-demo', {
            getChecked: function (othis) {
                var checkedData = tree.getChecked('demoId1'); //获取选中节点的数据
                layer.alert(JSON.stringify(checkedData), {shade: 0});
                console.log(checkedData);
            }
            , setChecked: function () {
                tree.setChecked('demoId1', [12, 16]); //勾选指定节点
            }
            , reload: function () {
                //重载实例
                tree.reload('demoId1', {});
            }
        });
        var loadIndex;
        var id = getURLParameter("id");
        if (id == undefined || id == "" || id == null) {
            layer.alert('查询数据失败!', function (index) {
                parent.closeAll()
            });
            return false;
        }
        $.ajax({
            url: wpt_serverName + "qxgl/query",
            type: 'post',
            dataType: 'json',
            data: {id: id},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    $("#q_name_text").html(data.OUT_DATA.re.Q_NAME)
                    form.val('example', {
                        "q_id": data.OUT_DATA.re.Q_ID // "name": "value"
                    })
                    form.render()
                    var menuTree = data.OUT_DATA.menuTree;
                    if (menuTree != '') {
                        tree.render({
                            elem: '#test12'
                            , data: menuTree
                            , showCheckbox: true  //是否显示复选框
                            , id: 'demoId1'
                        });
                        var this_re = data.OUT_DATA.this_re;
                        var allFatherMenu = data.OUT_DATA.allFatherMenu;
                        if (this_re.length > 0) {
                            var myMenu = new Array();
                            for (var i = 0; i < this_re.length; i++) {
                                var m_id = Number(this_re[i].M_ID);
                                console.log(m_id)
//                                if (m_id != 14)
                                var bool = false;
                                for (var a = 0; a < allFatherMenu.length; a++) {
                                    var fatherID = Number(allFatherMenu[a].M_ID);
                                    if (fatherID == m_id) {
                                        bool = true;
                                        break;
                                    }
                                }
                                if (!bool)
                                    myMenu.push(m_id)
                            }
                            console.log(myMenu)
                            tree.setChecked('demoId1', myMenu); //勾选指定节点
                        }
                    } else {
                        layer.alert('暂无可以编辑的菜单!', function (index) {
                            parent.closeAll()
                        });
                    }
                } else {
                    layer.alert('数据查询失败!', function (index) {
                        parent.closeAll()
                    });
                }
            },
            complete: function () {
                layer.close(loadIndex);
            }
        })
        //监听提交
        form.on('submit(formDemo)', function (data) {
            var menu = new Array();
            var checkedData = tree.getChecked('demoId1');
//            layer.alert(JSON.stringify(checkedData), {shade: 0});
            if (checkedData.length > 0) {
                for (var i = 0; i < checkedData.length; i++) {
                    var id = checkedData[i].id;
                    menu.push(id)
                    var children = checkedData[i].children;
                    for (var a = 0; a < children.length; a++) {
                        var cid = children[a].id;
                        menu.push(cid)
                    }
                }
            } else {
                layer.alert("请选择菜单按钮!", {shade: 0});
                return false;
            }
            $.ajax({
                url: wpt_serverName + "qxgl/edit",
                type: 'post',
                dataType: 'json',
                data: {menu: menu, jsid: data.field.q_id},
                timeout: 10000,
                beforeSend: function () {
                    loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                },
                success: function (data) {
                    if (data.RETURN_STATE == "SUCCESS") {
                        layer.alert('修改成功', function (index) {
                            parent.closeAll()
                        });
                    } else {
                        layer.msg(data.RETURN_MSG, {anim: 6, time: 2000});
                    }
                },
                complete: function () {
                    layer.close(loadIndex);
                }
            })
            return false
        });
    });
</script>
</body>
</html>
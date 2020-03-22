<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
<jsp:include page="/login/lsauth.jsp"></jsp:include>
<form class="layui-form" action="" lay-filter="example">
    <div class="layui-form-item">
        <label class="layui-form-label">菜单等级</label>

        <div class="layui-input-block">
            <input style="display: none" type="text" name="m_level" lay-filter="m_level" value="">
            <input style="display: none" type="text" name="m_id" lay-filter="m_id" value="">
        </div>
        <div class="layui-form-mid layui-word-aux" style="margin-top: -36px;" id="m_level_text">辅助文字</div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">菜单名称</label>

        <div class="layui-input-inline">
            <input type="text" name="m_name" required lay-verify="required" placeholder="请输入菜单名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item" id="m_father_div" style="display: none">
        <label class="layui-form-label">父级菜单</label>

        <div class="layui-input-inline">
            <select name="m_father" id="m_father">

            </select>
        </div>
    </div>
    <div class="layui-form-item layui-form-text" id="m_url_div" style="display: none">
        <label class="layui-form-label">菜单路径</label>

        <div class="layui-input-inline">
            <input type="text" name="m_url" placeholder="请输入菜单路径" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单状态</label>

        <div class="layui-input-block">
            <input type="radio" name="m_state" value="1" title="在线" checked>
            <input type="radio" name="m_state" value="0" title="下线">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use('form', function () {
        var form = layui.form;
        var loadIndex;
        var id = getURLParameter("id");
        if (id == undefined || id == "" || id == null) {
            layer.alert('查询数据失败!', function (index) {
                parent.closeAll()
            });
            return false;
        }
        $.ajax({
            url: wpt_serverName + "menu/query",
            type: 'post',
            dataType: 'json',
            data: {id: id},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    var allFather = data.OUT_DATA.allFather;
                    var html = '<option value=""></option>'
                    for (var i = 0; i < allFather.length; i++) {
                        html += '<option value="' + allFather[i].M_ID + '">' + allFather[i].M_NAME + '</option>'
                    }
                    $("#m_father").html(html)

                    if (data.OUT_DATA.thMenu.M_LEVEL == '2') {
                        $("#m_url_div").show()
                        $("#m_father_div").show()
                    }

                    $("#m_level_text").html(data.OUT_DATA.thMenu.M_LEVEL)
                    form.val('example', {
                        "m_id": data.OUT_DATA.thMenu.M_ID // "name": "value"
                        ,"m_level": data.OUT_DATA.thMenu.M_LEVEL // "name": "value"
                        , "m_name": data.OUT_DATA.thMenu.M_NAME
                        , "m_father": data.OUT_DATA.thMenu.M_FATHER
                        , "m_url": data.OUT_DATA.thMenu.M_URL
                        , "m_state": data.OUT_DATA.thMenu.M_STATE
                    })
                    form.render()
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
            if(data.field.m_level=='2'&&data.field.m_father=='') {
                layer.alert("请选择父类菜单!")
                return false;
            }
//            layer.alert(JSON.stringify(data.field));
            $.ajax({
                url: wpt_serverName + "menu/edit",
                type: 'post',
                dataType: 'json',
                data: data.field,
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
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
<form class="layui-form" action="" style="margin-top: 30px">
    <div class="layui-form-item">
        <label class="layui-form-label">菜单等级</label>

        <div class="layui-input-block">
            <input type="radio" name="m_level" lay-filter="m_level" value="1" title="一级菜单" checked>
            <input type="radio" name="m_level" lay-filter="m_level" value="2" title="二级菜单">
        </div>
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
        <label class="layui-form-label">公告状态</label>

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
    //Demo
    layui.use(['form', 'element', 'laydate'], function () {
        var jQuery = layui.jquery
                , layer = layui.layer
                , form = layui.form
                , element = layui.element;
        var loadIndex;
        var faBool = true
        $.ajax({
            url: wpt_serverName + "menu/father",
            type: 'post',
            dataType: 'json',
            data: {},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    var html = '<option value=""></option>'
                    for (var i = 0; i < data.OUT_DATA.length; i++) {
                        html += '<option value="' + data.OUT_DATA[i].M_ID + '">' + data.OUT_DATA[i].M_NAME + '</option>'
                    }
                    $("#m_father").html(html)
                    form.render()
                } else {
                    faBool = false
                    layer.alert(data.RETURN_MSG)
                }
            },
            complete: function () {
                layer.close(loadIndex);
            }
        })
        //监听提交
        form.on('submit(formDemo)', function (data) {
//            layer.msg(JSON.stringify(data.field));
            if (faBool||data.field.m_level=='1') {
                if(data.field.m_level=='2'&&data.field.m_father=='') {
                    layer.alert("请选择父类菜单!")
                    return false;
                }
                $.ajax({
                    url: wpt_serverName + "menu/add",
                    type: 'post',
                    dataType: 'json',
                    data: data.field,
                    timeout: 10000,
                    beforeSend: function () {
                        loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                    },
                    success: function (data) {
                        if (data.RETURN_STATE == "SUCCESS") {
                            layer.alert('添加成功!', function (index) {
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
            } else {
                layer.alert("暂无父级菜单，请先添加父级菜单!")
            }
            return false
        });
        form.on("radio(m_level)", function (data) {
            if (this.value == '1') {
                $("#m_url_div").hide()
                $("#m_father_div").hide()
                if(!faBool){
                    faBool=true
                }
            } else if (this.value == '2') {
                $("#m_url_div").show()
                $("#m_father_div").show()
                if(!faBool){
                    faBool=false
                }
            }
        });
    });

</script>
</body>
</html>
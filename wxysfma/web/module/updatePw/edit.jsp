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
<jsp:include page="/login/auth.jsp"></jsp:include>
<form class="layui-form" action="" lay-filter="example" id="this_form">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend>修改密码</legend>
    </fieldset>
    <div class="layui-form-item">
        <label class="layui-form-label">原密码</label>

        <div class="layui-input-inline">
            <input type="password" name="old_pw" required lay-verify="required" placeholder="请输入原密码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">新密码</label>

        <div class="layui-input-inline">
            <input type="password" name="new_pw_1" required lay-verify="required" placeholder="请输入新密码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">确认新密码</label>

        <div class="layui-input-inline">
            <input type="password" name="new_pw_2" required lay-verify="required" placeholder="请确认新密码" autocomplete="off"
                   class="layui-input">
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
        //监听提交
        form.on('submit(formDemo)', function (data) {
//            layer.msg(JSON.stringify(data.field));
            if (data.field.new_pw_2 != data.field.new_pw_1) {
                layer.alert('两次新密码输入不一致!');
                return false;
            }
            $.ajax({
                url: wpt_serverName + "pwd/updatePw",
                type: 'post',
                dataType: 'json',
                data: data.field,
                timeout: 10000,
                beforeSend: function () {
                    loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                },
                success: function (data) {
                    if (data.RETURN_STATE == "SUCCESS") {
                        layer.alert('修改成功');
                    } else {
                        layer.msg(data.RETURN_MSG, {anim: 6, time: 2000});
                    }
                    document.getElementById("this_form").reset();
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
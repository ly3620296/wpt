<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <style>
        html {
            background-color: #fbfbfb !important;
            color: #333;
        }

    </style>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<jsp:include page="/login/auth.jsp"></jsp:include>
<form class="layui-form" action="">
    <div class="layui-form-item" style="margin-top: 5%">
        <div class="layui-inline">
            <label class="layui-form-label">学年名称</label>

            <div class="layui-input-inline">
                <input name="XNMC" required lay-verify="required" autocomplete="off" class="layui-input" placeholder="学年名称">
            </div>
        </div>
        <%--<div class="layui-inline" style="margin-top: 30px">--%>
        <%--<label class="layui-form-label">验证邮箱</label>--%>
        <%--<div class="layui-input-inline">--%>
        <%--<input type="text" name="email" lay-verify="email" autocomplete="off" class="layui-input">--%>
        <%--</div>--%>
        <%--</div>--%>
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
        //监听提交
        form.on('submit(formDemo)', function (data) {
            $.ajax({
                url: wpt_serverName + "xnwh/add",
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
            return false
        });
    });

</script>
</body>
</html>
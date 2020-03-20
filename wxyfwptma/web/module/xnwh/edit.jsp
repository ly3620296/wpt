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
    <input type="text" name="XNID" style="display: none">

    <div class="layui-form-item" style="margin-top: 30px">
        <div class="layui-inline">
            <label class="layui-form-label">学年名称</label>

            <div class="layui-input-inline">
                <input  name="XNMC" required lay-verify="required" autocomplete="off" class="layui-input"
                       placeholder="学年名称">
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
        <div class="layui-input-block" style="margin-top: 24px;">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <%--<button type="reset" class="layui-btn layui-btn-primary">重置</button>--%>
        </div>
    </div>
</form>

<script>
    layui.use(['tree', 'util', 'form'], function () {
        var form = layui.form;
        var tree = layui.tree
                , util = layui.util
        //按钮事件
        var loadIndex;
        var id = getURLParameter("id");
        if (id == undefined || id == "" || id == null) {
            layer.alert('查询数据失败!', function (index) {
                parent.closeAll()
            });
            return false;
        }
        $.ajax({
            url: wpt_serverName + "xnwh/query",
            type: 'post',
            dataType: 'json',
            data: {id: id},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
//                    $("#xmlxid_text").html(data.OUT_DATA.XMLXID)
                    form.val('example', {
                        "XNID": data.OUT_DATA.XNID,
                        "XNMC": data.OUT_DATA.XNMC
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
            $.ajax({
                url: wpt_serverName + "xnwh/edit",
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
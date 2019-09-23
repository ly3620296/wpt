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
<form class="layui-form" action="">
    <div class="layui-form-item layui-form-text" style="margin-top: 5%;">
        <label class="layui-form-label">项目名称</label>

        <div class="layui-input-inline">
            <input type="text" name="xmmc" required lay-verify="required" placeholder="请输入项目名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">项目金额</label>

        <div class="layui-input-inline">
            <input type="text" name="xmje" required lay-verify="required|number" placeholder="请输入项目金额" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">项目类型</label>

        <div class="layui-input-inline">
            <select name="xmlxid" id="xmlxid" required lay-verify="required">

            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否必缴</label>

        <div class="layui-input-block">
            <input type="radio" name="sfbx" value="1" title="是" checked>
            <input type="radio" name="sfbx" value="0" title="否">
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
            url: wpt_serverName + "jfxm/queryjfxmlx",
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
                        html += '<option value="' + data.OUT_DATA[i].XMLXID + '">' + data.OUT_DATA[i].XMLXMC + '</option>'
                    }
                    $("#xmlxid").html(html)
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
                $.ajax({
                    url: wpt_serverName + "jfxm/add",
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
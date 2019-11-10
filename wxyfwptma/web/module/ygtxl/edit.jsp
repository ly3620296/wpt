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
<form class="layui-form" action="" lay-filter="example">
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">员工姓名</label>

        <div class="layui-input-inline">
            <input type="text" name="xm" required lay-verify="required" placeholder="请输入员工姓名" autocomplete="off"
                   class="layui-input">
            <input type="text" name="id"style="display: none">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">员工电话</label>

        <div class="layui-input-inline">
            <input type="text" name="dh" required lay-verify="required|number" placeholder="请输入员工电话"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所属学院</label>

        <div class="layui-input-inline">
            <select name="xyid" id="xyid" required lay-verify="required">

            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
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
            url: wpt_serverName + "ygtxl/query",
            type: 'post',
            dataType: 'json',
            data: {id: id},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    var xy = data.OUT_DATA.xy;
                    var html = '<option value=""></option>'
                    for (var i = 0; i < xy.length; i++) {
                        html += '<option value="' + xy[i].X_CODE + '">' + xy[i].X_NAME + '</option>'
                    }
                    $("#xyid").html(html)
                    form.val('example', {
                        "id": data.OUT_DATA.ygtxl.ID // "name": "value"
                        ,"xm": data.OUT_DATA.ygtxl.XM // "name": "value"
                        , "dh": data.OUT_DATA.ygtxl.DH // "name": "value"
                        , "xyid": data.OUT_DATA.ygtxl.XYID

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
                url: wpt_serverName + "ygtxl/edit",
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
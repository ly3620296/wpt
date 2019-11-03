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
</head>
<body>
<jsp:include page="/login/auth.jsp"></jsp:include>

<form class="layui-form" action="" lay-filter="example">
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">用户名称</label>

        <div class="layui-input-inline">
            <input type="text" name="m_name" required lay-verify="required" placeholder="请输入用户名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">用户账号</label>

        <div class="layui-input-block">
            <input style="display: none" type="text" name="m_id" lay-filter="m_id" value="">
        </div>
        <div class="layui-form-mid layui-word-aux" style="margin-top: -36px;" id="m_zh_text"></div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">用户权限</label>
        <div class="layui-input-inline">
            <select name="m_qx" id="m_qx" required lay-verify="required">

            </select>
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
            url: wpt_serverName + "qxgl/queryQxgl",
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
                        html += '<option value="' + data.OUT_DATA[i].Q_ID + '">' + data.OUT_DATA[i].Q_NAME + '</option>'
                    }
                    $("#m_qx").html(html)
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
        $.ajax({
            url: wpt_serverName + "user/query",
            type: 'post',
            dataType: 'json',
            data: {id: id},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS"){
                    $("#m_zh_text").html(data.OUT_DATA.M_ZH)
                    form.val('example', {
                        "m_id": data.OUT_DATA.M_ID // "name": "value"
                        ,"m_name": data.OUT_DATA.M_NAME // "name": "value"
                        , "m_zh": data.OUT_DATA.M_ZH
                        , "m_qx": data.OUT_DATA.M_QX
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
                url: wpt_serverName + "user/edit",
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
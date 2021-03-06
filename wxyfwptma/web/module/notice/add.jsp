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
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend>新增公告</legend>
    </fieldset>
    <div class="layui-form-item">
        <label class="layui-form-label">公告标题</label>

        <div class="layui-input-inline">
            <input type="text" name="g_title" required lay-verify="required" placeholder="请输入标题" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">公告内容</label>

        <div class="layui-input-inline">
            <textarea name="g_text" lay-verify="required" placeholder="请输入内容" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所属学院</label>

        <div class="layui-input-inline">
            <select name="g_xy" id="g_xy">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">公告群体</label>

        <div class="layui-input-block">
            <input type="radio" name="g_group" value="00" title="全部" checked>
            <input type="radio" name="g_group" value="01" title="老师">
            <input type="radio" name="g_group" value="02" title="学生">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">公告状态</label>

        <div class="layui-input-block">
            <input type="radio" name="g_state" value="1" title="在线" checked>
            <input type="radio" name="g_state" value="0" title="下线">
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
    layui.use('form', function () {
        var form = layui.form;
        var loadIndex;
        $(function () {
            $.ajax({
                url: wpt_serverName + "notice/queryXy",
                type: 'post',
                dataType: 'json',
                data: {},
                timeout: 10000,
                beforeSend: function () {
                    loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                },
                success: function (data) {
                    if (data.RETURN_STATE == "SUCCESS") {
                        var xy = data.OUT_DATA
                        var html = '<option value=""></option>'
                        for (var i = 0; i < xy.length; i++) {
                            html += '<option value="' + xy[i].X_CODE + '">' + xy[i].X_NAME + '</option>'
                        }
                        console.log(html)
                        $("#g_xy").html(html)
                        form.render()
                    } else {
                        layer.alert('暂无学院信息，请先录入学院后再发布公告!', function (index) {
                            window.location.href = wpt_serverName + '/module/notice/list.jsp';
                        });
                    }
                },
                complete: function () {
                    layer.close(loadIndex);
                }
            })
        })
        form.on('submit(formDemo)', function (data) {
            $.ajax({
                url: wpt_serverName + "notice/add",
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
                            window.location.href = wpt_serverName + '/module/notice/list.jsp';
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
    function show(str) {
        console.log(str)
//        $("#g_xy").html(str)
        document.getElementById("g_xy").innerHTML = str
    }
</script>
</body>
</html>
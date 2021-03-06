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
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend>新增公告</legend>
    </fieldset>
    <div class="layui-form-item">
        <input type="text" name="g_id" id="g_id" style="display: none">
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
            <input type="radio" name="g_group" value="00" title="全部">
            <input type="radio" name="g_group" value="01" title="老师">
            <input type="radio" name="g_group" value="02" title="学生">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">公告状态</label>

        <div class="layui-input-block">
            <input type="radio" name="g_state" value="1" title="在线">
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


    //表单初始赋值


    layui.use('form', function () {


        var form = layui.form;
        var loadIndex;

        var id = getURLParameter("id");
        console.log(id)
        if (id == undefined || id == "" || id == null) {
            window.location.href = wpt_serverName + '/module/notice/list.jsp';
        }
         $("#g_id").val(id)
        $.ajax({
            url: wpt_serverName + "notice/query",
            type: 'post',
            dataType: 'json',
            data: {id:id},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    var xy = data.OUT_DATA.xy
                    var html = '<option value=""></option>'
                    for (var i = 0; i < xy.length; i++) {
                        html += '<option value="' + xy[i].X_CODE + '">' + xy[i].X_NAME + '</option>'
                    }
                    $("#g_xy").html(html)
                    form.val('example', {
                        "g_title": data.OUT_DATA.re.G_TITLE // "name": "value"
                        , "g_text": data.OUT_DATA.re.G_TEXT
                        , "g_state": data.OUT_DATA.re.G_STATE
                        , "g_xy": data.OUT_DATA.re.G_XY
                        , "g_group": data.OUT_DATA.re.G_GROUP
                    })
                } else {
                    layer.alert('数据查询失败!', function (index) {
                        window.location.href = wpt_serverName + '/module/notice/list.jsp';
                    });
                }
            },
            complete: function () {
                layer.close(loadIndex);
            }
        })


        //监听提交
        form.on('submit(formDemo)', function (data) {
//            layer.msg(JSON.stringify(data.field));
            $.ajax({
                url: wpt_serverName + "notice/edit",
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
</script>
</body>
</html>
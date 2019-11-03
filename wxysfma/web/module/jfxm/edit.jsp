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
    <div class="layui-form-item">
        <label class="layui-form-label">项目ID</label>

        <div class="layui-input-block">
            <input style="display: none" type="text" name="xmid" lay-filter="m_level" value="">
        </div>
        <div class="layui-form-mid layui-word-aux" style="margin-top: -36px;" id="xmid_text"></div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">项目名称</label>

        <div class="layui-input-inline">
            <input type="text" name="xmmc" required lay-verify="required" placeholder="请输入项目名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">项目金额</label>

        <div class="layui-input-inline">
            <input type="text" name="xmje" required lay-verify="required|number" placeholder="请输入项目金额"
                   autocomplete="off"
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
            url: wpt_serverName + "jfxm/query",
            type: 'post',
            dataType: 'json',
            data: {id: id},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    var jfxmlx = data.OUT_DATA.jfxmlx;
                    var html = '<option value=""></option>'
                    for (var i = 0; i < jfxmlx.length; i++) {
                        html += '<option value="' + jfxmlx[i].XMLXID + '">' + jfxmlx[i].XMLXMC + '</option>'
                    }
                    $("#xmlxid").html(html)
                    $("#xmid_text").html(data.OUT_DATA.jfxm.XMID)
                    form.val('example', {
                        "xmid": data.OUT_DATA.jfxm.XMID // "name": "value"
                        , "xmmc": data.OUT_DATA.jfxm.XMMC // "name": "value"
                        , "xmje": data.OUT_DATA.jfxm.XMJE
                        , "xmlxid": data.OUT_DATA.jfxm.XMLXID
                        , "sfbx": data.OUT_DATA.jfxm.SFBX
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
                url: wpt_serverName + "jfxm/edit",
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
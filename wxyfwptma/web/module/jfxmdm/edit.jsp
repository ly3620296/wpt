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
    <input type="text" name="ID" style="display: none">

    <div class="layui-form-item layui-form-text" style="margin-top: 5%;">
        <label style="width: 130px;" class="layui-form-label">缴费项目代码</label>

        <div class="layui-input-inline">
            <input type="text" name="JFXMID" required lay-verify="required" placeholder="请输入缴费项目代码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text" style="margin-top: 5%;">
        <label style="width: 130px;" class="layui-form-label">缴费项目名称</label>

        <div class="layui-input-inline">
            <input type="text" name="JFXMMC" required lay-verify="required" placeholder="请输入缴费项目名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item" style="margin-top: 5%;">
        <label style="width: 130px;" class="layui-form-label">是否必缴</label>

        <div class="layui-input-block">
            <input type="radio" name="SFBX" value="1" title="是" checked>
            <input type="radio" name="SFBX" value="0" title="否">
        </div>
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
            url: wpt_serverName + "jfxmdm/query",
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
                        "ID": data.OUT_DATA.ID,
                        "JFXMID": data.OUT_DATA.JFXMID,
                        "JFXMMC": data.OUT_DATA.JFXMMC,
                        "SFBX": data.OUT_DATA.SFBX
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
                url: wpt_serverName + "jfxmdm/edit",
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
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
<%--<jsp:include page="/login/auth.jsp"></jsp:include>--%>
<div class="layui-tab layui-tab-card">
    <ul class="layui-tab-title">
        <li class="layui-this">表单录入</li>
        <li>表格导入</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form class="layui-form" action="">
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">姓名</label>

                    <div class="layui-input-inline">
                        <input type="text" name="xm" required lay-verify="required" placeholder="请输入姓名"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">电话号码</label>

                    <div class="layui-input-inline">
                        <input type="text" name="dh" required lay-verify="required|number" placeholder="请输入电话号码"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">部门名称</label>

                    <div class="layui-input-inline">
                        <select name="xyid" id="xyid" required lay-verify="required">

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
        </div>
        <div class="layui-tab-item">
            <%--<div class="layui-fluid">--%>
            <div class="layui-row">
                <div class="layui-card">
                    <div class="layui-card-header" style="height: 74px">
                        <span style="color: red;">温馨提示：模板中的学院编码可在【权限管理->学院管理】中查询对应关系</span></br>
                        <i class="layui-icon   layui-icon-link"></i>
                        <a style="text-decoration:underline;color: #0000FF" href="通讯录导入模板.xls">
                            下载通讯录导入模板</a>
                    </div>
                    <div class="layui-card-body">
                        <%--<a style="text-decoration:underline;color: #0000FF" href="用户导入模板.xls">下载模板</a>--%>

                        <div class="layui-upload-drag" id="test3" style="width: 388px;height: 240px;">
                            <i class="layui-icon"
                               style="    display: inline-block;margin-top: 70px;font-size: 55px;"></i>

                            <p>点击上传，或将文件拖拽到此处</p>
                        </div>

                    </div>
                    <button type="button" class="layui-btn" style="margin-bottom: 10px;margin-left: 14px;width: 452px;"
                            id="btn" lay-submit lay-filter="formDemo">立即提交
                    </button>

                    <%--<button class="layui-btn" >立即提交</button>--%>
                </div>
                <%--</div>--%>
            </div>
            <%--<div class="layui-form-item layui-form-text">--%>
            <%--<label class="layui-form-label">选择文件:</label>--%>

            <%--<div class="button-hide">--%>
            <%--<button type="button" class="layui-btn" id="test3"><i class="layui-icon"></i>上传文件</button>--%>
            <%--<a style="text-decoration:underline;color: #0000FF" href="通讯录导入模板.xls">下载模板</a>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-form-item layui-form-text">--%>
            <%--<div class="button-hide">--%>
            <%--<span style="color: red;margin-left: 61px;">注意:模板中的学院编码可在学院管理中查询对应关系</span>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-form-item">--%>
            <%--<div class="layui-input-block">--%>
            <%--<button class="layui-btn" id="btn">立即提交</button>--%>
            <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
</div>


<script>
    //Demo
    layui.use(['form', 'element', 'laydate', 'upload'], function () {
        var upload = layui.upload;
        var jQuery = layui.jquery
                , layer = layui.layer
                , form = layui.form
                , element = layui.element;
        var loadIndex;
        var faBool = true

        upload.render({
            elem: '#test3'
            , url: "/wptma/uptxl/upload"
            , accept: 'file' //普通文件
            , exts: 'xls|xlsx'//允许上传的文件后缀
            , multiple: true
            , auto: false
            , bindAction: '#btn'
            , before: function (obj) {
                console.log('文件上传中');
                layer.load();
            }
            , done: function (res) {
                layer.closeAll('loading');
                layer.alert(res.OUT_DATA, function (index) {
                    parent.closeAll()
                });
            }
            , error: function (data) {
                layer.msg("上传失败");
                console.log(data);
            }
        });


        $.ajax({
            url: wpt_serverName + "ygtxl/queryjfxmlx",
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
                        html += '<option value="' + data.OUT_DATA[i].X_CODE + '">' + data.OUT_DATA[i].X_NAME + '</option>'
                    }
                    $("#xyid").html(html)
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
                url: wpt_serverName + "ygtxl/add",
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
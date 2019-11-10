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
    <div class="layui-form-item layui-form-text" style="margin-top: 45px;">
        <label class="layui-form-label">选择文件:</label>

        <div class="button-hide">
            <button type="button" class="layui-btn" id="test3"><i class="layui-icon"></i>上传文件</button>
            <a style="text-decoration:underline;color: #0000FF" href="用户导入模板.xls">下载模板</a>
        </div>
    </div>

    <div class="layui-form-item" style="margin-top: 45px;">
        <div class="layui-input-block">
            <button class="layui-btn" id="btn" lay-submit lay-filter="formDemo">立即提交</button>
        </div>
    </div>


<script type="text/javascript">
    layui.use('upload', function () {
        var upload = layui.upload;
        upload.render({
            elem: '#test3'
            , url: "/wptma/scyh/upload"
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
                    layer.closeAll()
                });
            }
            , error: function (data) {
                layer.msg("上传失败");
                console.log(data);
            }
        });
    });

</script>
</body>
</html>
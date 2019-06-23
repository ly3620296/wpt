<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/admin.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
</head>
<body>
<div class="layui-form-item" id="upload_file">
     
    <div class="layui-input-block" style="width: 300px;">
          
        <input type="hidden" id="img_url1" name="HeadImageUrl" value=""/>

        <div class="layui-upload-drag" id="uploadpic1" lay-verify="uploadpic1">
            <div class="layui-col-xs12 layui-col-md12">
                <img class="layui-upload-img" id="demo1">
            </div>
            <div class="button-hide">
                <%--<input type="file" name="banner_file_upload" id="banner_file_upload" class="layui-uplaod-file"--%>
                       <%--lay-type="file">--%>
                    <%--<button class="layui-btn test" lay-data="{url: '/b/', accept: 'file'}">上传文件</button>--%>
                    <button type="button" class="layui-btn" id="test3"><i class="layui-icon"></i>上传文件</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    layui.use('upload', function () {
        var upload = layui.upload;
        upload.render({
            elem: '#test3',
            url: "/index.php/Admin/Product/upload",
            method: 'post',
            data:[],

            before: function (obj) {
                console.log('文件上传中');
                layer.load();
            },
            success: function (msg) {
                console.log(msg);
                if (msg.msg == "success") {
                    layer.closeAll('loading');
                    layer.msg("上传成功");
                    $("#img_url1").attr("value", msg.src);
                } else if (msg.msg == "error") {
                    layer.closeAll('loading');
                    layer.msg(msg.code);
                }
            },
            error: function (data) {
                layer.msg("上传失败");
                console.log(data);
            }
        });
    });

</script>
</body>
</html>        
        
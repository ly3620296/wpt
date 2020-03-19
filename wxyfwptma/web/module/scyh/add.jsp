<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/myCommon.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
<jsp:include page="/login/auth.jsp"></jsp:include>

<div class="layui-fluid">

    <div>
        <fieldset class="layui-elem-field layui-field-title"
                  style="margin-top: 20px;border-color: #009688 !important;">
            <legend>用户信息导入</legend>
        </fieldset>
    </div>

</div>
<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-card-header">
                <i class="layui-icon   layui-icon-link"></i>
                <a style="text-decoration:underline;color: #0000FF" href="用户导入模板.xls">
                    下载用户导入模板</a>
            </div>
            <div class="layui-card-body">
                <%--<a style="text-decoration:underline;color: #0000FF" href="用户导入模板.xls">下载模板</a>--%>

                <div class="layui-upload-drag" id="test3" style="width: 388px;height: 240px;">
                    <i class="layui-icon" style="    display: inline-block;margin-top: 70px;font-size: 55px;"></i>

                    <p>点击上传，或将文件拖拽到此处</p>
                </div>

            </div>
            <button type="button" class="layui-btn" style="    margin-bottom: 10px;margin-left: 14px;width: 452px;"
                    id="btn" lay-submit lay-filter="formDemo">立即提交
            </button>
            <%--<button class="layui-btn" >立即提交</button>--%>
        </div>
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dialogBg"></div>
<div id="dialog" class="animated">
    <div class="dialogTop" id="alert_header">
        <span>aaaaaa</span>
        <%--<a href="javascript:;" class="claseDialogBtn">关闭</a>--%>
    </div>
    <ul class="editInfos" id="alert_ccj">

    </ul>
    <div style="text-align: center;margin: 20px">
        <button id="alert_close" class="layui-btn layui-btn-normal layui-btn-sm" style="width: 100px">返回</button>
    </div>
</div>
<script>
    function getSrceenWH() {
        var w = $(window).width();
        var h = $(window).height();
        $('#dialogBg').width(w).height(h);
    }

    window.onresize = function () {
        getSrceenWH();
    }

    $(window).resize();

    $(function () {
        getSrceenWH();
        //关闭弹窗
        $('#alert_close').click(function () {
//            $('#dialogBg').fadeOut(100, function () {
            $('#dialog').addClass('bounceOutUp').fadeOut(500, function () {
                $('#dialogBg').fadeOut();
            });
//            });
        });
    });
</script>
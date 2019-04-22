<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dialogBg"></div>
<div id="dialog" class="animated">
    <div class="dialogTop">
        <a href="javascript:;" class="claseDialogBtn">关闭</a>
    </div>
    <ul class="editInfos" id="alert_ccj">

    </ul>
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
        $('.claseDialogBtn').click(function () {
            $('#dialogBg').fadeOut(300, function () {
                $('#dialog').addClass('bounceOutUp').fadeOut();
            });
        });
    });
</script>
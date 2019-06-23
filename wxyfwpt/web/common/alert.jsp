<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dialogBg"></div>
<div id="dialog" class="animated">
    <div class="dialogTop" id="alert_header">
        <span></span>
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
        var w = window.innerWidth;
        var h = window.innerHeight;

        document.getElementById("dialogBg").clientWidth = w + "px";
        document.getElementById("dialogBg").clientHeight = h + "px";
    }
    function setOpacity(ele, opacity) {
        if (document.all) {
            ///兼容ie
            ele.style.filter = "alpha(opacity=" + opacity + ")";
        }
        ele
        {
            ///兼容FF和GG
            ele.style.opacity = opacity / 100;
        }
    }
    function fadeout(ele, opacity, speed) {
        if (ele) {
            var v = ele.style.filter.replace("alpha(opacity=", "").replace(")", "") || ele.style.opacity || 100;
            v < 1 && (v = v * 100);
            var count = speed / 1000;
            var avg = (100 - opacity) / count;
            var timer = null;
            timer = setInterval(function () {
                if (v - avg > opacity) {
                    v -= avg;
                    setOpacity(ele, v);
                } else {
                    clearInterval(timer);
                }
            }, 500);
        }
    }

    function close() {
//        $('#dialog').addClass('bounceOutUp').fadeOut(500, function () {
//            $('#dialogBg').fadeOut();
//        });
        var close_btn = document.getElementById("dialog");
        close_btn.className = close_btn.className == '' ? 'bounceOutUp' : close_btn.className + ' ' + 'bounceOutUp';
        fadeout(close_btn, 0, 500);
        setTimeout(function () {
            document.getElementById("dialogBg").style.display = "none";
        }, 510);

    }

    window.onload = function () {
        getSrceenWH();
        //关闭弹窗
//        $('#alert_close').click(function () {
////            $('#dialogBg').fadeOut(100, function () {
//            $('#dialog').addClass('bounceOutUp').fadeOut(500, function () {
//                $('#dialogBg').fadeOut();
//            });
////            });
//        });
        var btn_close = document.getElementById("alert_close");
        btn_close.addEventListener('click', close, false);
    };
</script>
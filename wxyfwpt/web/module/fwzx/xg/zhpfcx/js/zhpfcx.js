var wpt_zhpfcx;
layui.use('form', function () {
    var $ = layui.jquery;
    var loadIndex;
    wpt_zhpfcx = {
        zhpfMx: function (pf) {
            var my_html =
                '<li><icon class="fa fa-calendar-minus-o"></icon><label>学年学期：' + (pf.XNXQ == null ? "" : pf.XNXQ) + '</label></li>' +
                '<li><icon class="fa fa-wpforms"></icon><label>总分：' + (pf.FS == null ? "" : pf.FS) + '</label></li>' +
                '<li><icon class="fa fa-line-chart"></icon><label>参评组排名：' + (pf.CPZPM == null ? "" : pf.CPZPM ) + '</label></li>' +
                '<li><icon class="fa fa-line-chart"></icon><label>年级专业排名：' + (pf.NJZYPM == null ? "" : pf.NJZYPM ) + '</label></li>' +
                '<li><icon class="fa fa-line-chart"></icon><label>班级排名：' + (pf.BJPM == null ? "" : pf.BJPM) + '</label></li>' +
                '<li><icon class="fa fa-line-chart"></icon><label>全校排名：' + (pf.QXPM == null ? "" : pf.QXPM ) + '</label></li>' +
                '<li><icon class="fa fa-line-chart"></icon><label>学院排名：' + (pf.XYPM == null ? "" : pf.XYPM ) + '</label></li>' +
                '<li><icon class="fa fa-line-chart"></icon><label>年级学院排名：' + (pf.NJXYPM == null ? "" : pf.NJXYPM) + '</label></li>';
            $("#alert_ccj").html(my_html);
            $("#alert_header").html('<span>' + pf.XMMC + '</span>');
            var className = 'bounceIn';
            $('#dialogBg').fadeIn(300);
            $('#dialog').removeAttr('class').addClass('animated ' + className + '').fadeIn();
        },
        init: function (zhpfcxList) {
            var pfs = "";
            if (zhpfcxList) {
                for (var index in zhpfcxList) {
                    var pf = zhpfcxList[index];
                    var pfObj = JSON.stringify(pf).replace(/"/g, '&quot;')
                    pfs += '<tr onclick="wpt_zhpfcx.zhpfMx(' + pfObj + ')">' +
                    '<td>' + pf.XNXQ + '</td>' +
                    '<td>' + pf.FS + '</td>' +
                    '<td>' + pf.XMMC + '</td>' +
                    '</tr>';
                }
                $("#wpt_zhpfcx_table").html(pfs);
            }
        }
    }

    $.ajax({
        url: wpt_serverName + "xg/zhpfcx",
        type: 'post',
        dataType: 'json',
        timeout: 10000,
        beforeSend: function () {
            layer.ready(function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
            })
        },
        success: function (data) {
            if (data) {
                var code = data.returnInfo.return_code;
                var msg = data.returnInfo.return_msg;
                if (code == "0") {
                    wpt_zhpfcx.init(data.zhpfcxList);
                } else {
                    layer.msg(msg, {anim: 6, time: 2000});
                }
            }
        }
        ,
        complete: function () {
            layer.close(loadIndex);
        }
    })

});



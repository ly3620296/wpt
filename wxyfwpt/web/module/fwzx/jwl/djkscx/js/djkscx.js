var wpt_ccj;
layui.use('form', function () {
    var $ = layui.jquery;
    var loadIndex;
    wpt_ccj = {
        cjmx: function (cj) {
            var cjbz = cj.CJBZ || "";
            var my_html = '<li><icon class="fa fa-vcard"></icon><label>准考证号：' + (cj.ZKZH == null ? "" : cj.ZKZH) + '</label></li>' +
                '<li><icon class="fa fa-wpforms"></icon><label>综合成绩：' + (cj.ZHCJ == null ? "" : cj.ZHCJ) + '</label></li>' +
                '<li><icon class="fa fa-wpforms"></icon><label>项目分享成绩1：' + (cj.XMFXCJ1 == null ? "" : cj.XMFXCJ1) + '</label></li>' +
                '<li><icon class="fa fa-wpforms"></icon><label>项目分享成绩2：' + (cj.XMFXCJ2 == null ? "" : cj.XMFXCJ2) + '</label></li>' +
                '<li><icon class="fa fa-wpforms"></icon><label>项目分享成绩3：' + (cj.XMFXCJ3 == null ? "" : cj.XMFXCJ3) + '</label></li>' +
                '<li><icon class="fa fa-wpforms"></icon><label>项目分享成绩4：' + (cj.XMFXCJ4 == null ? "" : cj.XMFXCJ4) + '</label></li>' +
                '<li><icon class="fa fa-calendar-minus-o"></icon><label>考试日期：' + (cj.KSRQ == null ? "" : cj.KSRQ) + '</label></li>' +
                '<li><icon class="fa fa-calendar-minus-o"></icon><label>学年学期：' + (cj.XNXQ == null ? "" : cj.XNXQ ) + '</label></li>';
            $("#alert_ccj").html(my_html);
            $("#alert_header").html('<span>' + (cj.DJKSMC == null ? "" : cj.DJKSMC) + '</span>');
            var className = 'bounceIn';
            $('#dialogBg').fadeIn(300);
            $('#dialog').removeAttr('class').addClass('animated ' + className + '').fadeIn();
        },
        initCj: function (djkscjList) {
            var cjs = "";
            if (djkscjList) {
                for (var index in djkscjList) {
                    var cj = djkscjList[index];
                    var cjObj = JSON.stringify(cj).replace(/"/g, '&quot;')
                    cjs += '<tr onclick="wpt_ccj.cjmx(' + cjObj + ')">' +
                    '<td class="cjj_td"></td>' +
                    '<td><p style="width: 9em">' + (cj.DJKSMC == null ? "" : cj.DJKSMC) + '</p></td>' +
                    '<td>' + (cj.ZHCJ == null ? "" : cj.ZHCJ) + '</td>' +
                    '<td>' + (cj.KSRQ == null ? "" : cj.KSRQ) + '</td>' +
                    '<td>' + (cj.XNXQ == null ? "" : cj.XNXQ) + '</td>' +
                    '<td class="cjj_td"></td>' +
                    '</tr>';
                }
                $("#wpt_cj_table").html(cjs);
            }
        }
    }

    $.ajax({
        url: wpt_serverName + "jwl/djkscx",
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
                    //加载成绩
                    wpt_ccj.initCj(data.djkscjList);
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



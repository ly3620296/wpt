layui.use('form', function () {
    var form = layui.form,
        layer = layui.layer;
    var loadIndex;
    form.on('select(xnxq_filter)', function (data) {
        $.ajax({
            url: wpt_serverName + "jwl/ccj/ccjByXnxq",
            type: 'post',
            dataType: 'json',
            data: {currXnxq: data.value},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
            },
            success: function (data) {
                if (data) {
                    var code = data.returnInfo.return_code;
                    var msg = data.returnInfo.return_msg;
                    if (code == "0") {
                        //加载成绩
                        wpt_ccj.initCj(data.cjList);
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

    $.ajax({
        url: wpt_serverName + "jwl/ccj/index",
        type: 'post',
        dataType: 'json',
        timeout: 10000,
        beforeSend: function () {
            loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
        },
        success: function (data) {
            if (data) {
                var code = data.returnInfo.return_code;
                var msg = data.returnInfo.return_msg;
                if (code == "0") {
                    var xnxqList = data.xnxqList;
                    var currXnxq = data.currXnxq;
                    //加载学年学期下拉
                    if (xnxqList) {
                        var options = "";
                        for (var index in xnxqList) {
                            var xnxq = xnxqList[index].XNXQ;
                            if (currXnxq == xnxq) {
                                options += "<option value='" + xnxq + "' selected>" + xnxq + "</option>";
                            } else {
                                options += "<option value='" + xnxq + "'>" + xnxq + "</option>";
                            }
                        }
                        $("#ccj_xq").html(options);
                        form.render('select');
                    }
                    //加载成绩
                    wpt_ccj.initCj(data.cjList);
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

var wpt_ccj = {
    cjmx: function (cj) {
        var cjbz = cj.CJBZ || "";
        var my_html = '<li><label>课程名：' + cj.KCMC + '</label></li>' +
            '<li><label>学分：' + cj.XF + '</label></li>' +
            '<li><label>成绩：' + cj.ZPCJ + '</label></li>' +
            '<li><label>课程性质：' + cj.CJXZ + '</label></li>' +
            '<li><label>课程代码：' + cj.KCH + '</label></li>' +
            '<li><label>成绩性质：' + cj.KCXZ + '</label></li>' +
            '<li><label>成绩备注：' + cjbz + '</label></li>';
        $("#alert_ccj").html(my_html);

        var classArr = new Array();
        classArr.push('bounceIn');
        classArr.push('rollIn');
        classArr.push('bounceInDown');
        classArr.push('flipInX');
        var className = 'bounceIn';
        $('#dialogBg').fadeIn(300);
        $('#dialog').removeAttr('class').addClass('animated ' + className + '').fadeIn();
    },
    initCj: function (cjList) {
        var cjs = "";
        if (cjList) {
            for (var index in cjList) {
                var cj = cjList[index];
                var cjObj = JSON.stringify(cj).replace(/"/g, '&quot;')
                cjs += '<tr onclick="wpt_ccj.cjmx(' + cjObj + ')">' +
                '<td>' + cj.KCMC + '</td>' +
                '<td>' + cj.XF + '</td>' +
                '<td>' + cj.ZPCJ + '</td>' +
                '<td>' + cj.CJXZ + '</td>' +
                '</tr>';
            }
            $("#wpt_cj_table").html(cjs);

        }
    }
}


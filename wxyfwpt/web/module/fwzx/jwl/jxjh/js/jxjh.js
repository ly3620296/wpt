layui.use('form', function () {
    var form = layui.form,
        layer = layui.layer;
    var loadIndex;
    form.on('select(xnxq_filter)', function (data) {
        $.ajax({
            url: wpt_serverName + "jwl/jxjh/jxjhByXnxq",
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
                        //加载教学计划
                        wpt_jxjh.initJxjh(data.jxjhList);
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
        url: wpt_serverName + "jwl/jxjh",
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
                    wpt_jxjh.initJxjh(data.jxjhList);
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

var wpt_jxjh = {
    cjmx: function (cj) {
        var my_html = '<li><icon class="fa fa-book"></icon><label>学分：' + cj.KCXF + '</label></li>' +
            '<li><icon class="fa fa-clock-o"></icon><label>学时：' + cj.KCXS + '</label></li>' +
            '<li><icon class="fa fa-bookmark-o"></icon><label>课程性质：' + cj.KCXZ + '</label></li>' +
            '<li><icon class="fa fa-pencil"></icon><label>考核方式：' + cj.KHFS + '</label></li>' +
            '<li><icon class="fa fa-pencil-square-o"></icon><label>考试方式：' + cj.KSFS + '</label></li>' +
            '<li><icon class="fa fa-paint-brush"></icon><label>考试形式：' + cj.KSXS + '</label></li>' +
            '<li><icon class="fa fa-tasks"></icon><label>课程类别：' + cj.KCLB + '</label></li>' +
            '<li><icon class="fa fa-building-o"></icon><label>开课部门：' + cj.KKBM + '</label></li>';
        $("#alert_ccj").html(my_html);
        $("#alert_header").html('<span>' + cj.KCMC + '</span>');
        var classArr = new Array();
        classArr.push('bounceIn');
        classArr.push('rollIn');
        classArr.push('bounceInDown');
        classArr.push('flipInX');
        var className = 'bounceIn';
        $('#dialogBg').fadeIn(300);
        $('#dialog').removeAttr('class').addClass('animated ' + className + '').fadeIn();
    },
    initJxjh: function (jxjhList) {
        var jxjhs = "";
        if (jxjhList) {
            for (var index in jxjhList) {
                var jxjh = jxjhList[index];
                var jxjhObj = JSON.stringify(jxjh).replace(/"/g, '&quot;')
                jxjhs += '<tr onclick="wpt_jxjh.cjmx(' + jxjhObj + ')">' +
                '<td class="cjj_td"></td>' +
                '<td><p style="width: 6em;">' + jxjh.KCMC + '</p></td>' +
                '<td>' + jxjh.KCXF + '</td>' +
                '<td>' + jxjh.KCXS + '</td>' +
                '<td>' + jxjh.KCXZ + '</td>' +
                '<td>' + jxjh.KHFS + '</td>' +
                '<td class="cjj_td"></td>' +
                '</tr>';
            }
            $("#wpt_cj_table").html(jxjhs);

        }
    }
}


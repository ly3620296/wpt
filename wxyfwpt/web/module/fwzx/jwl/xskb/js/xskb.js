var courseList = [
    ['', '', '', '', '', '', '', '', '', '', '', ''],
    ['', '', '', '', '', '', '', '', '', '', '', ''],
    ['', '', '', '', '', '', '', '', '', '', '', ''],
    ['', '', '', '', '', '', '', '', '', '', '', ''],
    ['', '', '', '', '', '', '', '', '', '', '', ''],
    ['', '', '', '', '', '', '', '', '', '', '', ''],
    ['', '', '', '', '', '', '', '', '', '', '', '']
];
var courseType = [
    [{index: '1', name: ''}, 1],
    [{index: '2', name: ''}, 1],
    [{index: '3', name: ''}, 1],
    [{index: '4', name: ''}, 1],
    [{index: '5', name: ''}, 1],
    [{index: '6', name: ''}, 1],
    [{index: '7', name: ''}, 1],
    [{index: '8', name: ''}, 1],
    [{index: '9', name: ''}, 1],
    [{index: '10', name: ''}, 1],
    [{index: '11', name: ''}, 1],
    [{index: '12', name: ''}, 1]
];
var week = ['一', '二', '三', '四', '五', '六', '日'];

layui.use('form', function () {
    var form = layui.form;

    form.on('select(xskb_zc)', function (data) {
        $.ajax({
            url: wpt_serverName + "jwl/xskb/xskbByzc",
            type: 'post',
            dataType: 'json',
            data: {currZc: data.value},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
            },
            success: function (data) {
                if (data) {
                    var code = data.returnInfo.return_code;
                    var msg = data.returnInfo.return_msg;
                    if (code == "0") {
                        //初始化课表
                        Timetable.setOption({
                            timetables: data.xskbArry || courseList,
                            highlightWeek: data.currXq
                        });
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

    var Timetable = new Timetables({
        cc: "cccc",
        el: '#coursesTable',
        timetables: courseList,
        week: week,
        timetableType: courseType,
        styles: {
            palette: ["#f05261", "#48a8e4", "#ffd061", "#52db9a", "#70d3e6", "#52db9a", "#3f51b5", "#f3d147", "#4adbc3", "#673ab7", "#f3db49", "#76bfcd", "#b495e1", "#ff9800", "#8bc34a"],
            Gheight: 50
        },
        merge: true,
        gridOnClick: function (e) {
            var name = e.name;
            if (name) {
                var names = name.split("_n_");
                var currAry = names[0].split("@");
                var kcmc = "";
                var skdd = "";
                var jsmc = "";
                var khfs = "";
                var zc = "";
                if (currAry.length == 2) {
                    kcmc = currAry[0] || "";
                    skdd = currAry[1] || "";
                }

                if (names.length == 4) {
                    jsmc = names[1] || "";
                    khfs = names[2] || "";
                    zc = names[3] || "";
                }
                var my_html = '<li><label>课程名：' + kcmc + '</label></li>' +
                    '<li><label>地点：' + skdd + '</label></li>' +
                    '<li><label>教师：' + jsmc + '</label></li>' +
                    '<li><label>考核方式：' + khfs + '</label></li>' +
                    '<li><label>周次：' + zc + '</label></li>';
                $("#alert_ccj").html(my_html);
                var className = 'bounceIn';
                $('#dialogBg').fadeIn(300);
                $('#dialog').removeAttr('class').addClass('animated ' + className + '').fadeIn();
            }

        }
    });
    var loadIndex;
    $.ajax({
        url: wpt_serverName + "jwl/xskb/index",
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
                    //初始化周次下拉选
                    var zcs = data.zcs;
                    if (zcs) {
                        var options = "";
                        var currZc = data.currZc;
                        for (var index in zcs) {
                            var zc = zcs[index];
                            //下拉选定位当前周
                            if (currZc == zc) {
                                options += "<option value='" + zc + "' selected>第" + zc + "周（当前周）</option>";
                            } else {
                                options += "<option value='" + zc + "'>第" + zc + "周</option>";
                            }
                        }
                        $("#xskb_zc").html(options);
                        form.render('select');
                    }
                    //初始化课表
                    Timetable.setOption({
                        timetables: data.xskbArry || courseList,
                        highlightWeek: data.currXq
                    });

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



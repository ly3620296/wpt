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

    form.on('select(jskb_zc)', function (data) {
        $.ajax({
            url: wpt_serverName + "jwl/jskb/jskbByzc",
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
                            timetables: data.jskbArry || courseList,
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
                var jxbmc = "";
                var bjzc = "";
                var khfs = "";
                var zc = "";
                var jxbrs = "";
                if (currAry.length == 2) {
                    kcmc = currAry[0] || "";
                    skdd = currAry[1] || "";
                }
                if (names.length == 6) {
                    jxbmc = names[1] || "";
                    bjzc = names[2] || "";
                    khfs = names[3] || "";
                    zc = names[4] || "";
                    jxbrs = names[5] || "";
                }
                var my_html = '<li><icon class="fa fa fa-building-o"></icon><label>地点：' + skdd + '</label></li>' +
                    '<li><icon class="fa fa-flag"></icon><label>教学班名称：' + jxbmc + '</label></li>' +
                    '<li><icon class="fa fa-group"></icon><label>班级组成：' + bjzc + '</label></li>' +
                    '<li><icon class="fa fa-pencil-square-o"></icon><label>考核方式：' + khfs + '</label></li>' +
                    '<li><icon class="fa fa-calendar-minus-o"></icon><label>周次：' + zc + '</label></li>' +
                    '<li><icon class="fa fa-male"></icon><label>教学班人数：' + jxbrs + '</label></li>';
                $("#alert_ccj").html(my_html);
                $("#alert_header").html('<span>' + kcmc + '</span>');
                var className = 'bounceIn';
                $('#dialogBg').fadeIn(300);
                $('#dialog').removeAttr('class').addClass('animated ' + className + '').fadeIn();
            }

        }
    });
    var loadIndex;
    $.ajax({
        url: wpt_serverName + "jwl/jskb/index",
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
                        $("#jskb_zc").html(options);
                        form.render('select');
                    }
                    //初始化课表
                    Timetable.setOption({
                        timetables: data.jskbArry || courseList,
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



var qscx_loadIndex;
var wpt_qscx;
layui.use('form', function () {
    var form = layui.form,
        $ = layui.jquery;

    wpt_qscx = {
        xsmx: function (xs) {  //学生明细弹窗
            var my_html = '<li><icon class="fa fa-institution"></icon><label>年级：' + xs.NJMC + '</label></li>' +
                '<li><icon class="fa fa-building-o"></icon><label>学院：' + xs.JGMC + '</label></li>' +
                '<li><icon class="fa fa-wpforms"></icon><label>专业：' + xs.ZYMC + '</label></li>' +
                '<li><icon class="fa fa-book"></icon><label>班级：' + xs.BJMC + '</label></li>' +
                '<li><icon class="fa fa-id-badge"></icon><label>联系电话：' + xs.LXDH + '</label></li>' +
                '<li><icon class="fa fa-clock-o"></icon><label>入住时间：' + xs.RZSJ + '</label></li>' +
                '<li><icon class="fa fa-bookmark-o"></icon><label>入住方式：' + xs.RZYYMC + '</label></li>';

            $("#alert_ccj").html(my_html);
            $("#alert_header").html('<span>' + xs.XM + '</span>');
            var classArr = new Array();
            classArr.push('bounceIn');
            classArr.push('rollIn');
            classArr.push('bounceInDown');
            classArr.push('flipInX');
            var className = 'bounceIn';
            $('#dialogBg').fadeIn(300);
            $('#dialog').removeAttr('class').addClass('animated ' + className + '').fadeIn();
        },
        initXsTable: function (xsList) {  //加载学生table
            var xss = "";
            if (xsList) {
                for (var index in xsList) {
                    var xs = xsList[index];
                    var xsObj = JSON.stringify(xs).replace(/"/g, '&quot;')
                    xss += '<tr onclick="wpt_qscx.xsmx(' + xsObj + ')">' +
                    '<td >' + xs.CWH + '</td>' +
                    '<td>' + xs.XH + '</td>' +
                    '<td>' + xs.XM + '</td>' +
                    '<td>' + xs.XB + '</td>' +
                    '</tr>';
                }
                $("#wpt_xs_table").html(xss);

            }
        },
        initLdSel: function (ldList) {    //初始化楼栋下拉选
            if (ldList) {
                var optionsCd = "<option value=''>请选择（楼栋名称）</option>";
                for (var index in ldList) {
                    var ld = ldList[index];
                    optionsCd += "<option value='" + ld.LDDM + "'> " + ld.LDMC + "</option>";
                }
                $("#qxcx_ld").html(optionsCd);
                form.render('select', 'qxcx_ld');
            }
        },
        initQshSel: function (qshList) {  //初始化寝室下拉选
            if (qshList) {
                var optionsLh = "<option value=''>请选择（寝室）</option>";
                for (var index in qshList) {
                    var qsh = qshList[index];
                    optionsLh += "<option value='" + qsh.QSH + "'> " + qsh.QSH + "</option>";
                }
                $("#qxcx_qs").html(optionsLh);
                form.render('select', 'qxcx_qs');
            }
        },
        initXsList: function (ldId, qshId) {  //查询所有学生列表
            $.ajax({
                url: wpt_serverName + "xg/qscx",
                type: 'post',
                dataType: 'json',
                data: {ldId: ldId, qshId: qshId},
                timeout: 10000,
                beforeSend: function () {
                    layer.ready(function () {
                        qscx_loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                    })
                },
                success: function (data) {
                    if (data) {
                        var code = data.returnInfo.return_code;
                        var msg = data.returnInfo.return_msg;
                        if (code == "0") {
                            var xsList = data.xsList;
                            wpt_qscx.initXsTable(xsList);
                        } else {
                            layer.msg(msg, {anim: 6, time: 2000});
                        }
                    }
                },
                complete: function () {
                    layer.close(qscx_loadIndex);
                }
            })
        },
        initLdList: function () { //查询所有楼栋列表
            $.ajax({
                url: wpt_serverName + "xg/qscx/ld",
                type: 'post',
                dataType: 'json',
                timeout: 10000,
                beforeSend: function () {
                    layer.ready(function () {
                        qscx_loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                    })
                },
                success: function (data) {
                    if (data) {
                        var code = data.returnInfo.return_code;
                        var msg = data.returnInfo.return_msg;
                        if (code == "0") {
                            //初始化楼栋
                            wpt_qscx.initLdSel(data.ldList);
                        } else {
                            layer.msg(msg, {anim: 6, time: 2000});
                        }
                    }
                }
                ,
                complete: function () {
                    layer.close(qscx_loadIndex);
                }
            })

        },
        xsListByXh: function () {
            var xhVal = $("#xh").val();
            if (xhVal) {
                $.ajax({
                    url: wpt_serverName + "xg/qscx/qscxByXh",
                    type: 'post',
                    dataType: 'json',
                    data: {ldId: $("#qxcx_ld").val(), qshId: $("#qxcx_qs").val(), xh: xhVal},
                    timeout: 10000,
                    beforeSend: function () {
                        layer.ready(function () {
                            qscx_loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                        })
                    },
                    success: function (data) {
                        if (data) {
                            var code = data.returnInfo.return_code;
                            var msg = data.returnInfo.return_msg;
                            if (code == "0") {
                                var xsList = data.xsList;
                                wpt_qscx.initXsTable(xsList);
                            } else {
                                layer.msg(msg, {anim: 6, time: 2000});
                            }
                        }
                    },
                    complete: function () {
                        layer.close(qscx_loadIndex);
                    }
                })
            } else {
                layer.msg("请填写学号", {anim: 6, time: 2000});
            }

        },
        bindCli: function () {
            $("#cxxh").bind("click", function () {
                wpt_qscx.xsListByXh();
            })
        },
        clearTable: function () {
            $("#xh").val("");
            $("#wpt_xs_table").html("");
        }
    };


    //楼栋下拉选
    form.on('select(qxcx_ld_se)', function (data) {
        wpt_qscx.clearTable();
        $.ajax({
            url: wpt_serverName + "xg/qscx/qsh",
            type: 'post',
            data: {ldId: data.value},
            dataType: 'json',
            timeout: 10000,
            beforeSend: function () {
                qscx_loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
            },
            success: function (data) {
                if (data) {
                    var code = data.returnInfo.return_code;
                    var msg = data.returnInfo.return_msg;
                    if (code == "0") {
                        wpt_qscx.initQshSel(data.qshList);
                    } else {
                        layer.msg(msg, {anim: 6, time: 2000});
                    }
                }
            }
            ,
            complete: function () {
                layer.close(qscx_loadIndex);
            }

        })
    });

    //寝室下拉选
    form.on('select(qxcx_qs_se)', function (data) {
        var ldId = $("#qxcx_ld").val();
        var qshId = data.value;
        wpt_qscx.clearTable();
        wpt_qscx.initXsList(ldId, qshId);
    });

    wpt_qscx.bindCli();
    //onload加载
    wpt_qscx.initLdList();
});
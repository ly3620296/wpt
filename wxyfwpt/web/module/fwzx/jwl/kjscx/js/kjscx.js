var kjscx_loadIndex;

layui.use('form', function () {
    var form = layui.form,
        $ = layui.jquery;
    //场地类别下拉选
    form.on('select(kjscx_cdlb_se)', function (data) {

        var rq = $("#kjscx_rq").val();
        var cdlbId = data.value;
        $.ajax({
            url: wpt_serverName + "jwl/kjscx/lh",
            type: 'post',
            dataType: 'json',
            data: {cdlbId: cdlbId},
            timeout: 10000,
            beforeSend: function () {
                //layer.ready(function () {
                kjscx_loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                //})
            },
            success: function (data) {
                if (data) {
                    var code = data.returnInfo.return_code;
                    var msg = data.returnInfo.return_msg;
                    if (code == "0") {
                        initLh(data.lhList);
                        var lhId = $("#kjscx_lh").val();
                        initKjsTable(rq, cdlbId, lhId);
                    } else {
                        layer.msg(msg, {anim: 6, time: 2000});
                    }
                }
            }
            ,
            complete: function () {
                layer.close(kjscx_loadIndex);
            }

        })
    });

    //日期下拉选
    form.on('select(kjscx_rq_se)', function (data) {
        var cdlbId = $("#kjscx_cdlb").val();
        var lhId = $("#kjscx_lh").val();
        if (!cdlbId) {
            layer.msg("请选择教室类别", {anim: 6, time: 2000});
            return;
        }
        initKjsTable(data.value, cdlbId, lhId)
    });

    //楼号下拉选
    form.on('select(kjscx_lh_se)', function (data) {
        var cdlbId = $("#kjscx_cdlb").val();
        var rq = $("#kjscx_rq").val();
        initKjsTable(rq, cdlbId, data.value);
    });

    function initKjsTable(rq, cdlbId, lhId) {
        $.ajax({
            url: wpt_serverName + "jwl/kjscx/kjscxByCon",
            type: 'post',
            dataType: 'json',
            data: {rq: rq, cdlbId: cdlbId, lhId: lhId},
            timeout: 10000,
            beforeSend: function () {
                layer.ready(function () {
                    kjscx_loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                })
            },
            success: function (data) {
                if (data) {
                    var code = data.returnInfo.return_code;
                    var msg = data.returnInfo.return_msg;
                    if (code == "0") {
                        var kjscxList = data.kjscxList;
                        var kjscxEmptlist = data.kjscxEmptlist;
                        var kjHtml = "";
                        for (var index in kjscxList) {
                            var kjscxI = kjscxList[index];
                            kjHtml += '<tr><td>' + kjscxI.skdd + '</td>';
                            var jcs = kjscxI.jc;
                            for (var ins in jcs) {
                                if (jcs[ins] == "1") {
                                    kjHtml += '<td><i class="fa fa-home fa-lg kjsactive"></i></td>';
                                } else {
                                    kjHtml += '<td><i class="fa fa-home fa-lg"></i></td>';
                                }
                            }
                            kjHtml += ' </tr>';
                        }
                        for (var inxx in kjscxEmptlist) {
                            var kjscxEnp = kjscxEmptlist[inxx];
                            kjHtml += '<tr><td>' + kjscxEnp.CDMC + '</td>';
                            kjHtml += '<td><i class="fa fa-home fa-lg"></i></td>' +
                            '<td><i class="fa fa-home fa-lg"></i></td>' +
                            '<td><i class="fa fa-home fa-lg"></i></td>' +
                            '<td><i class="fa fa-home fa-lg"></i></td>' +
                            '<td><i class="fa fa-home fa-lg"></i></td>' +
                            '<td><i class="fa fa-home fa-lg"></i></td>></tr>';
                        }


                        $("#kjs_tb").html(kjHtml);
                    } else {
                        layer.msg(msg, {anim: 6, time: 2000});
                    }
                }
            }
            ,
            complete: function () {
                layer.close(kjscx_loadIndex);
            }
        })
    }


    $.ajax({
        url: wpt_serverName + "jwl/kjscx",
        type: 'post',
        dataType: 'json',
        timeout: 10000,
        beforeSend: function () {
            layer.ready(function () {
                kjscx_loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
            })
        },
        success: function (data) {
            if (data) {
                var code = data.returnInfo.return_code;
                var msg = data.returnInfo.return_msg;
                if (code == "0") {
                    //初始化场地类别
                    initCdlb(data.cdlbList);
                    initRq(data.rqList);

                } else {
                    layer.msg(msg, {anim: 6, time: 2000});
                }
            }
        }
        ,
        complete: function () {
            layer.close(kjscx_loadIndex);
        }
    })

    //初始化场地类别
    function initCdlb(cdlbList) {
        if (cdlbList) {
            var optionsCd = "<option value=''>请选择（教室类别）</option>";
            for (var index in cdlbList) {
                var cdlb = cdlbList[index];
                optionsCd += "<option value='" + cdlb.CDLB_ID + "'> " + cdlb.CDLBMC + "</option>";
            }
            $("#kjscx_cdlb").html(optionsCd);
            form.render('select', 'kjscx_cdlb');
        }
    }

    //初始化楼号
    function initLh(lhList) {
        if (lhList) {
            var optionsLh = "<option value=''>请选择（楼号）</option>";
            for (var index in lhList) {
                var lh = lhList[index];
                optionsLh += "<option value='" + lh.LH + "'> " + lh.LH + "</option>";
            }
            $("#kjscx_lh").html(optionsLh);
            form.render('select', 'kjscx_lh');
        }
    }

    //初始化日期
    function initRq(rqList) {
        if (rqList) {
            var optionsRq = "";
            for (var index in rqList) {
                var rq = rqList[index];
                optionsRq += "<option value='" + rq.rq + "--" + rq.xq_num + "'> " + rq.xq + "</option>";
            }
            $("#kjscx_rq").html(optionsRq);
            form.render('select', 'kjscx_rq');
        }
    }
});
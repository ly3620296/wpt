var wpt_xkqkcx;
layui.use('form', function () {
    var form = layui.form,
        $ = layui.jquery;
    var loadIndex;

    wpt_xkqkcx = {
        cjmx: function (jxb_id, jxbmc) {
            //window.location.href = wpt_serverName + "module/fwzx/jwl/xkqkcx/xkqkcxDetail.jsp?jxb_id=" + jxb_id + "&jxbmc=" + jxbmc;
            window.location.replace(wpt_serverName + "module/fwzx/jwl/xkqkcx/xkqkcxDetail.jsp?jxb_id=" + jxb_id + "&jxbmc=" + jxbmc);
        },
        initXkqkcx: function (xkqkcxList) {
            var xkqkcxs = "";
            if (xkqkcxList) {
                for (var index in xkqkcxList) {
                    var xkqkcx = xkqkcxList[index];
                    var encJxbmc = encodeURIComponent(xkqkcx.JXBMC);
                    xkqkcxs += '<tr onclick="wpt_xkqkcx.cjmx(\'' + xkqkcx.JXB_ID + '\',\'' + encJxbmc + '\')">' +
                    '<td class="cjj_td"></td>' +
                    '<td><p style="width: 7em">' + xkqkcx.JXBMC + '</p></td>' +
                    '<td>' + xkqkcx.KCMC + '</td>' +
                    '<td>' + xkqkcx.KCXZ + '</td>' +
                    '<td>' + xkqkcx.JXBRS + '</td>' +
                    '<td class="cjj_td"></td>' +
                    '</tr>';
                }
                $("#wpt_cj_table").html(xkqkcxs);

            }
        }
    }

    form.on('select(xnxq_filter)', function (data) {
        $.ajax({
            url: wpt_serverName + "jwl/xkqkcx/xkqkcxByXnxq",
            type: 'post',
            dataType: 'json',
            data: {currXnxq: data.value},
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
                        wpt_xkqkcx.initXkqkcx(data.xkqkcxList);
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
        url: wpt_serverName + "jwl/xkqkcx",
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
                    wpt_xkqkcx.initXkqkcx(data.xkqkcxList);
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



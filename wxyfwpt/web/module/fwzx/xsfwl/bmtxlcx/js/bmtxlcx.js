var wpt_bmtxlcx;

layui.use('form', function () {
    var form = layui.form,
        $ = layui.jquery;
    var loadIndex;
    wpt_bmtxlcx = {
        initTxl: function (txlList) {
            if (txlList) {
                var txls = "";
                for (var index in txlList) {
                    var dh = txlList[index].DH;
                    var xm = txlList[index].XM;
                    txls += '  <li>' +
                    '<p class="lefttext"><i class="fa fa-address-book fa-lg"></i>' + xm + '</p>' +
                    '<p class="righttext">' + dh + '</p>' +
                    '</li>'
                }
                $("#txl-li").html(txls);
            }
        },
        initBm: function (bmList) {
            if (bmList) {
                var options = "";
                for (var index in bmList) {
                    var bmid = bmList[index].X_CODE;
                    var bmmc = bmList[index].X_NAME;
                    if (bmid == 0) {
                        //options += "<option value='" + bmid + "' selected>" + bmmc + "</option>";
                    } else {
                        options += "<option value='" + bmid + "'>" + bmmc + "</option>";
                    }

                }
                $("#bm").html(options);
                form.render('select');
            }
        },
        initPage: function () {
            $.ajax({
                url: wpt_serverName + "xsfwl/bmtxl",
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
                            var bmList = data.bmList;
                            var txlList = data.txlList;
                            //加载部门
                            wpt_bmtxlcx.initBm(bmList)
                            //加载通讯录
                            wpt_bmtxlcx.initTxl(txlList);
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
        },
        bindCli: function () {
            form.on('select(bm-filter)', function (data) {
                $.ajax({
                    url: wpt_serverName + "xsfwl/bmtxl/bmtxtByBm",
                    type: 'post',
                    dataType: 'json',
                    data: {bmCode: data.value},
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
                                //加载通讯录
                                wpt_bmtxlcx.initTxl(data.txlList);
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
        }
    };

    wpt_bmtxlcx.initPage();
    wpt_bmtxlcx.bindCli();

});



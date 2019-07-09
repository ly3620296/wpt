var wpt_tswzcx;
layui.use(['form', 'element', 'layer'], function () {
    var form = layui.form;
    var element = layui.element;
    var layer = layui.layer;
    var $ = layui.jquery;
    var loadIndex;
    wpt_tswzcx = {
        init: function (tswzcxList) {
            var tswzcxs = "";
            for (var index in tswzcxList) {
                var tswzcx = tswzcxList[index];
                tswzcxs += '<div class="layui-colla-item">' +
                '<h2 class="layui-colla-title">' + tswzcx.WZLB + '<span class="time-text">' + tswzcx.ZHGXSJ + '</span></h2>' +
                '<div class="layui-colla-content">' +
                '<ul class="textlist">' +
                '<li>' +
                '<p class="lefttext">读者证号</p>' +
                '<p class="righttext">' + tswzcx.DZZH + '</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">正题名</p>' +
                '<p class="righttext">' + tswzcx.ZTM + '</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">违章原因</p>' +
                '<p class="righttext">' + tswzcx.WZYY + '</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">应罚金额</p>' +
                '<p class="righttext">' + tswzcx.YFJE + '</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">实罚金额</p>' +
                '<p class="righttext">' + tswzcx.SFJE + '</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">付款方式</p>' +
                '<p class="righttext">' + tswzcx.FKFS + '</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">办理时间</p>' +
                '<p class="righttext">' + tswzcx.BLSJ + '</p>' +
                '</li>' +
                '</ul>' +
                '</div>' +
                '</div>';
            }
            $("#tswzcx").html(tswzcxs);
            element.render('collapse', 'tswzcx')
        },
        tswzcxIndex: function () {
            $.ajax({
                url: wpt_serverName + "tsfwl/tsgwz",
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
                            wpt_tswzcx.init(data.tsgwzList);
                        } else {
                            layer.msg(msg, {anim: 6, time: 2000});
                        }
                    }
                },
                complete: function () {
                    layer.close(loadIndex);
                }
            })
        }
    }
    wpt_tswzcx.tswzcxIndex();
});
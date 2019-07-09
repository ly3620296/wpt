var wpt_sfb;
layui.use(['form', 'element', 'layer'], function () {
    var form = layui.form;
    var element = layui.element;
    var layer = layui.layer;
    var $ = layui.jquery;
    var loadIndex;
    wpt_sfb = {
        init: function (sfbList) {
            var sfbs = "";
            for (var index in sfbList) {
                var sfb = sfbList[index];
                sfbs += '<div class="layui-colla-item">' +
                '<h2 class="layui-colla-title">' + sfb.XMMC + '<span class="time-text">' + sfb.XNXQ + '</span></h2>' +
                '<div class="layui-colla-content">' +
                '<ul class="textlist">' +
                '<li>' +
                '<p class="lefttext">项目编号</p>' +
                '<p class="righttext">' + sfb.XMBH + '</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">收费金额</p>' +
                '<p class="righttext">' + sfb.SSJE + '</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">实收金额</p>' +
                '<p class="righttext">' + sfb.YSJE + '</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">最后更新时间</p>' +
                '<p class="righttext">' + sfb.ZHGXSJ + '</p>' +
                '</li>' +
                '</ul>' +
                '</div>' +
                '</div>';
            }
            $("#sfList").html(sfbs);
            element.render('collapse', 'sfb')
        },
        sfbIndex: function () {
            $.ajax({
                url: wpt_serverName + "cw/sfb",
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
                            wpt_sfb.init(data.sfbList);
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
    wpt_sfb.sfbIndex();
});
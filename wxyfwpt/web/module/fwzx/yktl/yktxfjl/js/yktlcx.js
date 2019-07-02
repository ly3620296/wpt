var wpt_yktxfjl;
layui.use(['form', 'element'], function () {
    var form = layui.form;
    var element = layui.element;
    var $ = layui.jquery;
    var loadIndex;
    element.on('collapse(xfjl)', function (data) {
        //console.log(data);
        //$(".layui-colla-item").find(".layui-show").removeClass("layui-show");
        //$(data).addClass("layui-show");

    });
    wpt_yktxfjl = {
        initYktxfjl: function (xfjLList) {
            var xfjls = "";
            var jye = '';
            if (xfjLList) {
                for (var index in xfjLList) {
                    var xfjl = xfjLList[index];
                    if (index == 0) {
                        $("#yue").html("余额" + xfjl.YE + "元");
                    }
                    if (xfjl.JYLX == "支付") {
                        jye = '-' + xfjl.JYE + '元';
                    } else {
                        jye = '+' + xfjl.JYE + '元';
                    }
                    xfjls += '<div class="layui-colla-item">' +
                    '<h2 class="layui-colla-title">' + jye + '<span class="time-text">' + xfjl.JYSJ + '</span></h2>' +
                    '<div class="layui-colla-content">' +
                    '<ul class="textlist">' +
                    '<li>' +
                    '<p class="lefttext">商户</p>' +
                    '<p class="righttext">' + xfjl.SH + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">交易类型</p>' +
                    '<p class="righttext">' + xfjl.JYLX + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">余额</p>' +
                    '<p class="righttext">' + xfjl.YE + '元</p>' +
                    '</li>' +
                    '</ul>' +
                    '</div>' +
                    '</div>';
                }
                $("#xfjl").html(xfjls);
                element.render('collapse', 'xfjl')

            }
        },
        xfjlQuery: function () {
            $.ajax({
                url: wpt_serverName + "yktlcx/xfjl",
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
                            wpt_yktxfjl.initYktxfjl(data.xfjLList);
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
    };
    wpt_yktxfjl.xfjlQuery();

});





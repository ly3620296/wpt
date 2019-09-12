var wpt_gg;
layui.use('form', function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var loadIndex;
    wpt_gg = {
        initGg: function () {
            $.ajax({
                url: wpt_serverName + "xxts/gg",
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
                            var ggLi = data.ggList;
                            var ggHtml = "";
                            for (var ii in ggLi) {
                                var gg = ggLi[ii];
                                ggHtml += ' <li id="' + gg.G_ID + '">' +
                                '<p class="title"><i class="fa fa-bullhorn"></i>' + gg.G_TITLE + '</p>' +
                                '<p class="cont">' + gg.G_TEXT + '</p>' +
                                '<p class="time">' + gg.GTIME + '</p>' +
                                '</li>';
                            }
                            $('#gg').html(ggHtml);

                        } else {
                            layer.msg(msg, {anim: 6, time: 2000});
                        }
                    }
                },
                error: function () {
                },
                complete: function () {
                    layer.close(loadIndex);
                }
            })
        },
        bindMyClik: function () {
            $(document).on("click", "li", function (data) {
                window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails.jsp?ggId=" + data.currentTarget.id);
            })
        }
    }

    wpt_gg.initGg();
    wpt_gg.bindMyClik();


})


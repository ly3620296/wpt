$(function () {
    $("#loginOut").on("click", function () {
        var loadIndex;
        $.ajax({
            url: wpt_serverName + "login/loginOut",
            type: 'post',
            dataType: 'json',
            data: {},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
            },
            success: function (data) {
                if (data) {
                    var code = data.return_code;
                    var msg = data.return_msg;
                    if (code == "0") {
                        window.location.href = wpt_serverName;
                    } else {
                        layer.msg("登出失败,请稍后再试!", {anim: 6, time: 2000});
                    }
                }
            },
            complete: function () {
                layer.close(loadIndex);
            }
        })
    })
})
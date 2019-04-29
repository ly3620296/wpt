
function setRefer(){
    $("#wpt_main>li").on("click", function () {
        var my_li = $(this);
        var lay_href = my_li.attr("lay-href");
        if (lay_href) {
            window.location.href = wpt_serverName + lay_href;
        }
    })
}


var layer
var loadIndex;
layui.use('layer', function () {
    layer = layui.layer
    $.ajax({
        url: wpt_serverName + "my",
        type: 'post',
        dataType: 'json',
        timeout: 10000,
        beforeSend: function () {
            loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
        },
        success: function (data) {
            if (data) {
                var code = data.returnInfo.return_code;
                var msg = data.returnInfo.return_msg;
                if (code == "0") {
                    var myMenu = data.myMenu;
                    if (myMenu.length > 0) {
                        for (var a = 0; a < myMenu.length; a++) {
                            console.log(myMenu[a].URL==null)
                            //var html_myMenu = '<li lay-href="module/fwzx/jwl/xskb/xskb.jsp"> ' +
                            var html_myMenu = '<li lay-href="'+(myMenu[a].URL==null?"":myMenu[a].URL)+'"> ' +
                                '<img src="' + wpt_serverName + myMenu[a].IMG + '"/> ' +
                                '<p>' + myMenu[a].NAME + '</p> ' +
                                '</li>'
                            $("#wpt_main").append(html_myMenu)
                        }
                        setRefer();
                    }
                } else {
                    layer.msg(msg, {anim: 6, time: 2000});
                }
            }
        },
        complete: function () {
            layer.close(loadIndex);
        }
    })
});


var mySwiper = new Swiper('.swiper-container', {
    direction: 'horizontal',
    loop: true,
    // 如果需要分页器
    pagination: '.swiper-pagination',
    autoplay: 3000
})
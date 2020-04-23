var wpt_main;
layui.use('form', function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var loadIndex;
    wpt_main = {
        initSwiper: function () {
            var mySwiper = new Swiper('.swiper-container', {
                direction: 'horizontal',
                loop: true,
                // 如果需要分页器
                pagination: '.swiper-pagination',
                autoplay: 2000
            })
        },
        initWdcy: function () {
            $.ajax({
                url: wpt_serverName + "my",
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
                            var myMenu = data.myMenu;
                            if (myMenu.length > 0) {
                                for (var a = 0; a < myMenu.length; a++) {
                                    var html_myMenu = '<li lay-href="' + (myMenu[a].URL == null ? "" : myMenu[a].URL) + '"> ' +
                                        '<img src="' + wpt_serverName + myMenu[a].IMG + '"/> ' +
                                        '<p>' + myMenu[a].NAME + '</p> ' +
                                        '</li>'
                                    $("#wpt_main").append(html_myMenu)
                                }
                                wpt_main.setRefer();
                            }
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
        initGg: function () {
            $.ajax({
                url: wpt_serverName + "xxts/gg/ggMain",
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
                                '<p class="title" style="overflow:hidden; text-overflow:ellipsis; white-space: nowrap;"><i class="fa fa-bullhorn"></i>' + gg.G_TITLE + '</p>' +
                                '<p class="cont" style="overflow:hidden; text-overflow:ellipsis; white-space: nowrap;">' + gg.G_TEXT + '</p>' +
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
        setRefer: function () {
            $("#wpt_main>li").on("click", function () {
                var my_li = $(this);
                var lay_href = my_li.attr("lay-href");
                if (lay_href) {
                    if (window.location.pathname == wpt_serverName + "module/main/main.jsp" || window.location.pathname == wpt_serverName) {
                        window.location.replace(wpt_serverName + lay_href + "?pageSource=main");
                    } else {
                        window.location.replace(wpt_serverName + lay_href);
                    }
                }
            })
        }, bindLi: function () {
            $("#wpt_foot>li").on("click", function () {
                var my_li = $(this);
                var lay_href = my_li.attr("lay-href");
                if (lay_href) {
                    window.location.replace(wpt_serverName + lay_href);
                }
            })
            var url = window.location.href;
            if (url.search("module/fwzx/fwzxapp.jsp") != -1) {
                $(".footer-menu li").eq(1).find("i").addClass("active");
            } else if (url.search("module/msg/msg.jsp") != -1) {
                $(".footer-menu li").eq(2).find("i").addClass("active");
            } else if (url.search("module/my/my.jsp") != -1) {
                $(".fa-user").addClass("active");
            } else {
                $(".footer-menu li").eq(0).find("i").addClass("active");
            }

        }, bindMyClik: function () {
            $("#bj").on("click", function () {
                window.location.replace(wpt_serverName + "module/my/wdcy/wdcy.jsp");
            })

            $("#gd").on("click", function () {
                window.location.replace(wpt_serverName + "module/msg/ggList.jsp?pageSource=main");
            })

            $("#gg").on("click", "li", function (data) {
                window.location.replace(wpt_serverName + "module/msg/gg_demo/ggDetails.jsp?ggId=" + data.currentTarget.id);
            })
        }
    }
    wpt_main.initSwiper();
    wpt_main.initWdcy();
    wpt_main.bindLi();
    wpt_main.bindMyClik();
    wpt_main.initGg();


})


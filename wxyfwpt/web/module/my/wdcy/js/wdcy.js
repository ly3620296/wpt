var menu_bj;


layui.use(['layer', 'element'], function () {


    var layer = layui.layer;
    var element = layui.element;
    var $ = layui.jquery;
    var loadIndex = "";

    $(".layui-tab-title").click(function () {
        console.log($(".layui-this").html())
    })
    var my_menu = new Array();
    menu_bj = {
        addId: function (myMenu) {
            if (myMenu.length > 0) {
                for (var a = 0; a < myMenu.length; a++) {
                    var my_id = myMenu[a].ID;
                    my_menu.push(my_id)
                }
            }
        },
        add: function (str) {
            if (my_menu.length >= 9) {
                layer.msg("常用按钮不能大于九个!", {anim: 6, time: 2000})
                return
            } else {
                var th_id = $(str).attr("id")
                var index = my_menu.indexOf(th_id);
                if (index > -1) {
                    my_menu.splice(index, 1);
                }
                my_menu.push(th_id)
                my_menu = my_menu.filter(function (element, index, array) {
                    return array.indexOf(element) === index;
                });
                $(str).attr("class", "fa fa-minus-square")
                $(str).attr("onclick", "menu_bj.re(this)")
                $("#myMenu").append('<li id="my_' + th_id + '">' + $(str).parent().html() + '</li>')
            }
        },
        finish: function () {
            $.ajax({
                url: wpt_serverName + "my/wdcy/finish",
                type: 'post',
                dataType: 'json',
                data: {myId: my_menu},
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
                            //layer.msg("编辑我的常用完成!", {anim: 6, time: 2000});
                            //setTimeout(function () {
                            //window.location.href = wpt_serverName + "module/main/main.jsp"
                            window.location.replace(wpt_serverName + "module/main/main.jsp");
                            //}, 2000)
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
        re: function (str) {
            if (my_menu.length < 2) {
                layer.msg("常用按钮不能小于一个!", {anim: 6, time: 2000})
                return
            } else {
                var th_id = $(str).attr("id")
                var index = my_menu.indexOf(th_id);
                if (index > -1) {
                    my_menu.splice(index, 1);
                }
                my_menu = my_menu.filter(function (element, index, array) {
                    return array.indexOf(element) === index;
                });
                $("#my_" + th_id).remove()
                $("#" + th_id).attr("class", "fa fa-plus-square")
                $("#" + th_id).attr("onclick", "menu_bj.add(this)")
            }
        }
    }

    $.ajax({
        url: wpt_serverName + "my/wdcy",
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
                            var html_myMenu = '<li id="my_' + myMenu[a].ID + '"> ' +
                                '<img src="' + wpt_serverName + myMenu[a].IMG + '"/>' +
                                '<i class="fa fa-minus-square" onclick="menu_bj.re(this)" id="' + myMenu[a].ID + '"></i> ' +
                                '<p>' + myMenu[a].NAME + '</p> ' +
                                '</li>'
                            $("#myMenu").append(html_myMenu)
                        }
                    }
                    var allMenu = data.allMenu;
                    if (allMenu.length > 0) {
                        for (var i = 0; i < allMenu.length; i++) {
                            var type = allMenu[i].TYPE;
                            if (type == '1') {
                                var tab_change = ''
                                var menu_html = ''
                                if (i == 0) {
                                    tab_change = '<li class="layui-this">' + allMenu[i].NAME + '</li>';
                                    menu_html = '<div class="layui-tab-item layui-show"><div class="tc"><ul id="fa_' + allMenu[i].ID + '"></ul></div></div>'
                                } else {
                                    tab_change = '<li>' + allMenu[i].NAME + '</li>';
                                    menu_html = '<div class="layui-tab-item"><div class="tc"><ul id="fa_' + allMenu[i].ID + '"></ul></div></div>'
                                }
                                $("#tab_change").append(tab_change)
                                $("#menu_html").append(menu_html)
                            } else {
                                var id = allMenu[i].ID;
                                var bool = false;
                                if (myMenu.length > 0) {
                                    for (var a = 0; a < myMenu.length; a++) {
                                        var my_id = myMenu[a].ID;
                                        if (id == my_id) {
                                            bool = true;
                                            break
                                        }
                                    }
                                    if (bool) {
                                        var this_menu = '<li><i class="fa fa-minus-square" onclick="menu_bj.re(this)" id="' + allMenu[i].ID + '"></i>' +
                                            '<img src="' + wpt_serverName + allMenu[i].IMG + '"/>' +
                                            '<p>' + allMenu[i].NAME + '</p></li>'
                                        $("#fa_" + allMenu[i].FATHER).append(this_menu)

                                    } else {
                                        var this_menu = '<li><i class="fa fa-plus-square" onclick="menu_bj.add(this)" id="' + allMenu[i].ID + '"></i>' +
                                            '<img src="' + wpt_serverName + allMenu[i].IMG + '"/>' +
                                            '<p>' + allMenu[i].NAME + '</p></li>'
                                        $("#fa_" + allMenu[i].FATHER).append(this_menu)
                                    }
                                } else {
                                    var this_menu = '<li><i class="fa fa-plus-square" onclick="menu_bj.add(this)" id="' + allMenu[i].ID + '"></i>' +
                                        '<img src="' + wpt_serverName + allMenu[i].IMG + '"/>' +
                                        '<p>' + allMenu[i].NAME + '</p></li>'
                                    $("#fa_" + allMenu[i].FATHER).append(this_menu)
                                }
                            }
                        }
                        menu_bj.addId(myMenu);
                    } else {
                        layer.msg("暂未查询到菜单信息!", {anim: 6, time: 2000});
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


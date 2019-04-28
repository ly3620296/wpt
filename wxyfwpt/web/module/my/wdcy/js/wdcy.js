var layer
var loadIndex;
layui.use(['layer', 'element'], function () {
    layer = layui.layer
    var element = layui.element;
    $.ajax({
        url: wpt_serverName + "my/wdcy",
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
                            var html_myMenu = '<li id="my_' + myMenu[a].ID + '"> ' +
                                '<img src="' + wpt_serverName + myMenu[a].IMG + '"/>' +
                                '<i class="fa fa-minus-square" onclick="re(this)" id="' + myMenu[a].ID + '"></i> ' +
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
                                var bool=false;
                                if (myMenu.length > 0) {
                                    for (var a = 0; a < myMenu.length; a++) {
                                        var my_id = myMenu[a].ID;
                                        if (id == my_id) {
                                            bool=true;
                                            break
                                        }
                                    }
                                    if(bool){
                                        var this_menu = '<li><i class="fa fa-minus-square" onclick="re(this)" id="' + allMenu[i].ID + '"></i>' +
                                            '<img src="' + wpt_serverName + allMenu[i].IMG + '"/>' +
                                            '<p>' + allMenu[i].NAME + '</p></li>'
                                        $("#fa_" + allMenu[i].FATHER).append(this_menu)
                                    }else{
                                        var this_menu = '<li><i class="fa fa-plus-square" onclick="add(this)" id="' + allMenu[i].ID + '"></i>' +
                                            '<img src="' + wpt_serverName + allMenu[i].IMG + '"/>' +
                                            '<p>' + allMenu[i].NAME + '</p></li>'
                                        $("#fa_" + allMenu[i].FATHER).append(this_menu)
                                    }
                                } else {
                                    var this_menu = '<li><i class="fa fa-plus-square" onclick="add(this)" id="' + allMenu[i].ID + '"></i>' +
                                        '<img src="' + wpt_serverName + allMenu[i].IMG + '"/>' +
                                        '<p>' + allMenu[i].NAME + '</p></li>'
                                    $("#fa_" + allMenu[i].FATHER).append(this_menu)
                                }
                            }
                        }
                        //onclick="add(this)"
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
    var active = {
        tabChange: function () {
            element.tabChange('demo', '22');
        }
    };
});

function add(str) {
    var th_id = $(str).attr("id")
    $.ajax({
        url: wpt_serverName + "my/wdcy/addMyMenu",
        type: 'post',
        dataType: 'json',
        data: {id: th_id},
        timeout: 10000,
        beforeSend: function () {
            loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
        },
        success: function (data) {
            if (data) {
                var code = data.returnInfo.return_code;
                var msg = data.returnInfo.return_msg;
                if (code == "0") {
                    $(str).attr("class", "fa fa-minus-square")
                    $(str).attr("onclick", "re(this)")
                    $("#myMenu").append('<li id="my_' + th_id + '">' + $(str).parent().html() + '</li>')
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
}

function re(str) {
    var th_id = $(str).attr("id")
    console.log(th_id)
    $.ajax({
        url: wpt_serverName + "my/wdcy/reMyMenu",
        type: 'post',
        dataType: 'json',
        data: {id: th_id},
        timeout: 10000,
        beforeSend: function () {
            loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
        },
        success: function (data) {
            if (data) {
                var code = data.returnInfo.return_code;
                var msg = data.returnInfo.return_msg;
                if (code == "0") {
                    $("#my_" + th_id).remove()
                    $("#" + th_id).attr("class", "fa fa-plus-square")
                    $("#" + th_id).attr("onclick", "add(this)")
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
}
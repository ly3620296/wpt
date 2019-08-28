var wpt_tsjycx;
layui.use(['form', 'element', 'layer'], function () {
    var form = layui.form;
    var element = layui.element;
    var layer = layui.layer;
    var $ = layui.jquery;
    var loadIndex;
    wpt_tsjycx = {
        init: function (tsjycxList) {
            var tsjycxyghs = "";
            var tsjycxwghs = "";
            for (var index in tsjycxList) {
                var tsjycx = tsjycxList[index];
                if (tsjycx.SFGH == 'y') {
                    tsjycxyghs += '<div class="layui-colla-item">' +
                    '<h2 class="layui-colla-title">' + (tsjycx.ZTM == null ? "" : tsjycx.ZTM) + '<span class="time-text">' + (tsjycx.ZHGXSJ == null ? "" : tsjycx.ZHGXSJ) + '</span></h2>' +
                    '<div class="layui-colla-content">' +
                    '<ul class="textlist">' +
                    '<li>' +
                    '<p class="lefttext">读者证号</p>' +
                    '<p class="righttext">' + (tsjycx.DZZH == null ? "" : tsjycx.DZZH) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">条形码</p>' +
                    '<p class="righttext">' + (tsjycx.TXM == null ? "" : tsjycx.TXM) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">正题名</p>' +
                    '<p class="righttext">' + (tsjycx.ZTM == null ? "" : tsjycx.ZTM) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">借出时间</p>' +
                    '<p class="righttext">' + (tsjycx.JCSK == null ? "" : tsjycx.JCSK) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">限还时间</p>' +
                    '<p class="righttext">' + (tsjycx.XHSK == null ? "" : tsjycx.XHSK) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">续借次数</p>' +
                    '<p class="righttext">' + (tsjycx.XJCS == null ? "" : tsjycx.XJCS) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">流通类型</p>' +
                    '<p class="righttext">' + (tsjycx.LTLX == null ? "" : tsjycx.LTLX) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">文献类型</p>' +
                    '<p class="righttext">' + (tsjycx.WXLX == null ? "" : tsjycx.WXLX) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">书期号</p>' +
                    '<p class="righttext">' + (tsjycx.SQH == null ? "" : tsjycx.SQH) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">实还时间</p>' +
                    '<p class="righttext">' + (tsjycx.BB == null ? "" : tsjycx.BB) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">出版者</p>' +
                    '<p class="righttext">' + (tsjycx.CBZ == null ? "" : tsjycx.CBZ) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">出版日期</p>' +
                    '<p class="righttext">' + (tsjycx.CBRQ == null ? "" : tsjycx.CBRQ) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">已借数</p>' +
                    '<p class="righttext">' + (tsjycx.YJS == null ? "" : tsjycx.YJS) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">归还时间</p>' +
                    '<p class="righttext">' + (tsjycx.GHSJ == null ? "" : tsjycx.GHSJ) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">最后更新时间</p>' +
                    '<p class="righttext">' + (tsjycx.ZHGXSJ == null ? "" : tsjycx.ZHGXSJ) + '</p>' +
                    '</li>' +
                    '</ul>' +
                    '</div>' +
                    '</div>';
                } else {
                    tsjycxwghs += '<div class="layui-colla-item">' +
                    '<h2 class="layui-colla-title">' + (tsjycx.ZTM == null ? "" : tsjycx.ZTM) + '<span class="time-text">' + (tsjycx.ZHGXSJ == null ? "" : tsjycx.ZHGXSJ) + '</span></h2>' +
                    '<div class="layui-colla-content">' +
                    '<ul class="textlist">' +
                    '<li>' +
                    '<p class="lefttext">读者证号</p>' +
                    '<p class="righttext">' + (tsjycx.DZZH == null ? "" : tsjycx.DZZH) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">条形码</p>' +
                    '<p class="righttext">' + (tsjycx.TXM == null ? "" : tsjycx.TXM) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">正题名</p>' +
                    '<p class="righttext">' + (tsjycx.ZTM == null ? "" : tsjycx.ZTM) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">借出时间</p>' +
                    '<p class="righttext">' + (tsjycx.JCSK == null ? "" : tsjycx.JCSK) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">限还时间</p>' +
                    '<p class="righttext">' + (tsjycx.XHSK == null ? "" : tsjycx.XHSK) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">续借次数</p>' +
                    '<p class="righttext">' + (tsjycx.XJCS == null ? "" : tsjycx.XJCS) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">流通类型</p>' +
                    '<p class="righttext">' + (tsjycx.LTLX == null ? "" : tsjycx.LTLX) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">文献类型</p>' +
                    '<p class="righttext">' + (tsjycx.WXLX == null ? "" : tsjycx.WXLX) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">书期号</p>' +
                    '<p class="righttext">' + (tsjycx.SQH == null ? "" : tsjycx.SQH) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">实还时间</p>' +
                    '<p class="righttext">' + (tsjycx.BB == null ? "" : tsjycx.BB) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">出版者</p>' +
                    '<p class="righttext">' + (tsjycx.CBZ == null ? "" : tsjycx.CBZ) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">出版日期</p>' +
                    '<p class="righttext">' + (tsjycx.CBRQ == null ? "" : tsjycx.CBRQ) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">已借数</p>' +
                    '<p class="righttext">' + (tsjycx.YJS == null ? "" : tsjycx.YJS) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">归还时间</p>' +
                    '<p class="righttext">' + (tsjycx.GHSJ == null ? "" : tsjycx.GHSJ) + '</p>' +
                    '</li>' +
                    '<li>' +
                    '<p class="lefttext">最后更新时间</p>' +
                    '<p class="righttext">' + (tsjycx.ZHGXSJ == null ? "" : tsjycx.ZHGXSJ) + '</p>' +
                    '</li>' +
                    '</ul>' +
                    '</div>' +
                    '</div>';
                }
            }
            $("#yh").html(tsjycxyghs);
            $("#wh").html(tsjycxwghs);
            element.render('collapse', 'tsjycxygh');
            element.render('collapse', 'tsjycxwgh');
        },
        tswzcxIndex: function () {
            $.ajax({
                url: wpt_serverName + "tsfwl/tsjycx",
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
                            wpt_tsjycx.init(data.tsjycxList);
                        } else {
                            layer.msg(msg, {anim: 6, time: 2000});
                        }
                    }
                },
                complete: function () {
                    layer.close(loadIndex);
                }
            })
        },
        bindLi: function () {
            $(".bar .li1").click(function () {
                $(".div11").show().siblings(".div22").hide()
            })
            $(".bar li").click(function () {
                $(this).addClass("libarlist").siblings().removeClass("libarlist")
            })
            $(".bar .li2").click(function () {
                $(".div22").show().siblings(".div11").hide()
            })
        }
    }

    wpt_tsjycx.bindLi();
    wpt_tsjycx.tswzcxIndex();

});
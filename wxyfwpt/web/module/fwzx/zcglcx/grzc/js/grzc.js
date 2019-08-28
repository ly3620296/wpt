var x;
layui.use(['form', 'element', 'layer'], function () {
    var form = layui.form;
    var element = layui.element;
    var layer = layui.layer;
    var $ = layui.jquery;
    var loadIndex;
    wpt_grzc = {
        init: function (grzcList) {
            var grzcs = "";
            for (var index in grzcList) {
                var grzc = grzcList[index];
                grzcs += '<div class="layui-colla-item">' +
                '<h2 class="layui-colla-title">'+(grzc.MC==null?"":grzc.MC)+'<span class="time-text">'+(grzc.GZRQ==null?"":grzc.GZRQ)+'</span></h2>' +
                '<div class="layui-colla-content">' +
                '<ul class="textlist">' +
                '<li>' +
                '<p class="lefttext">名称</p>' +
                '<p class="righttext">'+(grzc.MC==null?"":grzc.MC)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">型号</p>' +
                '<p class="righttext">'+(grzc.XH==null?"":grzc.XH)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">规格</p>' +
                '<p class="righttext">'+(grzc.GG==null?"":grzc.GG)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">成批条数</p>' +
                '<p class="righttext">'+(grzc.CPTS==null?"":grzc.CPTS)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">领用单位</p>' +
                '<p class="righttext">'+(grzc.LYDW==null?"":grzc.LYDW)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">单价</p>' +
                '<p class="righttext">'+(grzc.DJ==null?"":grzc.DJ)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">金额</p>' +
                '<p class="righttext">'+(grzc.JE==null?"":grzc.JE)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">分类号</p>' +
                '<p class="righttext">'+(grzc.FLH==null?"":grzc.FLH)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">厂家</p>' +
                '<p class="righttext">'+(grzc.CJ==null?"":grzc.CJ)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">出厂号</p>' +
                '<p class="righttext">'+(grzc.CCH==null?"":grzc.CCH)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">采购形式</p>' +
                '<p class="righttext">'+(grzc.CGXS==null?"":grzc.CGXS)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">购置日期</p>' +
                '<p class="righttext">'+(grzc.GZRQ==null?"":grzc.GZRQ)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">出厂日期</p>' +
                '<p class="righttext">'+(grzc.CCRQ==null?"":grzc.CCRQ)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">保修期限</p>' +
                '<p class="righttext">'+(grzc.BXQX==null?"":grzc.BXQX)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">发票号</p>' +
                '<p class="righttext">'+(grzc.FPH==null?"":grzc.FPH)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">人员编号</p>' +
                '<p class="righttext">'+(grzc.RYBH==null?"":grzc.RYBH)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">经费科目</p>' +
                '<p class="righttext">'+(grzc.JFKM==null?"":grzc.JFKM)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">领用人</p>' +
                '<p class="righttext">'+(grzc.LYR==null?"":grzc.LYR)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">经手人</p>' +
                '<p class="righttext">'+(grzc.JSR==null?"":grzc.JSR)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">存放地名称</p>' +
                '<p class="righttext">'+(grzc.CFDMC==null?"":grzc.CFDMC)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">现状</p>' +
                '<p class="righttext">'+(grzc.XZ==null?"":grzc.XZ)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">设备来源</p>' +
                '<p class="righttext">'+(grzc.SBLY==null?"":grzc.SBLY)+'</p>' +
                '</li>' +
                '<li>' +
                '<p class="lefttext">使用方向</p>' +
                '<p class="righttext">'+(grzc.SYFX==null?"":grzc.SYFX)+'</p>' +
                '</li>' +
                '</ul>' +
                '</div>' +
                '</div>';
            }
            $("#grzc").html(grzcs);
            element.render('collapse', 'grzc')
        },
        grzcIndex: function () {
            $.ajax({
                url: wpt_serverName + "zcgl/grzc",
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
                            wpt_grzc.init(data.grzcList);
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
    wpt_grzc.grzcIndex();
});
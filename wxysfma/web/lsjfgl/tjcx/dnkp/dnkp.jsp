<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/myCommon.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/commonLs.css">
    <style>
        .layui-table tbody tr:hover {
            background-color: #fbfbfb;
        }

        /*@media screen  and (max-width: 1599px) {*/
        .layui-form-radio > i {
            font-size: 18px !important;
            margin-right: 4px;
        }

        .layui-form-radio {
            margin: 6px 0 0 0 !important;
            padding-right: 7px;
        }

        .layui-form-radio * {
            font-size: 13px !important;
        }

        /*}*/
    </style>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<input type="hidden" id="my_status" value="0">
<jsp:include page="/login/lsauth.jsp"></jsp:include>
<div class="layui-fluid">
    <div>
        <fieldset class="layui-elem-field layui-field-title"
                  style="margin-top: 20px;border-color: #009688 !important;">
            <legend>电脑开票</legend>
        </fieldset>
    </div>


</div>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item" id="my-header">
                <div class="layui-inline">
                    <label class="layui-form-label">缴费学年：</label>

                    <div class="layui-input-inline">
                        <select lay-verify="required" id="search-sfxn">
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">学号：</label>

                    <div class="layui-input-inline">
                        <input type="text" id="search-xh" placeholder="学号" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label" style="line-height: 32px;">缴费方式：</label>

                    <div class="layui-input-inline">
                        <input type="radio" name="jffs" value="CASH" title="现金" checked>
                        <input type="radio" name="jffs" value="CARD" title="刷卡">
                        <input type="radio" name="jffs" value="GXZZ" title="高校转账">
                    </div>
                </div>
                <div class="layui-inline my-cx" style="margin-left: 50px;">
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="search" id="my-search">
                        查询
                    </button>
                </div>
            </div>
        </div>
        <div class="layui-card-body">
            <div class="layui-form">
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th colspan="14" style="background-color: #eef9fb">
                            <div align="center" style="color:#4aa4a5;font-weight:bold">
                                学生信息核实
                            </div>
                        </th>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr>
                        <td width="5%" style="background-color: #eef9fb">学号</td>
                        <td width="6%"><span id="XH"></span><span id="XN" style="display: none"></span></td>
                        <td width="5%" style="background-color: #eef9fb">姓名</td>
                        <td width="6%"><span id="XM"></span></td>
                        <td width="5%" style="background-color: #eef9fb">身份证号</td>
                        <td width="10%"><span id="SFZH"></span></td>
                        <td width="5%" style="background-color: #eef9fb">学院</td>
                        <td width="12%"><span id="XYMC"></span></td>
                        <td width="5%" style="background-color: #eef9fb">专业</td>
                        <td width="10%"><span id="ZYMC"></span></td>
                        <td width="7%" style="background-color: #eef9fb">班级</td>
                        <td width="5%"><span id="BJMC"></span></td>
                        <td width="5%" style="background-color: #eef9fb">年级</td>
                        <td width="5%"><span id="NJ"></span></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-card-body">
                <div class="layui-form">
                    <table class="layui-table" id="jfxxhs">
                        <tr>
                            <th style="background-color: #eef9fb" id="this_th">
                                <div align="center" style="color:#4aa4a5;font-weight:bold">
                                    缴费信息核实<font color='red'>（单位元）</font>
                                </div>
                            </th>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-form-item">
                <div class="layui-inline" style="margin-left: 1%;">
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="search" id="my-save">
                        保存
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-card-body">
                <div class="layui-form">
                    <table class="layui-table" id="yjf-order-table">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="search_data" style="padding: 5px 20px; line-height: 22px; font-weight: 300;display: none">
    <ul id="biuuu_city_list"></ul>
    <div id="demo20" style="    text-align: right;"></div>
</div>

<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script>
    var title;
    layui.use(['form', 'layer', 'table', 'laypage'], function () {
        var laypage = layui.laypage
        var jQuery = layui.jquery
                , form = layui.form
                , layer = layui.layer
                , table = layui.table
                , $ = layui.jquery

        var loadIndex;
        var wpt_grjfxx = {
            init: function () {
                $.ajax({
                    url: wpt_serverName + "lsjfgl/dnkp/title",
                    type: 'post',
                    dataType: 'json',
                    timeout: 10000,
                    beforeSend: function () {
                        loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                    },
                    success: function (data) {
                        if (data.code == "0") {
                            var xnList = data.xnList;
                            var optionsXn = "";
                            for (var index in xnList) {
                                var xnmc = xnList[index].XNMC;
                                //下拉选定位当前周
                                if (index == 0) {
                                    optionsXn += "<option value='" + xnmc + "' checked>" + xnmc + "</option>";
                                } else {
                                    optionsXn += "<option value='" + xnmc + "'>" + xnmc + "</option>";
                                }

                            }
                            $("#search-sfxn").html(optionsXn);
                            form.render('select');

                            var title = data.titles
                            var length = title.length + 2;
                            $("#this_th").attr("colspan", length)
                            var html = '';
                            html += '<tbody align="center">';
                            html += '<tr style="background-color: #eef9fb"> ' +
                            '<td   width="60px"></td> ' +
                            '<td style="color:#4aa4a5;font-weight:bold" >总额</td> ';
                            for (var i = 0; i < title.length; i++) {
                                if (title[i].SFBX == "1") {
                                    html += '<td style="color:#4aa4a5;font-weight:bold">' + title[i].JFXMMC + '</td> '
                                } else {
                                    html += '<td style="color:#4aa4a5;font-weight:bold">' + title[i].JFXMMC + '<span style="color: #bd9b4a">（选交）</span></td> '
                                }
                            }
                            html += '</tr> ';
                            html += '<tr> ' +
                            '<td  style="background-color: #eef9fb" width="60px">应缴费用</td> ' +
                            '<td><span id="ze_show"></span></td> ';
                            for (var i = 0; i < title.length; i++) {
                                html += '<td><span id="' + title[i].JFXMID + '_show"></span></td>'
                            }
                            html += '</tr> ';
                            html += '<tr> ' +
                            '<td  style="background-color: #eef9fb" width="60px">本次缴费</td> ' +
                            '<td><input disabled="disabled" id="ze_this" type="text" class="layui-input" style="text-align: center;height: 32px;margin-left: -8px;"></td> ';
                            for (var i = 0; i < title.length; i++) {
                                if (title[i].SFBX == "1") {
                                    html += '<td><input  disabled="disabled" id="' + title[i].JFXMID + '_this" type="text" class="layui-input" style="text-align: center;height: 32px;margin-left: -8px;"></td>'
                                } else {
                                    html += '<td><input onchange="changeMoney(this)" id="' + title[i].JFXMID + '_this" type="text" class="layui-input" style="text-align: center;height: 32px;margin-left: -8px;"></td>'
                                }

                            }
                            html += '</tr> ';
                            html += '</tbody>';
                            $("#jfxxhs").append(html)
                        } else {
                            layer.msg(data.msg, {anim: 6, time: 2000});
                        }
                    },
                    complete: function () {
                        layer.close(loadIndex);
                    }
                })
            },

            //回车查询
            queryByXh: function () {
                var xh = $("#search-xh").val();
                var xn = $("#search-sfxn").val();
                if (xh != null && xh.length > 0) {
                    $.ajax({
                        url: wpt_serverName + "lsjfgl/dnkp/queryByXh",
                        type: 'post',
                        dataType: 'json',
                        data: {xh: xh, xn: xn},
                        timeout: 10000,
                        beforeSend: function () {
                            loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                        },
                        success: function (data) {
                            if (data && data.code == "0") {
                                //补全用户信息
                                wpt_grjfxx.initUserInfo(data.userInfo);
                                //补全缴费信息
                                wpt_grjfxx.initWjf(data.wjfjl, data.titles);

                                //补全已交费信息
                                wpt_grjfxx.initYjfXx(data.yjfList, data.titles);
                            } else {
                                if (data) {
                                    layer.msg(data.msg, {anim: 6, time: 2000});
                                } else {
                                    layer.msg("系统繁忙请稍后重试！", {anim: 6, time: 2000});
                                }
                            }
                        },
                        complete: function () {
                            layer.close(loadIndex);
                        }
                    })
                } else {
                    layer.msg("请输入要查询的学号！", {anim: 6, time: 3000});
                    $("#search-xh").focus();
                }

            },
            //用户信息
            initUserInfo: function (userInfo) {
                if (userInfo) {
                    $("#XN").html(userInfo.XN)
                    $("#XH").html(userInfo.XH)
                    $("#XM").html(userInfo.XM)
                    $("#SFZH").html(userInfo.SFZH)
                    $("#XYMC").html(userInfo.XYMC)
                    $("#ZYMC").html(userInfo.ZYMC)
                    $("#BJMC").html(userInfo.BJMC)
                    $("#NJ").html(userInfo.NJ)
                }
            },
            //应缴费信息
            initWjf: function (wjfjl, titles) {
                if (wjfjl) {
                    $("#ze_show").html(wjfjl.YSHJ);
                    for (var i = 0; i < titles.length; i++) {
                        var str = titles[i].JFXMID
                        $("#" + titles[i].JFXMID + "_show").html(wjfjl[str])
//                        $1("#" + titles[i].JFXMID + "_this").val(wjfjl[str])
                    }
                }else{
                    $("#ze_show").html("");
                    for (var i = 0; i < titles.length; i++) {
                        var str = titles[i].JFXMID
                        $("#" + titles[i].JFXMID + "_show").html("")
//                        $1("#" + titles[i].JFXMID + "_this").val(wjfjl[str])
                    }
                }
            },
            //已交费订单
            initYjfXx: function (yjfList, titles) {
                if (yjfList) {
                    var cols = [
                        {title: "订单号", field: "DDH", align: "center", width: "14%"},
                        {title: "交费类型", field: "JFLX", align: "center"},
                        {title: "交费合计", field: "SSHJ", align: "center"},
                        {title: "下单时间", field: "XDSJ", align: "center"}];
                    var colsLen = cols.length;
                    for (var i = 0; i < titles.length; i++) {
                        if (titles[i].SFBX == "1") {
                            cols[i + colsLen] = {title: titles[i].JFXMMC, field: titles[i].JFXMID, align: "center"};
                        } else {
                            cols[i + colsLen] = {
                                title: titles[i].JFXMMC + "（选交）",
                                field: titles[i].JFXMID,
                                align: "center"
                            };
                        }

                    }
                    var colSapnLen = colsLen + titles.length;
                    table.render({
                        elem: '#yjf-order-table'  //容器id
                        , cols: [[
                            {
                                title: "已缴费用信息<font color='red'>（单位元）</font>",
                                colspan: colSapnLen,
                                align: "center"
                            }
                        ], cols],
                        data: yjfList,
                        title: '用户表',
                        page: false, //开启分页
                        loading: true,

                        done: function (res, curr, count) { //加载完回调
                            $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'})
                        },
                        id: 'yjfTable'
                    });
                }
            },

            searchData: function (para) {
                if (para.length > 0) {
                    var data = [];
                    var para = para;
                    for (var i = 0; i < para.length; i++) {
                        data.push(para[i].XM + "_" + para[i].XH + "|" + para[i].XN)
                    }
                    laypage.render({
                        elem: 'demo20'
                        , count: data.length
                        , jump: function (obj) {
                            //模拟渲染
                            document.getElementById('biuuu_city_list').innerHTML = function () {
                                var arr = []
                                        , thisData = data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {
                                    var xm = item.split("_")[0]
                                    var para = item.split("_")[1]
                                    arr.push('<li style="width:100%;border-bottom:1px solid #eee;padding:5px 0;text-decoration: underline;color: #0088ff;cursor: pointer;" onclick="checkData(this)" id="' + para + '">' + xm + '</li>');
                                });
                                return arr.join('');
                            }();
                        }
                    });
                    layer.open({
                        type: 1,
                        area: ['50%', '80%'],
                        title: "请选择学生进行缴费验证",
                        content: $('#search_data') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    });
                } else {
                    layer.msg("当前查询条件内暂无学生信息!", {anim: 6, time: 2000});
                }
            },

            bindCli: function () {
                $('body').keyup(function (e) {
                    if (e.keyCode === 13) {
                        wpt_grjfxx.queryByXh();
                    }
                })

                $("#my-search").bind("click", function () {
                    var sfxn = $('#search-sfxn').val(); //缴费学年
                    var xm = $('#search-xm').val(); //姓名
                    var xh = $('#search-xh').val(); //学号
                    var sfzh = $('#search-sfzh').val(); //身份证

                    $.ajax({
                        url: wpt_serverName + "lsjfgl/dnkp/index",
                        type: 'post',
                        dataType: 'json',
                        data: {sfxn: sfxn, xm: xm, xh: xh, sfzh: sfzh},
                        timeout: 10000,
                        beforeSend: function () {
                            loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                        },
                        success: function (data) {
                            if (data.code == "0") {
                                wpt_grjfxx.searchData(data.list);
                            }
                            else {
                                layer.msg(data.msg, {anim: 6, time: 2000});
                            }
                        },
                        complete: function () {
                            layer.close(loadIndex);
                        }
                    })

                })

                $("#my-save").bind("click", function () {
                    alert(1);
                    var jffs = $("input[name='jffs']:checked").val();
                    var object = {};
                    var xh = $("#XH").html()
                    var xn = $("#XN").html()
                    var ze = 0
                    if (xh != null && xh != "" && xn != null && xn != "") {
                        object['jffs'] = jffs;
                        object['xh'] = xh;
                        object['xn'] = xn;
                        for (var i = 0; i < title.length; i++) {
                            object[title[i].JFXMID] = $("#" + title[i].JFXMID + "_this").val();
                            ze += Number($("#" + title[i].JFXMID + "_this").val());
                        }
                        object['ze'] = ze;
                        var json = JSON.stringify(object);
                        $.ajax({
                            url: wpt_serverName + "lsjfgl/dnkp/save",
                            type: 'post',
                            dataType: 'json',
                            data: {json: json},
                            timeout: 10000,
                            beforeSend: function () {
                                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                            },
                            success: function (data) {
                                if (data.code == "0") {
                                    layer.alert("记录缴费成功!");
                                }
                                else {
                                    layer.msg(data.msg, {anim: 6, time: 2000});
                                }
                            },
                            complete: function () {
                                layer.close(loadIndex);
                            }
                        })
                    } else {
                        layer.msg("请选择需要缴费学生!", {anim: 6, time: 2000});
                    }
                })
            }
        }
        wpt_grjfxx.init();
        wpt_grjfxx.bindCli();
    });
    function checkData(str) {
        var $1 = layui.jquery;
        var xh = $1(str).attr("id").split("|")[0];//学号
        var xn = $1(str).attr("id").split("|")[1];//学年
        var loadIndex;
        $1.ajax({
            url: wpt_serverName + "lsjfgl/dnkp/checkPay",
            type: 'post',
            dataType: 'json',
            data: {xh: xh, xn: xn},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.code == "0") {
                    var para = data.para;
                    var titles = data.titles;
                    title = titles
                    $1("#XN").html(para.XN)
                    $1("#XH").html(para.XH)
                    $1("#XM").html(para.XM)
                    $1("#SFZH").html(para.SFZH)
                    $1("#XYMC").html(para.XYMC)
                    $1("#ZYMC").html(para.ZYMC)
                    $1("#BJMC").html(para.BJMC)
                    $1("#NJ").html(para.NJ)
                    $1("#ze_show").html(para.YSHJ)
                    $1("#ze_this").val(para.YSHJ)
                    for (var i = 0; i < titles.length; i++) {
                        var str = titles[i].JFXMID
                        $1("#" + titles[i].JFXMID + "_show").html(para[str])
                        $1("#" + titles[i].JFXMID + "_this").val(para[str])
                    }
                    layer.closeAll('page'); //关闭所有页面层
                }
                else {
                    layer.msg(data.msg, {anim: 6, time: 2000});
                }
            },
            complete: function () {
                layer.close(loadIndex);
            }
        })
    }

    function changeMoney(th) {
        var lenth = title.length;
        var $1 = layui.jquery;
        var money = $1(th).val();
        var id = $1(th).attr("id")
        var realMoneyId = id.replace("this", "show")
        var realMoney = $1("#" + realMoneyId).html();
        if (money != realMoney && money != "0") {
            layer.msg("非必缴项目只能填写0或者原始金额!", {anim: 6, time: 2000});
            $1(th).val(realMoney)
        }
        var sum_money = 0;
        for (var i = 0; i < title.length; i++) {
            sum_money += Number($1("#" + title[i].JFXMID + "_this").val())
        }
        $1("#ze_this").val(sum_money)


    }
</script>
</body>
</html>
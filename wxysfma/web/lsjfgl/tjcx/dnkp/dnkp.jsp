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
        .layui-form-item .layui-inline {
            margin-right: 14px !important;
        }

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

        .layuiadmin-card-header-auto {
            padding-top: 13px;
        }

        .layui-field-title {
            margin: 5px 0 10px;

        }

        /*}*/
    </style>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<input type="hidden" id="my_xh" value="0">
<jsp:include page="/login/lsauth.jsp"></jsp:include>
<div class="layui-fluid">
    <div>
        <fieldset class="layui-elem-field layui-field-title"
                  style="margin-top: 10px;border-color: #009688 !important;">
            <legend>电脑开票</legend>
        </fieldset>
    </div>


</div>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item" id="my-header" style="margin-bottom: -5px;">
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
                <div class="layui-inline my-cx" style="margin-left: 50px;">
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="search" id="my-search">
                        查询
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-card-body" style="padding: 10px 15px 28px 15px;">
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
                            <td width="7%" style="background-color: #eef9fb">身份证号</td>
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
                    <table class="layui-table" id="jfxxhs" style="margin-top: 30px;">
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
    <div class="layui-card" style="border-top: 1px solid #f6f6f6;">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item" id="my-heade1r" style="margin-bottom: -5px;">
                <div class="layui-inline ">
                    <label class="layui-form-label" style="line-height: 32px;padding-left: 0">缴费方式：</label>

                    <div class="layui-input-inline">
                        <input type="radio" name="jffs" value="CASH" title="现金" checked>
                        <input type="radio" name="jffs" value="CARD" title="刷卡">
                        <input type="radio" name="jffs" value="GXZZ" title="高校转账">
                    </div>
                </div>
                <div class="layui-inline my-cx" style="margin-left: 0px;">
                    <button class="layui-btn layuiadmin-btn-list" lay-filter="search" id="my-save">
                        保存
                    </button>
                </div>
            </div>
        </div>

    </div>
</div>


<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-form-item">
                <div class="layui-form">
                    <table class="layui-table" id="yjf-order-table" lay-filter="jfFilter">
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
<script type="text/html" id="barDemo1">
    {{#  if(d.TFBS == 0){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="dnkp-tf">退费</a>
    {{#  } else if(d.TFBS == 1){ }}
    <font color="red">已退费</font>
    {{#  } }}


</script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script>
    var titleCash;
    var lastCach = {};
    var allCach = [];
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
                            titleCash = data.titles;
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
                            '<td  width="7%"></td> ' +
                            '<td style="color:#4aa4a5;font-weight:bold;width:6%" >总额</td> ';
                            for (var i = 0; i < title.length; i++) {
                                if (title[i].SFBX == "1") {
                                    if (title[i].JFXMMC.length >= 8) {
                                        html += '<td style="color:#4aa4a5;font-weight:bold;width:13%">' + title[i].JFXMMC + '</td> '
                                    } else {
                                        html += '<td style="color:#4aa4a5;font-weight:bold;width:8%">' + title[i].JFXMMC + '</td> '
                                    }
                                } else {
                                    if (title[i].JFXMMC.length >= 4) {
                                        html += '<td style="color:#4aa4a5;font-weight:bold;width:13%">' + title[i].JFXMMC + '<span style="color: #bd9b4a">（选交）</span></td> '
                                    } else {
                                        html += '<td style="color:#4aa4a5;font-weight:bold;width:8%">' + title[i].JFXMMC + '<span style="color: #bd9b4a">（选交）</span></td> '
                                    }
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
                            '<td><input id="ze_this" type="text" onkeyup="value=totalContro(this)"   class="layui-input" style="text-align: center;height: 32px;margin-left: -8px;"></td> ';
                            for (var i = 0; i < title.length; i++) {
                                html += '<td><input  onkeyup="value=zhzs(this)"    id="' + title[i].JFXMID + '_this" class="layui-input" style="text-align: center;height: 32px;margin-left: -8px;"></td>'
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
            queryByXh: function (data) {
                var xh = $("#search-xh").val();
                var xn = $("#search-sfxn").val();
                if ((xh != null && xh.length > 0)) {
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
            //弹窗查询
            queryByXhOpen: function (xh) {
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
                            $("#my_xh").val("0");
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
                } else {
                    $("#XN").html("")
                    $("#XH").html("")
                    $("#XM").html("")
                    $("#SFZH").html("")
                    $("#XYMC").html("")
                    $("#ZYMC").html("")
                    $("#BJMC").html("")
                    $("#NJ").html("")
                }
            },
            //应缴费信息
            initWjf: function (wjfjl, titles) {
                if (wjfjl) {
                    $("#ze_show").html(wjfjl.YSHJ);
                    $("#ze_this").val(wjfjl.YSHJ);
                    allCach.length = 0;
                    for (var i = 0; i < titles.length; i++) {
                        var str = titles[i].JFXMID
                        lastCach[titles[i].JFXMID + "_this"] = wjfjl[str];
                        allCach.push(titles[i].JFXMID + "_" + wjfjl[str]);
                        $("#" + titles[i].JFXMID + "_show").html(wjfjl[str])
                        $("#" + titles[i].JFXMID + "_this").val(wjfjl[str])
                    }
                } else {
                    $("#ze_show").html("");
                    $("#ze_this").val("");
                    for (var i = 0; i < titles.length; i++) {
                        var str = titles[i].JFXMID
                        $("#" + titles[i].JFXMID + "_show").html("")
                        $("#" + titles[i].JFXMID + "_this").val("")
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
                    cols[cols.length] = {title: "操作", align: "center", templet: "#barDemo1", fixed: "right"}
                    var colSapnLen = colsLen + titles.length + 1;
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
                        page: true, //开启分页
                        loading: true,
//                        height: 'full-20',
                        done: function (res, curr, count) { //加载完回调
                            $('th').css({'background-color': '#eef9fb', 'color': '#4aa4a5', 'font-weight': 'bold'})
                        },
                        id: 'yjfTable'
                    });
                }
            },

            bindCli: function () {
                //金额自动补全
                $('body').keyup(function (e) {
                    if (e.keyCode === 13) {
                        wpt_grjfxx.queryByXh();
                    }
                })
                //弹窗查询
                $("#my-search").bind("click", function () {
                    layer.open({
                        type: 2,
                        area: [parseInt(parent.$("#iframe_00").width()) * 0.8 + 'px', parseInt(parent.$("#iframe_00").height()) * 0.89 + 'px'],
                        title: "学生信息",
                        fixed: false, //不固定
                        maxmin: true,
                        content: wpt_serverName + 'lsjfgl/tjcx/dnkp/userInfo.jsp?xn=' + $("#search-sfxn").val(),
                        end: function () {
                            var xh = $("#my_xh").val();
                            if (xh != "0") {
                                $("#search-xh").val(xh);
                                wpt_grjfxx.queryByXhOpen(xh);
                            }
                        }
                    });
                })

                $("#my-save").bind("click", function () {
                    //支付类型
                    var pay_type = $("input[name='jffs']:checked").val();
                    //学号
                    var xh = $("#XH").html()
                    //学年
                    var xn = $("#search-sfxn").val();
                    var object = {};
                    var ze = 0
                    if (xh && xn && $("ze_show").html() != "") {
                        object['pay_type'] = pay_type;
                        object['xh'] = xh;
                        object['xn'] = xn;
                        //缴费信息
                        for (var i = 0; i < titleCash.length; i++) {
                            object[titleCash[i].JFXMID] = $("#" + titleCash[i].JFXMID + "_this").val();
                            ze += Number($("#" + titleCash[i].JFXMID + "_this").val());
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
                                    layer.alert("缴费成功!");
                                    wpt_grjfxx.queryByXh();
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
            },
            listenTool: function () {
//                $("#refresh").bind("click", function () {
//                    window.location.reload();
//                })
                //监听行工具事件
                table.on('tool(jfFilter)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                    var data = obj.data //获得当前行数据
                            , layEvent = obj.event; //获得 lay-event 对应的值
                    var ddh = data.DDH;
                    var xn = data.XN;
                    var xh = data.XH
                    if (layEvent === 'dnkp-tf') {
                        layer.confirm('确定要退费吗？', {
                            btn: ['确定', '取消'] //按钮
                        }, function () {
                            $.ajax({
                                url: wpt_serverName + "lsjfgl/dnkp/tf",
                                type: 'post',
                                dataType: 'json',
                                data: {ddh: ddh, xn: xn, xh: xh},
                                timeout: 10000,
                                beforeSend: function () {
                                    loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                                },
                                success: function (data) {
                                    if (data.code == "0") {
                                        layer.alert("退费成功!");
                                        wpt_grjfxx.queryByXh();
                                    }
                                    else {
                                        layer.msg(data.msg, {anim: 6, time: 2000});
                                    }
                                },
                                complete: function () {
                                    layer.close(loadIndex);
                                }
                            })
                        }, function () {

                        });

                    }
                });
            }
        }
        wpt_grjfxx.init();
        wpt_grjfxx.bindCli();
        wpt_grjfxx.listenTool();
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


    //金额控制
    function zhzs(th) {
        var $1 = layui.jquery;
        var th_id = th.id;
        var value = th.value;
        var show_id = th_id.replace("this", "show");
        var show_val = $1("#" + show_id).html();
        value = value.replace(/[^\d]/g, '');
        if ('' != value) {
            value = parseInt(value);
        }
        if (show_val != "" && value > show_val) {
            value = show_val;
        }
        if ('' == value) {
            value = 0;
        }
        if (show_val != "" && th_id != "ze_this") {
            var lastVal = lastCach[th_id];
            if (lastVal >= value) {
                $1("#ze_this").val(parseInt($1("#ze_this").val()) - parseInt(lastVal - value));
            } else {
                $1("#ze_this").val(parseInt($1("#ze_this").val()) + parseInt(value - lastVal));
            }
            lastCach[th_id] = value;
        }
        return value;
    }

    function totalContro(th) {
        var $1 = layui.jquery;
        var th_id = th.id;
        var value = th.value;
        var show_id = th_id.replace("this", "show");
        var show_val = $1("#" + show_id).html();
        value = value.replace(/[^\d]/g, '');
        if ('' != value) {
            value = parseInt(value);
        }
        if (show_val != "" && value > show_val) {
            value = show_val;
        }
        if ('' == value) {
            value = 0;
        }
        var changeVal = value;
        if (show_val != "") {
            var offCon = true;
            for (var i = 0; i < allCach.length; i++) {
                var id = allCach[i].split("_")[0];
                var orVal = allCach[i].split("_")[1];
                if (offCon) {
                    if (changeVal >= orVal) {
                        $1("#" + id + "_this").val(orVal);
                        lastCach[id + "_this"] = orVal;
                        changeVal = changeVal - orVal;
                    } else {
                        $1("#" + id + "_this").val(changeVal);
                        lastCach[id + "_this"] = changeVal;
                        offCon = false;
                    }
                } else {
                    $1("#" + id + "_this").val(0);
                    lastCach[id + "_this"] = 0;
                }
            }
        }
        return value;
    }


</script>
</body>
</html>
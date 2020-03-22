var wpt_xzf;
layui.use(['form', 'element', 'layer'], function () {
    var form = layui.form;
    var layer = layui.layer;
    var $ = layui.jquery;
    var loadIndex;
    wpt_xzf = {
        noPayOrderInfo: {
            noPayOrder: "no",
            orderInfo: "",
            prepay_id: "",
            payType: ""
        },
        init: function (xzfNormal, xzfList, titles) {
            for (var xzf in xzfList) {
                var currXnxq = xzfList[xzf].XN;
                var sfxm = '<div class="tablediv">' +
                    '<p class="tablename">' + currXnxq + '年度</p>' +
                    '<table border="" cellspacing="" cellpadding="">' +
                    '<tr>' +
                    '<th>选择</th>' +
                    '<th>费用名称</th>' +
                    '<th>应交金额</th>' +
                    '<th>已缴金额</th>' +
                    '<th>欠费金额</th>' +
                    '</tr>';
                var totalMoney = 0;
                var isAllPay = true;
                var xzfInfo = xzfList[xzf];


                for (var title in titles) {
                    var myClass = 'WJF';
                    var myId = '_' + currXnxq + "wow" + titles[title].JFXMID;
                    //已缴金额
                    var yj = xzfList[xzf][titles[title].JFXMID];

                    //应缴金额
                    var yjje = xzfNormal[xzf][titles[title].JFXMID];

                    if (parseInt(yj) ==parseInt(yjje)) {
                        myClass = 'JF';
                    } else {
                        if (titles[title].SFBX == 0) {
                            myClass = 'check bixuan active'
                            myId = 'checkJF_' + currXnxq + '_' + (currXnxq + "wow" + titles[title].JFXMID);
                        }
                    }

                    sfxm += '<tr id="checkId_' + currXnxq + '_' + (currXnxq + "wow" + titles[title].JFXMID) + '">' +
                    '<td>' +
                    '<div style="cursor:pointer" id="' + myId + '" class="' + myClass + '" value="' + yjje + '">' +
                    '</div>' +
                    '</td>' +
                    '<td>' + titles[title].JFXMMC + '</td>' +
                    '<td>' + yjje + '</td>' +
                    '<td>' + yj + '</td>' +
                    '<td>' + (yjje - yj) + '</td>' +
                    '</tr>';
                    totalMoney += parseInt(yjje - yj)

                }
                if (totalMoney > 0) {
                    isAllPay = false;
                }
                var ljjf = '<a href="javascript:void(0)" class="btnjf" id="jfClick_' + currXnxq + '">立即缴费</a>'
                if (isAllPay) {
                    ljjf = '<a href="javascript:void(0)" class="btnjf" ></a>'
                }
                sfxm += '</table>' +
                '<div class="bottom">' +
                '<p id="checkJF_' + currXnxq + '_total" value="' + totalMoney + '">合计：' + totalMoney + '元</p>' +
                ljjf +
                '</div>'
                $("#sfxmList").append(sfxm)
            }
        },
        xzfIndex: function () {
            $.ajax({
                url: wpt_serverName + "wstyzf/xzfSecond",
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
                            wpt_xzf.init(data.dataNormal, data.data, data.titles);
                            wpt_xzf.noPayOrder(data);

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
        bindCheckLy: function () {
            $(document).on('click', 'div[id^="checkJF_"]', function () {
                var myId = this.id;
                var ele = $("#" + myId);
                var myTotalId = myId.slice(0, myId.lastIndexOf("_")).split("wow")[0] + "_total";
                var totalEle = $("#" + myTotalId);
                var myTotalVal = parseInt(totalEle.attr("value"));
                var currVal = parseInt(ele.attr("value"));
                if (ele.attr("class") == 'check bixuan active') {
                    ele.attr("class", "check")
                    totalEle.attr("value", (myTotalVal - currVal));
                    totalEle.html(" 合计：" + (myTotalVal - currVal) + "元")

                } else {
                    ele.attr("class", "check bixuan active");
                    totalEle.attr("value", (myTotalVal + currVal));
                    totalEle.html(" 合计：" + (myTotalVal + currVal) + "元")
                }
            })
        },
        bindZf: function () {
            $(document).on('click', 'a[id^="jfClick_"]', function () {
                var thiId = this.id;
                var xnxq = thiId.slice(thiId.lastIndexOf("_"), thiId.length);
                var eleS = $('tr[id^="checkId' + xnxq + '"]');
                //支付ID
                var arrId = new Array();
                for (var i = 0; i < eleS.length; i++) {
                    var zfId = eleS.eq(i).attr("id");
                    var ele = eleS.eq(i)
                    var myClass = ele.find("td div").attr("class");
                    if (myClass == "check bixuan active" || myClass == "WJF") {
                        arrId.push(zfId.slice(zfId.lastIndexOf("_") + 1, zfId.length))
                    }
                }
                wpt_xzf.pay(arrId);

            })
        },
        onBridgeReady: function (obj) {
            WeixinJSBridge.invoke('getBrandWCPayRequest', {
                    "appId": obj.appId,     //公众号名称,由商户传入
                    "timeStamp": obj.timeStamp,         //时间戳,自1970年以来的秒数
                    "nonceStr": obj.nonceStr, //随机串
                    "package": obj.package,
                    "signType": obj.signType,         //微信签名方式：
                    "paySign": obj.paySign //微信签名
                },
                function (res) {
                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                        console.log('支付成功');
                        window.location.replace(location.href)
                        //支付成功后跳转的页面
                    } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                        console.log('支付取消');
                        window.location.replace(location.href)
                    } else if (res.err_msg == "get_brand_wcpay_request:fail") {
                        console.log('支付失败');
                        WeixinJSBridge.call('closeWindow');
                    } //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok,但并不保证它绝对可靠。
                });
        },
        pay: function (arrId) {
            var newArrid;
            if (arrId.length > 1) {
                newArrid = arrId.join(",");
            } else {
                newArrid = arrId[0];
            }
            $.ajax({
                url: wpt_serverName + "wstyzf/xzfSecond/zfXzf",
                type: 'post',
                dataType: 'json',
                timeout: 10000,
                data: {arrId: newArrid},
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
                            var obj = {
                                appId: data.appId,
                                timeStamp: data.timeStamp,
                                nonceStr: data.nonceStr,
                                package: data.package,
                                signType: data.signType,
                                paySign: data.paySign
                            }

                            if (typeof WeixinJSBridge == "undefined") {
                                if (document.addEventListener) {
                                    document.addEventListener('WeixinJSBridgeReady',
                                        wpt_xzf.onBridgeReady(obj), false);
                                } else if (document.attachEvent) {
                                    document.attachEvent('WeixinJSBridgeReady',
                                        wpt_xzf.onBridgeReady(obj));
                                    document.attachEvent('onWeixinJSBridgeReady',
                                        wpt_xzf.onBridgeReady(obj));
                                }
                            } else {
                                wpt_xzf.onBridgeReady(obj);
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

        },
        bindShowOrder: function () {
            var payType = wpt_xzf.noPayOrderInfo.payType;

            var orderInfos = wpt_xzf.noPayOrderInfo.orderInfo;
            var nd = orderInfos[0].xnxq;
            $(document).on('click', 'a[id^="jfClick_"]', function () {
                var noPayOrderHtml = '<p class="tablename">' + nd + '年度</p>' +
                    '<table border = ""cellspacing = "" cellpadding = "" > ' +
                    '<tbody>' +
                    '<tr>' +
                    '<th>费用名称</th>' +
                    '<th>应交金额</th>' +
                    '<th>已缴金额</th>' +
                    '<th>欠费金额</th>' +
                    '</tr>';
                var totalMoney = 0;
                for (var i = 0; i < orderInfos.length; i++) {
                    var yj = orderInfos[i].yj;
                    var jfje = orderInfos[i].jfje;
                    noPayOrderHtml += '<tr>' +
                    '<td>' + orderInfos[i].xmmc + '</td>' +
                    '<td>' + orderInfos[i].jfje + '</td>' +
                    '<td>0</td>' +
                    '<td>' + orderInfos[i].jfje + '</td>' +
                    '</tr>';
                    totalMoney += parseInt(orderInfos[i].jfje);
                }
                noPayOrderHtml += '</tbody>' +
                '</table>';

                noPayOrderHtml += '<div class="bottom">' +
                '<p>合计：' + totalMoney + '</p>' +
                '</div>';
                $("#noPayOrderTable").html(noPayOrderHtml);
                $("#tanchuddId").show();
            })

            $("#closeOrder").bind("click", function () {
                $.ajax({
                    url: wpt_serverName + "wstyzf/xzfSecond/closeOrder",
                    type: 'post',
                    dataType: 'json',
                    timeout: 10000,
                    data: {prepay_id: wpt_xzf.noPayOrderInfo.prepay_id},
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
                                window.location.reload();
                            } else {
                                layer.msg(msg, {anim: 6, time: 2000});
                            }
                        }
                    },
                    complete: function () {
                        layer.close(loadIndex);
                    }
                })
            })
            if (payType == "JSAPI") {
                $("#finishOrder").bind("click", function () {
                    $.ajax({
                        url: wpt_serverName + "wstyzf/xzfSecond/rezfXzf",
                        type: 'post',
                        dataType: 'json',
                        timeout: 10000,
                        data: {prepay_id: wpt_xzf.noPayOrderInfo.prepay_id},
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
                                    var obj = {
                                        appId: data.appId,
                                        timeStamp: data.timeStamp,
                                        nonceStr: data.nonceStr,
                                        package: data.package,
                                        signType: data.signType,
                                        paySign: data.paySign
                                    }

                                    if (typeof WeixinJSBridge == "undefined") {
                                        if (document.addEventListener) {
                                            document.addEventListener('WeixinJSBridgeReady',
                                                wpt_xzf.onBridgeReady(obj), false);
                                        } else if (document.attachEvent) {
                                            document.attachEvent('WeixinJSBridgeReady',
                                                wpt_xzf.onBridgeReady(obj));
                                            document.attachEvent('onWeixinJSBridgeReady',
                                                wpt_xzf.onBridgeReady(obj));
                                        }
                                    } else {
                                        wpt_xzf.onBridgeReady(obj);
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
                })
            } else {
                //$("#closeOrder").css("width","100%");
                $("#finishOrder").hide();
                $("#cancleOrder").show();

            }
        },

        noPayOrder: function (data) {
            wpt_xzf.noPayOrderInfo.noPayOrder = data.noPayOrder;
            if (data.noPayOrder == "yes") {
                wpt_xzf.noPayOrderInfo.orderInfo = data.orderInfo;
                wpt_xzf.noPayOrderInfo.prepay_id = data.prepay_id;
                wpt_xzf.noPayOrderInfo.payType = data.payType;
                wpt_xzf.bindShowOrder();
            } else {
                wpt_xzf.bindZf();
            }

        }
    }

    $("#cancleOrder").bind("click",function(){
        $("#tanchuddId").hide();
    });
    wpt_xzf.xzfIndex();
    wpt_xzf.bindCheckLy();


});
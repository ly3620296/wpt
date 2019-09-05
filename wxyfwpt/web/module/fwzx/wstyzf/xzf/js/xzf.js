var wpt_xzf;
layui.use(['form', 'element', 'layer'], function () {
    var form = layui.form;
    var layer = layui.layer;
    var $ = layui.jquery;
    var loadIndex;
    wpt_xzf = {
        init: function (xzfList, xzfXnxqList) {
            for (var xnxq in xzfXnxqList) {
                var currXnxq = xzfXnxqList[xnxq].XNXQ;
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
                for (var xzf in xzfList) {
                    var xzfInfo = xzfList[xzf]
                    if (xzfInfo.XNXQ != currXnxq) {
                        continue;
                    }
                    var myClass = '';
                    var myId = '_' + xzfInfo.ID;
                    if (xzfInfo.SFJF == 1) {
                        myClass = '';
                    } else {
                        if (xzfInfo.SFBX == 0) {
                            myClass = 'check bixuan active'
                            myId = 'checkJF_' + currXnxq + '_' + xzfInfo.ID;
                        }
                    }
                    sfxm += '<tr id="checkId_' + currXnxq + '_' + xzfInfo.ID + '">' +
                    '<td>' +
                    '<div style="cursor:pointer" id="' + myId + '" class="' + myClass + '" value="' + xzfInfo.XMJE + '">' +
                    '</div>' +
                    '</td>' +
                    '<td>' + xzfInfo.XMMC + '</td>' +
                    '<td>' + xzfInfo.XMJE + '</td>' +
                    '<td>' + (xzfInfo.SFJF == 1 ? xzfInfo.XMJE : 0) + '</td>' +
                    '<td>' + (xzfInfo.SFJF == 0 ? xzfInfo.XMJE : 0) + '</td>' +
                    '</tr>';
                    if (xzfInfo.SFJF == 0) {
                        totalMoney += parseInt(xzfInfo.XMJE)
                    }

                }
                sfxm += '</table>' +
                '<div class="bottom">' +
                '<p id="checkJF_' + currXnxq + '_total" value="' + totalMoney + '">合计：' + totalMoney + '元</p>' +
                '<a href="javascript:void(0)" class="btnjf" id="jfClick_' + currXnxq + '">立即缴费</a>' +
                '</div>'
                $("#sfxmList").append(sfxm)
            }
        },
        xzfIndex: function () {
            $.ajax({
                url: wpt_serverName + "wstyzf/xzf",
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
                            wpt_xzf.init(data.xzfList, data.xzfXnxqList);
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
                var myTotalId = myId.slice(0, myId.lastIndexOf("_")) + "_total";
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
                    if (myClass == "check bixuan active" || myClass == "") {
                        arrId.push(zfId.slice(zfId.lastIndexOf("_") + 1, zfId.length))
                    }
                }
                wpt_xzf.pay();

            })
        },
        onBridgeReady: function (obj) {
            WeixinJSBridge.invoke('getBrandWCPayRequest', {
                    "appId": appId,     //公众号名称,由商户传入
                    "timeStamp": timeStamp,         //时间戳,自1970年以来的秒数
                    "nonceStr": nonceStr, //随机串
                    "package": package,
                    "signType": signType,         //微信签名方式：
                    "paySign": paySign //微信签名
                },
                function (res) {
                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                        console.log('支付成功');
                        //支付成功后跳转的页面
                    } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                        console.log('支付取消');
                    } else if (res.err_msg == "get_brand_wcpay_request:fail") {
                        console.log('支付失败');
                        WeixinJSBridge.call('closeWindow');
                    } //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok,但并不保证它绝对可靠。
                });
        },
        pay: function () {
            $.ajax({
                url: wpt_serverName + "wstyzf/xzf/zfXzf",
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
                            var obj = {
                                appId: result.appId,
                                timeStamp: result.timeStamp,
                                nonceStr: result.nonceStr,
                                package: result.package,
                                signType: result.signType,
                                paySign: result.paySign
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

        }
    }
    wpt_xzf.xzfIndex();
    wpt_xzf.bindCheckLy();
    wpt_xzf.bindZf();

});
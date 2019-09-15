<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title></title>
</head>
<body>
<input id="code" type="button" value="微信支付" onclick="pay()"/>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
<script type="text/javascript">
    var appId, timeStamp, nonceStr, package, signType, paySign;
    function pay() {
        var url = wpt_serverName + "wstyzf/xzf/rezfXzf";
        $.get(url, function (result) {
            appId = result.appId;
            timeStamp = result.timeStamp;
            nonceStr = result.nonceStr;
            package = result.package;
            signType = result.signType;
            paySign = result.paySign;

            if (typeof WeixinJSBridge == "undefined") {
                if (document.addEventListener) {
                    document.addEventListener('WeixinJSBridgeReady',
                            onBridgeReady, false);
                } else if (document.attachEvent) {
                    document.attachEvent('WeixinJSBridgeReady',
                            onBridgeReady);
                    document.attachEvent('onWeixinJSBridgeReady',
                            onBridgeReady);
                }
            } else {
                onBridgeReady();
            }
        });
    }

    function onBridgeReady() {
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
    }
</script>

</body>
</html>

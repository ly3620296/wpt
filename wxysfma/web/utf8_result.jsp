<%@ page import="gka.resource.Constant" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
    <style type="text/css">
        body table tr td {
            font-size: 14px;
            word-wrap: break-word;
            word-break: break-all;
            empty-cells: show;
        }
    </style>
</head>
<body>
<input type="hidden" value="${code}" id="code">
<input type="hidden" value="${msg }" id="msg">
<input type="hidden" value="${txnAmt }" id="txnAmt">
<input type="hidden" value="${orderId }" id="orderId">


</body>
<script>
    $(function () {
        var code = $("#code").val();
        var msg = $("#msg").val();
        var txnAmt = $("#txnAmt").val();
        var orderId = $("#orderId").val();
        if (code == "00") {
            layer.alert('成功支付' + txnAmt + "元，订单号：" + orderId, {
                icon: 6, end: function () {
                    parent.location.reload();
                }
            });
        } else {
            layer.alert(msg, {
                icon: 5, end: function () {
                    parent.location.reload();
                }
            });
        }
    })
</script>
</html>
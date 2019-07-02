<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>哈哈哈</h1>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script>
    $(function () {
        $.ajax({
            url: "/wpt/index2.jsp?a=aasd%20cc_2343",
            type: 'get',
            dataType: 'json',
            data: {b:"aasd%20cc_2343"},
//            contentType:"multipart/form-data",
            timeout: 10000,
            beforeSend: function () {
            },
            success: function (data) {
            }
            ,
            complete: function () {
            }

        })
    })
</script>
</body>
</html>
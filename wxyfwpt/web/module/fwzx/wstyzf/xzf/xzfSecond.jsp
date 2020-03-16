<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>js-lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/title.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/zf.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/zf.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title></title>
</head>
<body>
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="titledddiv">
    <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
         onclick="javascript:window.location.replace('/wpt/module/fwzx/fwzxapp.jsp')"/>

    <p class="titleName">学杂费</p>
</div>

<div class="list" id="sfxmList">

</div>

<div class="tanchudd" style="display: none" id="tanchuddId">
    <div class="cont list">
        <div class="tablediv" id="noPayOrderTable">

        </div>

        <div class="foot">
            <a class="btnno" href="javascript:void(0)" id="closeOrder">关闭订单</a>
            <a class="btnyes" href="javascript:void(0)" id="finishOrder">继续支付</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=Constant.server_name%>module/fwzx/wstyzf/xzf/js/xzfSecond.js" charset="utf-8"></script>

</body>
</html>

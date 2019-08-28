<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/message.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/seclect.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="table.css"/>
    <script src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/swiper/swiper-3.4.2.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title></title>
</head>
<body>
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="messageMore">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
             onclick="javascript:window.location.replace(document.referrer)"/>

        <p class="titleName">详细信息</p>
    </div>
    <div class="first">
        <p class="titleT">辽宁机电职业技术学院财务工资模块升级及服务项目采购成交公告</p>

    </div>
    <div class="contT">
        <p> 辽宁机电职业技术学院对财务工资模块升级及服务项目进行了竞争性磋商，现将结果公告如下：</p>

        <p> 1、采购方式：竞争性磋商</p>

        <p> 2、采购项目名称：辽宁机电职业技术学院财务工资模块升级及服务</p>

        <p> 3、采购结果如下：</p>

        <p> 成交供应商名称：辽宁长众科技有限公司</p>

        <p> 成交价格：柒万伍仟元整（75,000.00元）</p>
    </div>
    <div class="footT">
        <p>辽宁机电职业技术学院设备管理处</p>

        <p>2019-7-12</p>
    </div>
</div>
</body>
</html>
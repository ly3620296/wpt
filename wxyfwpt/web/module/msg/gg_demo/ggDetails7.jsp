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

       <p>大连市机电设备招标有限责任公司对辽宁机电职业技术学院2019年学生公寓被褥等床上用品采购进行了竞争性磋商采购，现将采购结果公告如下：</p>
       <p>1、项目编号：LNJDCG-2019-001</p>
       <p>2、采购项目名称：辽宁机电职业技术学院2019年学生公寓被褥等床上用品采购</p>
       <p>3、采购内容：学生公寓床上生活用品（棉被1床、棉褥1床、被罩2条、床单2条、夏被1床、枕巾2条、枕套1条、枕芯1个，每套共计 11件</p>
       <p>4、磋商小组成员：齐春桥、杨芙蓉、刘岱熹</p>
       <p>5、采购结果如下：</p>
       <p>中选成交供应商名称：沈阳双鑫棉制品有限责任公司；</p>
       <p>成交价（人民币元）：单价每套（￥460）；</p>
       <p>参与本次采购活动的供应商如对以上成交结果有疑义，可自本通告发布之日起7个工作日内，请致电学院纪委：0415-3853922  黄老师。</p>
    </div>
    <div class="footT">
        <p>辽宁机电职业技术学院</p>

        <p>2019-7-10</p>
    </div>
</div>
</body>
</html>
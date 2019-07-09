<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="gka.resource.Constant" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/title.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/xl.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title>校历</title>
</head>
<body>
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="titledddiv">
    <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
         onclick="javascript:window.location.replace(document.referrer)"/>

    <p class="titleName">校历</p>
</div>

<div style="">
    <div class="school">
        <img src="<%=Constant.server_name%>/img/top-app5.jpg">
    </div>
    <div class="lt">
        <p id="xnxq"></p>
    </div>
</div>
<div class="xxtable">
    <table>
        <thead>
        <tr>
            <th style="border-right: 1px solid #f3f3f3;">周次</th>
            <th>一</th>
            <th>二</th>
            <th>三</th>
            <th>四</th>
            <th>五</th>
            <th>六</th>
            <th>日</th>
        </tr>
        </thead>
        <tbody id="xl_by">

        </tbody>
    </table>
</div>
</body>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script src="<%=Constant.server_name%>module/fwzx/jwl/xl/js/xl.js" charset="utf-8"></script>
</html>
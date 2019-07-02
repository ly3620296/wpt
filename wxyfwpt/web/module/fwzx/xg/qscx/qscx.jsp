<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>js-lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/seclect.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/alert/common.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title></title>
</head>
<body style="background-color: #f3f3f3">
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="ccj">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png" onclick="javascript:window.location.replace(document.referrer)"/>
        <p class="titleName">寝室查询</p>
    </div>

    <div class="layui-form" lay-filter="qxcx_ld">
        <div class="layui-input-block">
            <select name="interest" id="qxcx_ld" lay-filter="qxcx_ld_se">
                <option value=""></option>
            </select>
        </div>
    </div>

    <div class="layui-form" lay-filter="qxcx_qs">
        <div class="layui-input-block">
            <select name="interest" id="qxcx_qs" lay-filter="qxcx_qs_se">
                <option value=''>请选择（寝室）</option>
            </select>
        </div>
    </div>

    <div style="width: 96%;margin-left: 2%;overflow: hidden;">
        <input type="" id="xh"
               style="width: 80%;height: 34px;vertical-align: middle;border: none;text-indent: 1em;float: left;"
               placeholder='请填写学号'>
        <button type="button" class="layui-btn layui-btn-normal" id="cxxh"
                style="height: 34px;line-height: 34px;width: 18%;padding: 0;margin-left: 2%;float: left;">查询
        </button>

    </div>

    <table border="0" cellspacing="" cellpadding="">
        <thead>
        <tr>
            <th>床位号</th>
            <th>学号</th>
            <th>姓名</th>
            <th>性别</th>
        </tr>
        </thead>
        <tbody id="wpt_xs_table">

        </tbody>
    </table>
</div>
<%--弹窗--%>
<jsp:include page="/common/alert.jsp"></jsp:include>
</body>

<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script src="<%=Constant.server_name%>module/fwzx/xg/qscx/js/qscx.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
</html>


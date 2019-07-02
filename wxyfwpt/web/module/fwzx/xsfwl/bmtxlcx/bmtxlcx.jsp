<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.controller.login.WptUserInfo" %>
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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/gzcx.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title></title>

</head>
<body style="background: #f3f3f3;">
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
%>
<div class="gzcx">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png" onclick="javascript:window.location.replace(document.referrer)"/>

        <p class="titleName">部门通讯录</p>
    </div>

    <div class="divname">
        <img class="leftimg" src="<%=Constant.server_name%>img/txl-icon.png"/>

        <div class="right">
            <p>姓名：<%=wptUser.getXm()%>
            </p>

            <p>学号：<%=wptUser.getZh()%>
            </p>
        </div>
    </div>

    <div class="selectDiv">
        <form class="layui-form">
            <div class="layui-input-block" style="width: 100%;margin: 2% 0%;">
                <select name="interest" id="jskb_zc" lay-filter="jskb_zc">
                    <option value="">请选择部门</option>
                    <option value="">教务处</option>
                    <option value="">校长室</option>
                    <option value="">学生处</option>
                </select>
            </div>
        </form>
    </div>

    <ul class="textlist">
        <li>
            <p class="lefttext"><i class="fa fa-address-book fa-lg"></i>李晓红</p>

            <p class="righttext">18844092305</p>
        </li>
        <li>
            <p class="lefttext"><i class="fa fa-address-book fa-lg"></i>王小懒</p>

            <p class="righttext">13944402683</p>
        </li>
        <li>
            <p class="lefttext"><i class="fa fa-address-book fa-lg"></i>张大胖</p>

            <p class="righttext">13943778933</p>
        </li>
        <li>
            <p class="lefttext"><i class="fa fa-address-book fa-lg"></i>赵小欠</p>

            <p class="righttext">15754303333</p>
        </li>
    </ul>


</div>

<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use('form', function () {
        var form = layui.form,
                $ = layui.jquery;
    })
</script>
</body>

</html>








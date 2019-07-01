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

        <p class="titleName">图书馆违章</p>
    </div>

    <div class="divname">
        <img class="leftimg" src="<%=Constant.server_name%>img/icon_wqtsg.png"/>

        <div class="right">
            <p>姓名：<%=wptUser.getXm()%>
            </p>

            <p>学号：<%=wptUser.getZh()%>
            </p>
        </div>
    </div>

    <div class="layui-collapse" lay-accordion="">
        <div class="layui-colla-item">
            <h2 class="layui-colla-title">第一条<span class="time-text">2015-02-02</span></h2>

            <div class="layui-colla-content">
                <ul class="textlist">
                    <li>
                        <p class="lefttext">读者证号</p>

                        <p class="righttext">131500</p>
                    </li>
                    <li>
                        <p class="lefttext">正题名</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">违章类别</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">违章原因</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">应罚金额</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">实罚金额</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">付款方式</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">办理时间</p>

                        <p class="righttext">2019-02-01</p>
                    </li>
                    <li>
                        <p class="lefttext">最后更新时间</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                </ul>
            </div>
        </div>

        <div class="layui-colla-item">
            <h2 class="layui-colla-title">第二条<span class="time-text">2015-02-02</span></h2>

            <div class="layui-colla-content ">
                <ul class="textlist">
                    <li>
                        <p class="lefttext">读者证号</p>

                        <p class="righttext">131500</p>
                    </li>
                    <li>
                        <p class="lefttext">正题名</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">违章类别</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">违章原因</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">应罚金额</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">实罚金额</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">付款方式</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">办理时间</p>

                        <p class="righttext">2019-02-01</p>
                    </li>
                    <li>
                        <p class="lefttext">最后更新时间</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                </ul>
            </div>
        </div>

    </div>


</div>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use(['element', 'layer', 'form'], function () {
        var element = layui.element;
        var layer = layui.layer;
        var form = layui.form;

        //监听折叠
//	  element.on('collapse(test)', function(data){
//	    layer.msg('展开状态：'+ data.show);
//	  });
    });
</script>
</body>

</html>








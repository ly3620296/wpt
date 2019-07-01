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
        <p class="titleName">图书借阅查询</p>
    </div>

    <div class="divname">
        <img class="leftimg" src="<%=Constant.server_name%>img/logo-tsg.png"/>

        <div class="right">
            <p>姓名：<%=wptUser.getXm()%>
            </p>

            <p>学号：<%=wptUser.getZh()%>
            </p>
        </div>
    </div>

    <ul class="bar">
        <li class="li1 libarlist">已还</li>
        <li class="li2">未还</li>
    </ul>


    <div class="layui-collapse div11" lay-accordion="">
        <div class="layui-colla-item">
            <h2 class="layui-colla-title">第一条（已还）<span class="time-text">2015-02-02</span></h2>

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
                        <p class="lefttext">借出时间</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">限还时间</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">续借次数</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">流通类型</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">文献类型</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">书期号</p>

                        <p class="righttext">2019-02-01</p>
                    </li>
                    <li>
                        <p class="lefttext">实还时间</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">出版者</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">出版日期</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">已借数</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">是否归还</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">归还时间</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">归还时间</p>

                        <p class="righttext">最后更新时间</p>
                    </li>
                </ul>
            </div>
        </div>
    </div>


    <div class="layui-collapse div22" lay-accordion="" style="display: none;">
        <div class="layui-colla-item">
            <h2 class="layui-colla-title">第一条(未还)<span class="time-text">2015-02-30</span></h2>

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
                        <p class="lefttext">借出时间</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">限还时间</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">续借次数</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">流通类型</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">文献类型</p>

                        <p class="righttext">6000</p>
                    </li>
                    <li>
                        <p class="lefttext">书期号</p>

                        <p class="righttext">2019-02-01</p>
                    </li>
                    <li>
                        <p class="lefttext">实还时间</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">出版者</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">出版日期</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">已借数</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">是否归还</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">归还时间</p>

                        <p class="righttext">2019-05-02</p>
                    </li>
                    <li>
                        <p class="lefttext">归还时间</p>

                        <p class="righttext">最后更新时间</p>
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
        var $ = layui.jquery;

        $(".bar .li1").click(function () {
            $(".div11").show().siblings(".div22").hide()
        })
        $(".bar li").click(function () {
            $(this).addClass("libarlist").siblings().removeClass("libarlist")
        })
        $(".bar .li2").click(function () {
            $(".div22").show().siblings(".div11").hide()
        })
        //监听折叠
//	  element.on('collapse(test)', function(data){
//	    layer.msg('展开状态：'+ data.show);
//	  });
    });

    //    $(function () {
    //        $(".bar .li1").click(function () {
    //            $(".div11").show().siblings(".div22").hide()
    //        })
    //        $(".bar li").click(function () {
    //            $(this).addClass("libarlist").siblings().removeClass("libarlist")
    //        })
    //        $(".bar .li2").click(function () {
    //            $(".div22").show().siblings(".div11").hide()
    //        })
    //    })
</script>
</body>

</html>









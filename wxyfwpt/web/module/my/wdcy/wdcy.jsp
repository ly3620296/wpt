<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/my.css"/>

    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>/js-lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <script src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/swiper/swiper-3.4.2.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>

    <style type="text/css">
        .layui-unselect {
            display: none;
        }

        .layui-tab-title li {
            color: #0099db !important;
        }

        .tc li .fa-plus-square {
            color: #18ec63;
        }

        .layui-tab-brief > .layui-tab-title .layui-this {
            background: #0099db;
        }

        .layui-tab-title {
            border-bottom: 1px solid #0099db !important;
        }

        .layui-tab-brief > .layui-tab-more li.layui-this:after, .layui-tab-brief > .layui-tab-title .layui-this:after {
            border-bottom: 1px solid #0099db !important;
        }
    </style>
    <title>我的常用</title>
</head>
<body>
<div class="app">
    <div class="tcDiv">
        <div class="tclist">
            <div class="tc">
                <div class="name">
                    <p>我的常用</p>
                    <a class="btnbj" href="<%=Constant.server_name%>module/main/main.jsp">完成</a>
                </div>
                <ul id="myMenu">

                </ul>
            </div>
        </div>
    </div>

    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
        <!--<div class="layui-tab layui-tab-card" >-->
        <ul class="layui-tab-title" style="width: 100%;overflow-x: scroll;" id="tab_change">

        </ul>
        <div class="layui-tab-content " style="height: 100px;" id="menu_html">

        </div>
    </div>
</div>
</body>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=Constant.server_name%>module/my/wdcy/js/wdcy.js"></script>
</html>
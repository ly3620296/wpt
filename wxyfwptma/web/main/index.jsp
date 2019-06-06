<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.controller.login.WptMaUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/admin.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>main/index.js"></script>
</head>
<!--<span class="layui-unselect layui-tab-bar" lay-stope="tabmore" title="" style="-->
<body class="layui-layout-body">
<jsp:include page="/login/auth.jsp"></jsp:include>
<%
    WptMaUserInfo userInfo = (WptMaUserInfo) session.getAttribute("wptMaUserInfo");
    String userName = "";
    if (userInfo != null) {
        userName = userInfo.getM_name();
    }

%>
<div class="layui-layout layui-layout-admin">
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <div class="layui-header">
        <div class="layui-logo">微平台后台管理</div>
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a lay-href="">控制台</a></li>
            <li class="layui-nav-item"><a lay-href="">商品管理</a></li>
            <li class="layui-nav-item"><a lay-href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="#"><span class="nav-ly-child">邮件管理</span></a></dd>
                    <dd><a href="#"><span class="nav-ly-child">消息管理</span></a></dd>
                    <dd><a href="#"><span class="nav-ly-child">授权管理</span></a></dd>
                </dl>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <%--<img src="http://t.cn/RCzsdCq" class="layui-nav-img">--%>
                    <%--贤心--%>
                    <%=userName%>
                </a>
                <dl class="layui-nav-child">
                    <dd><a href=""><span class="nav-ly-child">基本资料</span></a></dd>
                    <dd><a href=""><span class="nav-ly-child">安全设置</span></a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="" id="loginOut">退出</a></li>
        </ul>
    </div>

    <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
    <div class="layui-side layui-bg-black" id="layui-left-side">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" lay-shrink="all" lay-filter="left-side">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">
                        <i class="layui-icon layui-icon-home"></i>
                        <cite>基础功能</cite></a>
                    <dl class="layui-nav-child">
                        <dd class="layui-this">
                            <a data-url="ly_home" data-id="ly_home" data-title="table-demo"
                               data-type="tabAdd">欢迎</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>module/notice/list.jsp" data-id="00" data-title="公告管理"
                               data-type="tabAdd">公告管理</a>
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <!--页面标签栏-->
    <div class="layadmin-pagetabs" id="LAY_app_tabs">
        <div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage" style="left: 0"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-down">
            <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
                <li class="layui-nav-item" lay-unselect id="hover_div">
                    <a href="javascript:;" class="index_a_tab" id="hover_div1"></a>
                    <!--layui-nav-child-my-->
                    <dl class="layui-nav-child layui-anim-fadein layui-nav-child-my">
                        <dd id="closeThisTab" lay-unselect><a href="javascript:;">关闭当前标签页</a></dd>
                        <dd id="closeOtherTabs" lay-unselect><a href="javascript:;">关闭其它标签页</a></dd>
                        <dd id="closeAllTabs" lay-unselect><a href="javascript:;">关闭全部标签页</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
        <!--lay-unauto禁用选项卡宽度不够时自动换行-->
        <div class="layui-tab" lay-filter="layadmin-layout-tabs" lay-unauto lay-allowclose="true">
            <ul class="layui-tab-title" id="LAY_app_tabsheader">
                <li lay-id="ly_home" lay-attr="ly_home" style="height: 40px;" class="layui-this"><i
                        class="layui-icon layui-icon-home "></i></li>
            </ul>
        </div>
    </div>
    <!--主体内容   -->
    <div class="layui-body layui-body-ly" id="LAY_app_body">
        <div class="layadmin-tabsbody-item layui-show" id="iframe_ly_home">
            <iframe data-frameid="ly_home" scrolling="no" frameborder="0"
                    src="<%=Constant.server_name%>ly_home.html?ran=2" frameborder="0"
                    class="layadmin-iframe" style="width: 100%; height: 363px;"></iframe>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/index.js"></script>
</body>
</html>
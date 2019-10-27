<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
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
    <title>学生缴费管理</title>
</head>
<body class="layui-layout-body">
<jsp:include page="/login/xsauth.jsp"></jsp:include>
<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
    String userName = "";
    if (userInfo != null) {
        userName = userInfo.getXm();
        System.out.println("userName" + userName);
    }
%>
<div class="layui-layout layui-layout-admin">
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <div class="layui-header">
        <div class="layui-logo">学生交费管理</div>
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
            <ul class="layui-nav layui-nav-tree" lay-shrink="all" lay-filter="left-side" id="menu">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">
                        <i class="layui-icon layui-icon-home"></i>
                        <cite>学生交费管理</cite></a>
                    <dl class="layui-nav-child">
                        <dd class="layui-this">
                            <a data-url="" data-id="ly_home" data-title="table-demo"
                               data-type="tabAdd">个人交费信息</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>xsjfgl/grjfxx.jsp" data-id="00" data-title="个人交费信息"
                               data-type="tabAdd">个人交费信息</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>xsjfgl/wyjf.jsp" data-id="01" data-title="我要交费"
                               data-type="tabAdd">我要交费</a>
                        </dd>
                        <dd>
                            <a data-url="demo-switch/switch.html" data-title="轮播" data-id="02"
                               data-type="tabAdd">订单查询</a>
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
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/index.js"></script>
</body>
<script type="application/javascript">
    $(function () {
        $("#loginOut").on("click", function () {
            var loadIndex;
            $.ajax({
                url: wpt_serverName + "XSlogin/loginOut",
                type: 'post',
                dataType: 'json',
                data: {},
                timeout: 10000,
                beforeSend: function () {
                    loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                },
                success: function (data) {
                    if (data) {
                        var code = data.return_code;
                        var msg = data.return_msg;
                        if (code == "0") {
//                            window.location.href = wpt_serverName + "login/xslogin.jsp";
                        } else {
                            layer.msg("登出失败,请稍后再试!", {anim: 6, time: 2000});
                        }
                    }
                },
                complete: function () {
                    layer.close(loadIndex);
                }
            })
        })
    })
</script>
</html>
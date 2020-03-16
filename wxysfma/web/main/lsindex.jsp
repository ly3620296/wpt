<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.lsjfgl.login.WptMaLSUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/admin.css">
    <link rel="stylesheet" href="<%=Constant.server_name%>css/myCommon.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/echarts4.40/echarts.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/echarts4.40/map/js/china.js"></script>
    <style>
        #iframe_ly_home {
            overflow: scroll !important;
        }
    </style>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
    <title>教师交费管理</title>
</head>
<body class="layui-layout-body">
<%
    WptMaLSUserInfo userInfo = (WptMaLSUserInfo) session.getAttribute("wptMaLSUserInfo");
    String userName = "";
    if (userInfo != null) {
        userName = userInfo.getXm();
        System.out.println("userName" + userName);
    } else {
%>
<script>
    window.location.href = wpt_serverName + "login/lslogin.jsp";
</script>
<%
    }
%>
<div class="layui-layout layui-layout-admin">
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <div class="layui-header">
        <div class="layui-logo">教师交费管理</div>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <%=userName%>
                </a>
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
                        <i class="layui-icon layui-icon-template-1"></i>
                        <cite>票据管理</cite></a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a data-url="<%=Constant.server_name%>lsjfgl/tjcx/dnkp/dnkp.jsp" data-id="00"
                               data-title="电脑开票"
                               data-type="tabAdd">电脑开票</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>xsjfgl/wyjf.jsp" data-id="01" data-title="票据打印"
                               data-type="tabAdd">票据打印</a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a class="" href="javascript:;">
                        <i class="layui-icon layui-icon-user"></i>
                        <cite>统计查询</cite></a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a data-url="<%=Constant.server_name%>lsjfgl/tjcx/xsddcx/ddxq.jsp" data-id="10"
                               data-title="学生订单查询"
                               data-type="tabAdd">学生订单查询</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>lsjfgl/tjcx/dzqk/dzqk.jsp" data-id="11"
                               data-title="对账情况"
                               data-type="tabAdd">对账情况</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>lsjfgl/tjcx/yjfxx/yjfxx.jsp" data-id="12"
                               data-title="应交费信息"
                               data-type="tabAdd">应交费信息</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>lsjfgl/tjcx/ijfxx/ijfxx.jsp" data-title="已交费信息"
                               data-id="13"
                               data-type="tabAdd">已交费信息</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>lsjfgl/tjcx/wjfxx/wjfxx.jsp" data-title="未交费信息"
                               data-id="14"
                               data-type="tabAdd">未交费信息</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>xsjfgl/wyjf.jsp" data-title="票据信息" data-id="15"
                               data-type="tabAdd">票据信息</a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a class="" href="javascript:;">
                        <i class="layui-icon layui-icon-component"></i>
                        <cite>综合系统管理</cite></a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a data-url="" data-id="ly_home" data-title="table-demo" data-type="tabAdd">辅助信息维护</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>xsjfgl/grjfxx.jsp" data-id="20" data-title="开票基础数据管理"
                               data-type="tabAdd">开票基础数据管理</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>xsjfgl/wyjf.jsp" data-id="21" data-title="人员与角色权限"
                               data-type="tabAdd">人员与角色权限</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>xsjfgl/ddxq.jsp" data-title="通知公告管理" data-id="22"
                               data-type="tabAdd">通知公告管理</a>
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
            <div class="layui-fluid">
                <div class="layui-row layui-col-space15">
                    <div class="layui-col-md6">
                        <div class="layui-card">
                            <div class="layui-card-header">标准地图</div>
                            <div class="layui-card-body">
                                <div id="china" style="height: 400px;width: 100%"></div>
                            </div>
                        </div>
                        <div class="layui-card">
                            <div class="layui-card-header">标准柱状图</div>
                            <div class="layui-card-body">
                                <div id="zzt" style="height: 400px;width: 100%"></div>

                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <div class="layui-card">
                            <div class="layui-card-header">标准饼图</div>
                            <div class="layui-card-body">
                                <div id="bt" style="height: 400px;width: 100%"></div>
                            </div>
                        </div>
                        <div class="layui-card">
                            <div class="layui-card-header">标准折线图</div>
                            <div class="layui-card-body">
                                <div id="zxt" style="height: 400px;width: 100%"></div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/echarts4.40/demo.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/index.js"></script>
</body>
<script type="application/javascript">
    $(function () {
        console.log(document.body.clientWidth)
        $("#loginOut").on("click", function () {
            var loadIndex;
            $.ajax({
                url: wpt_serverName + "LSlogin/loginOut",
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
                            window.location.href = wpt_serverName + "login/lslogin.jsp";
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
<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
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
        .zoomImage {
            object-fit: cover;
            max-width: 180px !important;
        }

        #ly_tab tbody tr:hover {
            background-color: #fbfbfb;
        }

        #iframe_ly_home {
            overflow: scroll !important;
        }

        th {
            background-color: #eef9fb !important;
            color: #4aa4a5 !important;
            font-weight: bold !important;
        }
    </style>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
    <title>学生缴费管理</title>
</head>
<body class="layui-layout-body">
<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
    String userName = "";
    if (userInfo != null) {
        userName = userInfo.getXm();
        System.out.println("userName" + userName);
    } else {
%>
<script>
    window.location.href = wpt_serverName + "login/xslogin.jsp";
</script>
<%
    }
%>
<div class="layui-layout layui-layout-admin">
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <div class="layui-header">
        <%--学生交费管理--%>
        <div class="layui-logo">
            <img src="/sfma/img/titlename.png" style="  width: 170px;margin-left: 18px;">
            <img src="/sfma/img/white2.png" style="  width: 460px;margin-left: 33px;">
        </div>
        <%--<ul class="layui-nav layui-layout-left">--%>
        <%--<li class="layui-nav-item"><a lay-href="">控制台</a></li>--%>
        <%--<li class="layui-nav-item"><a lay-href="">商品管理</a></li>--%>
        <%--<li class="layui-nav-item"><a lay-href="">用户</a></li>--%>
        <%--<li class="layui-nav-item">--%>
        <%--<a href="javascript:;">其它系统</a>--%>
        <%--<dl class="layui-nav-child">--%>
        <%--<dd><a href="#"><span class="nav-ly-child">邮件管理</span></a></dd>--%>
        <%--<dd><a href="#"><span class="nav-ly-child">消息管理</span></a></dd>--%>
        <%--<dd><a href="#"><span class="nav-ly-child">授权管理</span></a></dd>--%>
        <%--</dl>--%>
        <%--</li>--%>
        <%--</ul>--%>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <%=userName%>
                </a>
                <%--<dl class="layui-nav-child">--%>
                <%--<dd><a href=""><span class="nav-ly-child">基本资料</span></a></dd>--%>
                <%--<dd><a href=""><span class="nav-ly-child">安全设置</span></a></dd>--%>
                <%--</dl>--%>
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
                        <%--<dd class="layui-this">--%>
                        <%--<a data-url="" data-id="ly_home" data-title="table-demo"--%>
                        <%--data-type="tabAdd">通知公告</a>--%>
                        <%--</dd>--%>
                        <dd>
                            <a data-url="<%=Constant.server_name%>xsjfgl/grjfxx.jsp" data-id="00" data-title="个人交费信息"
                               data-type="tabAdd">个人交费信息</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>xsjfgl/wyjf.jsp" data-id="01" data-title="我要交费"
                               data-type="tabAdd">我要交费</a>
                        </dd>
                        <dd>
                            <a data-url="<%=Constant.server_name%>xsjfgl/ddxq.jsp" data-title="订单查询" data-id="02"
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
            <div class="layui-fluid">
                <div>
                    <fieldset class="layui-elem-field layui-field-title"
                              style="margin-top: 20px;border-color: #009688 !important;">
                        <legend>个人信息</legend>
                    </fieldset>
                </div>

            </div>
            <div class="layui-fluid">
                <div class="layui-row">
                    <div class="layui-card">
                        <div class="layui-card-body">
                            <div class="layui-form">
                                <table class="layui-table" id="ly_tab">
                                    <colgroup>
                                        <col width="15%">
                                        <col width="25%">
                                        <col width="15%">
                                        <col width="25%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th colspan="5" style="background-color: #d3d8de">
                                            <div align="center">
                                                学生个人信息
                                            </div>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody align="center">
                                    <tr>
                                        <td style="background-color: #eef9fb">姓名</td>
                                        <td><%=userInfo.getXm()%>
                                        </td>
                                        <td style="background-color: #eef9fb">学号/考生号</td>
                                        <td><%=userInfo.getZh()%>
                                        </td>
                                        <td rowspan="4" style="width: 500px;">
                                            <img src="<%=Constant.server_name%>imgShow.jsp" class="zoomImage"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="background-color: #eef9fb">年级</td>
                                        <td><%=userInfo.getNjmc()%>
                                        </td>
                                        <td style="background-color: #eef9fb">身份证号</td>
                                        <td><%=userInfo.getZjhm()%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="background-color: #eef9fb">学院名称</td>
                                        <td><%=userInfo.getJgmc()%>
                                        </td>
                                        <td style="background-color: #eef9fb">专业名称</td>
                                        <td><%=userInfo.getZymc()%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="background-color: #eef9fb">班级名称</td>
                                        <td><%=userInfo.getBjmc()%>
                                        </td>
                                        <td style="background-color: #eef9fb"></td>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%--<div class="layui-fluid">--%>
            <%--<div class="layui-row layui-col-space15">--%>
            <%--<div class="layui-col-md6">--%>
            <%--<div class="layui-card">--%>
            <%--<div class="layui-card-header">标准地图</div>--%>
            <%--<div class="layui-card-body">--%>
            <%--<div id="china" style="height: 400px;width: 100%"></div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-card">--%>
            <%--<div class="layui-card-header">标准柱状图</div>--%>
            <%--<div class="layui-card-body">--%>
            <%--<div id="zzt" style="height: 400px;width: 100%"></div>--%>

            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-col-md6">--%>
            <%--<div class="layui-card">--%>
            <%--<div class="layui-card-header">标准饼图</div>--%>
            <%--<div class="layui-card-body">--%>
            <%--<div id="bt" style="height: 400px;width: 100%"></div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-card">--%>
            <%--<div class="layui-card-header">标准折线图</div>--%>
            <%--<div class="layui-card-body">--%>
            <%--<div id="zxt" style="height: 400px;width: 100%"></div>--%>

            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
<%--<script type="text/javascript" src="<%=Constant.server_name%>js-lib/echarts4.40/demo.js"></script>--%>
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
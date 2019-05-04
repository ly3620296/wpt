<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.controller.login.WptUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>/css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>/css/swiper.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
    <script src="<%=Constant.server_name%>js-lib/swiper/swiper-3.4.2.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript" charset="utf-8"></script>
    <title>服务中心</title>
</head>
<body>
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
    String role = wptUser.getJsdm();
    if (role == null || "".equals(role)) {
        role = "01";
    }
%>
<div class="app">
    <div class="tcDiv">
        <div class="tclist" id="tc_list">
            <%--<div class="tc">--%>
            <%--<div class="name">--%>
            <%--<p>教务类</p>--%>
            <%--</div>--%>
            <%--<ul>--%>
            <%--<%--%>
            <%--if (role.equals("02")) {--%>
            <%--%>--%>
            <%--<li lay-href="module/fwzx/jwl/xskb/xskb.jsp">--%>
            <%--<img src="<%=Constant.server_name%>img/icon_chakebiao.png"/>--%>

            <%--<p>查课表</p>--%>
            <%--</li>--%>
            <%--<%--%>
            <%--} else if (role.equals("01")) {--%>
            <%--%>--%>
            <%--<li lay-href="module/fwzx/jwl/jskb/jskb.jsp">--%>
            <%--<img src="<%=Constant.server_name%>img/icon_chakebiao.png"/>--%>

            <%--<p>查课表</p>--%>
            <%--</li>--%>
            <%--<%--%>
            <%--}--%>
            <%--%>--%>
            <%--<%--%>
            <%--if (role.equals("02")) {--%>
            <%--%>--%>
            <%--<li lay-href="module/fwzx/jwl/ccj/ccj.jsp">--%>
            <%--<img src="<%=Constant.server_name%>img/icon_chachengji.png"/>--%>

            <%--<p>查成绩</p>--%>
            <%--</li>--%>
            <%--<%--%>
            <%--}--%>
            <%--%>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_kjscx.png"/>--%>

            <%--<p>空教室查询</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_chakaoshi.png"/>--%>

            <%--<p>查考试</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_cet.png"/>--%>

            <%--<p>四六级查询</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_xiaoli.png"/>--%>

            <%--<p>校历</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_kthd.png"/>--%>

            <%--<p>教学计划查询</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktp.png"/>--%>

            <%--<p>选课情况查询</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktqd.png"/>--%>

            <%--<p>教室课表查询</p>--%>
            <%--</li>--%>

            <%--</ul>--%>
            <%--</div>--%>
            <%--<div class="tc">--%>
            <%--<div class="name">--%>
            <%--<p>学生服务类模块</p>--%>
            <%--</div>--%>
            <%--<ul>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_shiwu.png"/>--%>

            <%--<p>学生公寓查询</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_xgb.png"/>--%>

            <%--<p>学生活动查询</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktqd.png"/>--%>

            <%--<p>部门公共通讯录查询</p>--%>
            <%--</li>--%>
            <%--</ul>--%>
            <%--</div>--%>
            <%--<div class="tc">--%>
            <%--<div class="name">--%>
            <%--<p>学工</p>--%>
            <%--</div>--%>
            <%--<ul>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktqd.png"/>--%>

            <%--<p>综合评分查询</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktqd.png"/>--%>

            <%--<p>奖评结果查询</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktqd.png"/>--%>

            <%--<p>寝室查询</p>--%>
            <%--</li>--%>
            <%--</ul>--%>
            <%--</div>--%>
            <%--<div class="tc">--%>
            <%--<div class="name">--%>
            <%--<p>图书服务类模块</p>--%>
            <%--</div>--%>
            <%--<ul>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_wqtsg.png"/>--%>

            <%--<p>图书馆违章</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktqd.png"/>--%>

            <%--<p>借阅时间</p>--%>
            <%--</li>--%>
            <%--</ul>--%>
            <%--</div>--%>
            <%--<div class="tc">--%>
            <%--<div class="name">--%>
            <%--<p>财务</p>--%>
            <%--</div>--%>
            <%--<ul>--%>

            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_tiyanka.png"/>--%>

            <%--<p>收费表</p>--%>
            <%--</li>--%>

            <%--</ul>--%>
            <%--</div>--%>
            <%--<div class="tc">--%>
            <%--<div class="name">--%>
            <%--<p>事物办理类</p>--%>
            <%--</div>--%>
            <%--<ul>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_baoxiu.png"/>--%>

            <%--<p>网上报修</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktqd.png"/>--%>

            <%--<p>请假办理</p>--%>
            <%--</li>--%>

            <%--</ul>--%>
            <%--</div>--%>
            <%--<div class="tc">--%>
            <%--<div class="name">--%>
            <%--<p>网上统一支付</p>--%>
            <%--</div>--%>
            <%--<ul>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktqd.png"/>--%>

            <%--<p>学杂费</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktqd.png"/>--%>

            <%--<p>通用类</p>--%>
            <%--</li>--%>
            <%--</ul>--%>
            <%--</div>--%>

            <%--<div class="tc">--%>
            <%--<div class="name">--%>
            <%--<p>人事信息服务</p>--%>
            <%--</div>--%>
            <%--<ul>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_weitoupiao.png"/>--%>

            <%--<p>工资查询</p>--%>
            <%--</li>--%>

            <%--</ul>--%>
            <%--</div>--%>
            <%--<div class="tc">--%>
            <%--<div class="name">--%>
            <%--<p>一卡通类查询</p>--%>
            <%--</div>--%>
            <%--<ul>--%>

            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_ktqd.png"/>--%>

            <%--<p>消费记录</p>--%>
            <%--</li>--%>

            <%--</ul>--%>
            <%--</div>--%>
            <%--<div class="tc">--%>
            <%--<div class="name">--%>
            <%--<p>资产管理查询</p>--%>
            <%--</div>--%>
            <%--<ul>--%>
            <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon_jiaofei.png"/>--%>

            <%--<p>个人资产</p>--%>
            <%--</li>--%>

            <%--</ul>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
<jsp:include page="/common/foot.jsp"></jsp:include>
</body>
</html>
<script>
    $(function () {
        var loadIndex = "";
        $.ajax({
            url: wpt_serverName + "fwzx",
            type: 'post',
            dataType: 'json',
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data) {
                    var code = data.returnInfo.return_code;
                    var msg = data.returnInfo.return_msg;
                    if (code == "0000") {
                        var menuParents = data.menuParents;
                        var menuHtml = "";
                        for (var parIndex in menuParents) {
                            var menuParent = menuParents[parIndex];
                            var htmlPar = '<div class="tc">' +
                                    '<div class="name">' +
                                    '<p>' + menuParent.menu_name + '</p>' +
                                    '</div>';
                            var menuChilds = menuParent.menuChildList;
                            var htmlChild = '<ul>';
                            for (var childIndex in menuChilds) {
                                var menuChild = menuChilds[childIndex];

                                var menu_url = menuChild.menu_url;

                                var menu_img = menuChild.menu_img;
                                if (menu_img) {
                                    menu_img = wpt_serverName + menu_img;
                                }
                                htmlChild += '<li lay-href="' + menu_url + '">' +
                                '<img src="' + menu_img + '">' +
                                '<p>' + menuChild.menu_name + '</p>' +
                                '</li>';
                            }
                            var htmlChildEnd = '</ul>';
                            var htmlParEnd = "</div>";
                            menuHtml = menuHtml + htmlPar + htmlChild + htmlChildEnd + htmlParEnd;
                        }
                        $("#tc_list").html(menuHtml);
                        bindLayHref();
                    } else {
                        layer.msg(msg, {anim: 6, time: 2000});
                    }
                }
            },
            complete: function () {
                layer.close(loadIndex);
            }
        })
        function bindLayHref() {
            $("ul>li").on("click", function () {
                var my_li = $(this);
                var lay_href = my_li.attr("lay-href");
                if (lay_href) {
                    window.location.href = wpt_serverName + lay_href;
                }
            })
        }
    })

</script>





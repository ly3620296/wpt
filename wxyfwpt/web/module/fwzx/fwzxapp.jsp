<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>/css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>/css/swiper.css"/>
    <title></title>
</head>
<body>
<div class="app">


    <div class="tcDiv">

        <div class="tclist">
            <div class="tc">
                <div class="name">
                    <p>教务类</p>
                </div>
                <ul id="wpt_main">
                    <li lay-href="module/education/course/courseDemo.jsp">
                        <img src="../../img/icon_chakebiao.png"/>

                        <p>查课表</p>
                    </li>
                    <li>
                        <img src="../../img/icon_chachengji.png"/>

                        <p>查成绩</p>
                    </li>
                    <li>
                        <img src="../../img/icon_kjscx.png"/>

                        <p>空教室查询</p>
                    </li>
                    <li>
                        <img src="../../img/icon_chakaoshi.png"/>

                        <p>查考试</p>
                    </li>
                    <li>
                        <img src="../../img/icon_cet.png"/>

                        <p>四六级查询</p>
                    </li>
                    <li>
                        <img src="../../img/icon_xiaoli.png"/>

                        <p>校历</p>
                    </li>
                    <li>
                        <img src="../../img/icon_kthd.png"/>

                        <p>教学计划查询</p>
                    </li>
                    <li>
                        <img src="../../img/icon_ktp.png"/>

                        <p>选课情况查询</p>
                    </li>
                    <li>
                        <img src="../../img/icon_ktqd.png"/>

                        <p>教室课表查询</p>
                    </li>

                </ul>
            </div>


            <div class="tc">
                <div class="name">
                    <p>学生服务类模块</p>
                </div>
                <ul id="wpt_main">

                    <li>
                        <img src="../../img/icon_shiwu.png"/>

                        <p>学生公寓查询</p>
                    </li>
                    <li>
                        <img src="../../img/icon_xgb.png"/>

                        <p>学生活动查询</p>
                    </li>
                    <li>
                        <img src="../../img/icon_ktqd.png"/>

                        <p>部门公共通讯录查询</p>
                    </li>

                </ul>
            </div>
            <div class="tc">
                <div class="name">
                    <p>学工</p>
                </div>
                <ul id="wpt_main">

                    <li>
                        <img src="../../img/icon_ktqd.png"/>

                        <p>综合评分查询</p>
                    </li>
                    <li>
                        <img src="../../img/icon_ktqd.png"/>

                        <p>奖评结果查询</p>
                    </li>
                    <li>
                        <img src="../../img/icon_ktqd.png"/>

                        <p>寝室查询</p>
                    </li>
                </ul>
            </div>
            <div class="tc">
                <div class="name">
                    <p>图书服务类模块</p>
                </div>
                <ul id="wpt_main">

                    <li>
                        <img src="../../img/icon_wqtsg.png"/>

                        <p>图书馆违章</p>
                    </li>
                    <li>
                        <img src="../../img/icon_ktqd.png"/>

                        <p>借阅时间</p>
                    </li>


                </ul>
            </div>
            <div class="tc">
                <div class="name">
                    <p>财务</p>
                </div>
                <ul id="wpt_main">

                    <li>
                        <img src="../../img/icon_tiyanka.png"/>

                        <p>收费表</p>
                    </li>

                </ul>
            </div>
            <div class="tc">
                <div class="name">
                    <p>事物办理类</p>
                </div>
                <ul id="wpt_main">

                    <li>
                        <img src="../../img/icon_baoxiu.png"/>

                        <p>网上报修</p>
                    </li>
                    <li>
                        <img src="../../img/icon_ktqd.png"/>

                        <p>请假办理</p>
                    </li>

                </ul>
            </div>
            <div class="tc">
                <div class="name">
                    <p>网上统一支付</p>
                </div>
                <ul id="wpt_main">

                    <li>
                        <img src="../../img/icon_ktqd.png"/>

                        <p>学杂费</p>
                    </li>
                    <li>
                        <img src="../../img/icon_ktqd.png"/>

                        <p>通用类</p>
                    </li>

                </ul>
            </div>

            <div class="tc">
                <div class="name">
                    <p>人事信息服务</p>
                </div>
                <ul id="wpt_main">

                    <li>
                        <img src="../../img/icon_weitoupiao.png"/>

                        <p>工资查询</p>
                    </li>

                </ul>
            </div>
            <div class="tc">
                <div class="name">
                    <p>一卡通类查询</p>
                </div>
                <ul id="wpt_main">

                    <li>
                        <img src="../../img/icon_ktqd.png"/>

                        <p>消费记录</p>
                    </li>

                </ul>
            </div>
            <div class="tc">
                <div class="name">
                    <p>资产管理查询</p>
                </div>
                <ul id="wpt_main">

                    <li>
                        <img src="../../img/icon_jiaofei.png"/>

                        <p>个人资产</p>
                    </li>

                </ul>
            </div>


        </div>
    </div>
</div>


<div class="footer-nav" style="display: block;">
    <ul class="footer-menu">
        <li>
            <a href="../main/app.html">
                <img src="../../img/icon-bottom1.png"/>
            </a>

            <p>首页</p>
        </li>
        <li>
            <a href="fwzxapp.html">
                <img src="../../img/icon-bottom2.png"/>
            </a>

            <p>服务中心</p>
        </li>
        <li>
            <img src="../../img/icon-bottom3.png"/>

            <p>通讯录</p>
        </li>
        <li>
            <img src="../../img/icon-bottom4.png"/>

            <p>我的</p>
        </li>
    </ul>
</div>
<script src="js/jquery.min.js" type="text/javascript" charset="utf-8"></script>

<script src="js/swper.js" type="text/javascript" charset="utf-8"></script>
<script>
    var mySwiper = new Swiper('.swiper-container', {
        direction: 'horizontal',
        loop: true,
        // 如果需要分页器
        pagination: '.swiper-pagination',
        autoplay: 3000
    })
</script>
</body>
</html>




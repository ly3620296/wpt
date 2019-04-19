<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/swiper.css"/>

    <title></title>
</head>
<body>
<div class="app">
    <div class="swiper-container swiper-container-horizontal">
        <div class="swiper-wrapper">
            <div class="swiper-slide">
                <a><img src="<%=Constant.server_name%>img/barent2.jpg"/></a>
            </div>

            <div class="swiper-slide">
                <a><img src="<%=Constant.server_name%>img/banner.jpg"/></a>
            </div>

        </div>
        <div class="swiper-pagination"></div>
    </div>


    <div class="peopleMessage">

        <img class="xing" src="<%=Constant.server_name%>img/icon_houqin.png"/>

        <div class="peopleText">
            <p class="p1">姓名：李小狗</p>

            <p class="p1">学号：13944402683</p>
        </div>

    </div>


    <div class="tcDiv">

        <div class="tclist">
            <div class="tc">
                <div class="name">
                    <p>我的常用</p>
                </div>
                <ul id="wpt_main">
                    <li lay-href="module/education/course/courseDemo.jsp">
                        <img src="<%=Constant.server_name%>img/icon_chakebiao.png"/>

                        <p>查课表</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_chachengji.png"/>

                        <p>查成绩</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_xysq.png"/>

                        <p>校园社区</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_chakaoshi.png"/>

                        <p>查考试</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_cet.png"/>

                        <p>四六级查询</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_xiaoli.png"/>

                        <p>校历</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_kthd.png"/>

                        <p>课堂互动</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_ktp.png"/>

                        <p>课堂派</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_ktqd.png"/>

                        <p>课堂签到</p>
                    </li>

                </ul>
            </div>


        </div>
    </div>

</div>


<div class="footer-nav" style="display: block;">
    <ul class="footer-menu">
        <li>
            <a href="app.html">
                <img src="img/icon-bottom1.png"/>
            </a>

            <p>首页</p>
        </li>
        <li>
            <a href="fwzxapp.html">
                <img src="img/icon-bottom2.png"/>
            </a>

            <p>服务中心</p>
        </li>
        <li>
            <img src="img/icon-bottom3.png"/>

            <p>通讯录</p>
        </li>
        <li>
            <img src="img/icon-bottom4.png"/>

            <p>我的</p>
        </li>
    </ul>
</div>
<script src="<%=Constant.server_name%>js/jquery.min.js" type="text/javascript" charset="utf-8"></script>

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



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
                    <a class="btnbj">完成</a>
                </div>
                <ul>
                    <li lay-href="module/education/course/courseDemo.jsp">
                        <img src="<%=Constant.server_name%>img/icon_chakebiao.png"/>
                        <!--
                           加号
                       -->
                        <i class="fa fa-minus-square"></i>

                        <p>查课表</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_chachengji.png"/>

                        <!--
                        	减号
                        -->
                        <i class="fa fa-minus-square"></i>

                        <p>查成绩</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_xysq.png"/>
                        <i class="fa fa-minus-square"></i>

                        <p>校园社区</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_chakaoshi.png"/>
                        <i class="fa fa-minus-square"></i>

                        <p>查考试</p>
                    </li>
                    <li>
                        <img src="<%=Constant.server_name%>img/icon_cet.png"/>
                        <i class="fa fa-minus-square"></i>

                        <p>四六级查询</p>
                    </li>


                </ul>
            </div>


        </div>
    </div>

    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
        <!--<div class="layui-tab layui-tab-card" >-->
        <ul class="layui-tab-title" style="width: 100%;overflow-x: scroll;">
            <li class="layui-this">教务类</li>
            <li>学生服务类模块</li>
            <li>学工</li>
            <li>图书服务类模块</li>
            <li>财务</li>
            <li>事物办理类</li>
            <li>网上统一支付</li>
            <li>人事信息服务</li>
            <li>一卡通类查询</li>
            <li>资产管理查询</li>
        </ul>
        <div class="layui-tab-content " style="height: 100px;">
            <div class="layui-tab-item layui-show">
                <div class="tc">
                    <ul>
                        <li lay-href="module/education/course/courseDemo.jsp">
                            <img src="<%=Constant.server_name%>img/icon_chakebiao.png"/>
                            <i class="fa fa-plus-square"></i>

                            <p>查课表</p>
                        </li>
                        <li>
                            <img src="<%=Constant.server_name%>img/icon_chachengji.png"/>
                            <i class="fa fa-plus-square"></i>

                            <p>查成绩</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_kjscx.png"/>

                            <p>空教室查询</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_chakaoshi.png"/>

                            <p>查考试</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_cet.png"/>

                            <p>四六级查询</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_xiaoli.png"/>

                            <p>校历</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_kthd.png"/>

                            <p>教学计划查询</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_ktp.png"/>

                            <p>选课情况查询</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_ktqd.png"/>

                            <p>教室课表查询</p>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="tc">

                    <ul>

                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_zhpf.png"/>

                            <p>综合评分查询</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_jpjg.png"/>

                            <p>奖评结果查询</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon-qscx.png"/>

                            <p>寝室查询</p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="tc">

                    <ul>

                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_wqtsg.png"/>

                            <p>图书馆违章</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon-jysj.png"/>

                            <p>借阅时间</p>
                        </li>


                    </ul>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="tc">

                    <ul>

                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_tiyanka.png"/>

                            <p>收费表</p>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="tc">

                    <ul>

                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_baoxiu.png"/>

                            <p>网上报修</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon-qjbl.png"/>

                            <p>请假办理</p>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="tc">

                    <ul>

                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon-xf.png"/>

                            <p>学杂费</p>
                        </li>
                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon-tyl.png"/>

                            <p>通用类</p>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="tc">

                    <ul>

                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_weitoupiao.png"/>

                            <p>工资查询</p>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="tc">

                    <ul>

                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon-xfju.png"/>

                            <p>消费记录</p>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="tc">

                    <ul>

                        <li>
                            <i class="fa fa-plus-square"></i>
                            <img src="<%=Constant.server_name%>img/icon_jiaofei.png"/>

                            <p>个人资产</p>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use('element', function () {
        var $ = layui.jquery
                , element = layui.element;
        var active = {
            tabChange: function () {
                element.tabChange('demo', '22');
            }
        };
    });
</script>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>layout 后台大布局 - Layui</title>
    <link rel="stylesheet" href="js-lib/layui-2.4.5/css/layui.css">
    <link rel="stylesheet" href="js-lib/layui-2.4.5/css/admin.css">
</head>

<!--<span class="layui-unselect layui-tab-bar" lay-stope="tabmore" title="" style="-->
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

    <!-- 头部区域（可配合layui已有的水平导航） -->
    <div class="layui-header">
        <div class="layui-logo">layui 后台布局</div>
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
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    贤心
                </a>
                <dl class="layui-nav-child">
                    <dd><a href=""><span class="nav-ly-child">基本资料</span></a></dd>
                    <dd><a href=""><span class="nav-ly-child">安全设置</span></a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">退了</a></li>
        </ul>
    </div>

    <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
    <div class="layui-side layui-bg-black" id="layui-left-side">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" lay-shrink="all" lay-filter="left-side">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">
                        <i class="layui-icon layui-icon-home"></i>
                        <cite>主页</cite></a>
                    <dl class="layui-nav-child">
                        <dd class="layui-this">
                            <a data-url="ly_home" data-id="ly_home" data-title="table-demo"
                               data-type="tabAdd">控制台</a>
                        </dd>
                        <dd>
                            <a data-url="demo-table/table.html" data-id="00" data-title="动态表格"
                               data-type="tabAdd">动态表格</a>
                        </dd>
                        <dd>
                            <a data-url="demo-switch/switch.html" data-title="轮播" data-id="01"
                               data-type="tabAdd">轮播</a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <i class="layui-icon layui-icon-component"></i>
                        解决方案</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" data-id="no">列表三级</a>
                            <dl class="layui-nav-child">
                                <dd><a href="javascript:;" data-url="test/test2.html" data-title="主页2"
                                       data-id="02">列表2</a></dd>
                                <dd><a href="javascript:;" data-url="test/test3.html" data-title="主页3"
                                       data-id="03">列表3</a></dd>
                            </dl>
                        </dd>

                        <dd><a href="javascript:;" data-url="test/test4.html" data-title="主页4" data-id="04">列表4</a></dd>
                        <dd><a href="javascript:;" data-url="test/test5.html" data-title="主页5" data-id="05">列表5</a></dd>
                        <dd><a href="javascript:;" data-url="test/test6.html" data-title="主页6" data-id="06">列表6</a></dd>
                        <dd><a href="javascript:;" data-url="test/test7.html" data-title="主页7" data-id="07">列表7</a></dd>
                        <dd><a href="javascript:;" data-url="test/test8.html" data-title="主页8" data-id="08">列表8</a></dd>
                        <dd><a href="javascript:;" data-url="test/test9.html" data-title="主页9" data-id="09">列表9</a></dd>
                        <dd><a href="javascript:;" data-url="test/test10.html" data-title="主页10" data-id="10">列表10</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test11.html" data-title="主页11" data-id="11">列表11</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test12.html" data-title="主页12" data-id="12">列表12</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test13.html" data-title="主页13" data-id="13">列表13</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test14.html" data-title="主页14" data-id="14">列表14</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页15" data-id="15">列表15</a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <i class="layui-icon layui-icon-component"></i>
                        <cite>云市场</cite>
                    </a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" data-url="demo-table/table.html" data-id="16" data-title="表格表格表格"
                               data-type="tabAdd">表格表格表格</a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="http://www.jl.10086.cn/service" data-title="主页二"
                               data-id="17"
                               data-type="tabAdd">主页二</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页27" data-id="27">列表27</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页28" data-id="28">列表28</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页29" data-id="29">列表29</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页30" data-id="30">列表30</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页31" data-id="31">列表31</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页32" data-id="32">列表32</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页33" data-id="33">列表33</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页34" data-id="34">列表34</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页35" data-id="35">列表35</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页36" data-id="36">列表36</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页37" data-id="37">列表37</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页38" data-id="38">列表38</a>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <i class="layui-icon layui-icon-component"></i>
                        <cite>发布商品</cite>
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" data-url="test/test12.html" data-title="主页16" data-id="16">列表16</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test13.html" data-title="主页17" data-id="17">列表17</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test14.html" data-title="主页18" data-id="18">列表18</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页19" data-id="19">列表19</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test12.html" data-title="主页20" data-id="20">列表20</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test13.html" data-title="主页21" data-id="21">列表21</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test14.html" data-title="主页22" data-id="22">列表22</a>
                        </dd>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页23" data-id="23">列表23</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页24" data-id="24">列表24</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页25" data-id="25">列表25</a>
                        <dd><a href="javascript:;" data-url="test/test15.html" data-title="主页26" data-id="26">列表26</a>
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
            <iframe data-frameid="ly_home" scrolling="no" frameborder="0" src="ly_home.html?ran=2" frameborder="0"
                    class="layadmin-iframe" style="width: 100%; height: 363px;"></iframe>
        </div>
    </div>
    <!-- 底部固定区域 -->
    <!--<div class="layui-footer">-->
    <!--© layui.com - 底部固定区域-->
    <!--</div>-->
</div>
<script type="text/javascript" src="js-lib/layui-2.4.5/layui.js"></script>
<script type="text/javascript" src="js-lib/layui-2.4.5/index.js"></script>
</body>
</html>
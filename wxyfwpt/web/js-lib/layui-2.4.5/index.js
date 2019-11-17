layui.use(['element', 'carousel'], function () {
    var carousel = layui.carousel;
    var $ = layui.jquery;
    var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
    //隐藏home默认关闭图标
    $(".layui-tab-title li:first-child .layui-tab-close").hide();

    //触发事件
    var active = {
        tabAdd: function (url, id, title) {
            element.tabAdd('layadmin-layout-tabs', {
                title: '<span>' + title + '</span>'
                , id: id
                , attr: url
            });
        },
        tabChange: function (id) {
            element.tabChange('layadmin-layout-tabs', id); //根据传入的id 切换到指定Tab项
        },
        tabDelete: function (id) {
            element.tabDelete("layadmin-layout-tabs", id); //根据传入的id 关闭Tab项
            active.iframeDelete(id);
        }
        , tabDeleteAll: function (ids) { //关闭所有
            $.each(ids, function (i, item) {
                if (item) {
                    element.tabDelete("layadmin-layout-tabs", item);
                    active.iframeDelete(item)
                }
            })
        }
        , iframeAdd: function (id, url) {
            //添加iframe
            $("#LAY_app_body").append([
                '<div class="layadmin-tabsbody-item" id="iframe_' + id + '">'
                , '<iframe data-frameid="' + id + '" src="' + url + '" frameborder="0" class="layadmin-iframe"></iframe>'
                , '</div>'
            ].join(''));
        }
        , iframeDelete: function (id) {
            $("#iframe_" + id).remove()
        }

        , rollPage: function (type, index) {
            var tabsHeader = $('#LAY_app_tabsheader')
                , liItem = tabsHeader.children('li')
                , scrollWidth = tabsHeader.prop('scrollWidth')
                , outerWidth = tabsHeader.outerWidth()
                , tabsLeft = parseFloat(tabsHeader.css('left'));
            //右左往右
            if (type === 'left') {
                if (!tabsLeft && tabsLeft <= 0) return;

                //当前的left减去可视宽度，用于与上一轮的页标比较
                var prefLeft = -tabsLeft - outerWidth;

                liItem.each(function (index, item) {
                    var li = $(item)
                        , left = li.position().left;

                    if (left >= prefLeft) {
                        tabsHeader.css('left', -left);
                        return false;
                    }
                });
            } else if (type === 'auto') { //自动滚动
                (function () {
                    var thisLi = liItem.eq(index), thisLeft;
                    if (!thisLi[0]) return;

                    thisLeft = thisLi.position().left;
                    //当目标标签在可视区域左侧时
                    if (thisLeft < -tabsLeft) {
                        return tabsHeader.css('left', -thisLeft);
                    }

                    //当目标标签在可视区域右侧时
                    var subLeft = thisLeft + thisLi.outerWidth() - (outerWidth - tabsLeft);
                    if (subLeft >= 0) {
                        liItem.each(function (i, item) {
                            var li = $(item)
                                , left = li.position().left;
                            //从当前可视区域的最左第二个节点遍历，如果减去最左节点的差 > 目标在右侧不可见的宽度，则将该节点放置可视区域最左
                            if (left + tabsLeft > 1) {
                                if (left - tabsLeft > subLeft) {
                                    tabsHeader.css('left', -left);
                                    return false;
                                }
                            }
                        });
                    }
                }());
            } else {
                //默认向左滚动
                liItem.each(function (i, item) {
                    var li = $(item)
                        , left = li.position().left;

                    if (left + li.outerWidth() >= outerWidth - tabsLeft) {
                        tabsHeader.css('left', -left);
                        return false;
                    }
                });
            }
        }
        , leftPage: function () {   //向右滚动页面标签
            active.rollPage('left');
        }
        , rightPage: function () {  //向左滚动页面标签
            active.rollPage();
        }
    };

    //监听tab点击时切换iframe
    element.on('tab(layadmin-layout-tabs)', function (data) {
        var currId = $(this).attr("lay-id");
        //$("a[data-id='" + currId + "']").trigger("click");

        //console.log(currId)
        $("#iframe_" + currId).addClass('layui-show').siblings().removeClass('layui-show');
    });


    //监听tab关闭时销毁iframe
    element.on('tabDelete(layadmin-layout-tabs)', function (data) {
        var currId = $(this).parent().attr("lay-id");
        active.iframeDelete(currId)
    });

    //选项卡左移按钮
    $(".layui-icon-prev").on('click', function () {
        active.leftPage();
    })

    //选项卡右移按钮
    $(".layui-icon-next").on('click', function () {
        active.rightPage();
    })

    //绑定左导航点击弹出tab
    $('.layui-bg-black dl dd a[data-id!="no"]').on('click', function () {
        var tabs = $('#LAY_app_tabsheader>li')
        //tab当前元素
        var curr_tab_ele = $(this);
        //tab开关标志
        var isShow = false;
        //url
        var curr_url = curr_tab_ele.attr("data-url") + "?ran=" + Math.random();
        //标识
        var curr_id = curr_tab_ele.attr("data-id");
        //标题
        var curr_title = curr_tab_ele.attr("data-title");
        //索引
        var cur_index;
        //遍历tab 判断左导航点击菜单是否为新tab
        $.each(tabs, function (index) {
            if ($(this).attr("lay-id") == curr_id) {
                isShow = true;
                cur_index = index;
                return false;
            }
        })
        //新增tab项
        if (!isShow) {
            //添加iframe
            active.iframeAdd(curr_id, curr_url);
            //获取索引
            cur_index = $('#LAY_app_tabsheader>li').length;
            //添加选项卡
            active.tabAdd(curr_url, curr_id, curr_title)
        }
        //不管是否新增tab，最后都转到要打开的选项页面上 并切换ifram-show
        active.tabChange(curr_tab_ele.attr("data-id"));

        //想象卡滑动
        active.rollPage('auto', cur_index);
    });


    //关闭当前标签页
    $("#closeThisTab").on("click", function () {
        var currTabId = $(".layui-tab-title li[class='layui-this']").attr("lay-id");
        if (currTabId && currTabId != "ly_home") {
            active.tabDelete(currTabId);
        }
        $(this).parent().removeClass("layui-show");
    })

    //关闭其他标签页
    $("#closeOtherTabs").on("click", function () {
        var currTabId = $(".layui-tab-title li[class='layui-this']").attr("lay-id");
        var tabs = $(".layui-tab-title li[lay-id]");
        var tabIds = new Array();
        $.each(tabs, function (i) {
            var tabId = $(this).attr("lay-id");
            if (tabId != "ly_home" && tabId != currTabId) {
                tabIds[i] = tabId;
            }

        })
        active.tabDeleteAll(tabIds);
        $('.layui-icon-prev').trigger('click');
        $(this).parent().removeClass("layui-show");
    })

    //关闭所有标签页
    $("#closeAllTabs").on("click", function () {
        var tabs = $(".layui-tab-title li[lay-id]");
        var tabIds = new Array();
        $.each(tabs, function (i) {
            var tabId = $(this).attr("lay-id");
            if (tabId != "ly_home") {
                tabIds[i] = tabId;
            }

        })
        active.tabDeleteAll(tabIds);
        $('.layui-icon-prev').trigger('click');
        $(this).parent().removeClass("layui-show");
    })

    //轮播
    carousel.render({
        elem: '#test1'
        , width: '100%' //设置容器宽度
        , arrow: 'none' //始终显示箭头
        //,anim: 'updown' //切换动画方式
    });
})
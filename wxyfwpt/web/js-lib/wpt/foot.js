$("#wpt_foot>li").on("click", function () {
    var my_li = $(this);
    var lay_href = my_li.attr("lay-href");
    if (lay_href) {
        //window.location.href = wpt_serverName + lay_href;
        window.location.replace(wpt_serverName + lay_href);
    }
})

$(document).ready(function () {
    var url = window.location.href;
    if (url.search("module/fwzx/fwzxapp.jsp") != -1) {
        $(".footer-menu li").eq(1).find("i").addClass("active");
    } else if (url.search("module/msg/msg.jsp") != -1) {
        $(".footer-menu li").eq(2).find("i").addClass("active");
    } else if (url.search("module/my/my.jsp") != -1) {
        $(".fa-user").addClass("active");
    } else {
        $(".footer-menu li").eq(0).find("i").addClass("active");
    }
})
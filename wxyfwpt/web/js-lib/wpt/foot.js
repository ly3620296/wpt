$("#wpt_foot>li").on("click", function () {
    var my_li = $(this);
    var lay_href = my_li.attr("lay-href");
    if (lay_href) {
        window.location.href = wpt_serverName + lay_href;
    }
})
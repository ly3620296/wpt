$(function () {
    $("#wpt_main>li").on("click", function () {
        var my_li = $(this);
        var lay_href = my_li.attr("lay-href");
        if(lay_href){
            window.location.href = wpt_serverName + lay_href;
        }
    })
})


var mySwiper = new Swiper('.swiper-container', {
    direction: 'horizontal',
    loop: true,
    // 如果需要分页器
    pagination: '.swiper-pagination',
    autoplay: 3000
})
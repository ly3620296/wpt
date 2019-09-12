<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title></title>
</head>
<body>

<script src="aa.js"></script>
//引进mshare.js
<button data-mshare="0">点击弹出原生分享面板</button>
<button data-mshare="1">点击触发朋友圈分享</button>
<button data-mshare="2">点击触发发送给微信朋友</button>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script>
    var mshare = new mShare({
        title: 'Lorem ipsum dolor sit.',
        url: 'http://www.kean.com.cn/wpt',
        desc: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quaerat inventore minima voluptates.',
        img: 'http://placehold.it/150x150'
    });
    $('button').click(function () {
        // 1 ==> 朋友圈  2 ==> 朋友  0 ==> 直接弹出原生
        mshare.init(+$(this).data('mshare'));
    });
</script>
</body>
</html>

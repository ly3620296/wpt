<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<div class="footer-nav" style="display: block;">--%>
    <%--<ul class="footer-menu" id="wpt_foot">--%>
        <%--<li lay-href="module/main/main.jsp">--%>
            <%--<img src="<%=Constant.server_name%>img/icon-bottom1.png"/>--%>

            <%--<p>首页</p>--%>
        <%--</li>--%>
        <%--<li lay-href="module/fwzx/fwzxapp.jsp">--%>
            <%--<img src="<%=Constant.server_name%>img/icon-bottom2.png"/>--%>

            <%--<p>服务中心</p>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon-bottom3.png"/>--%>

            <%--<p>通讯录</p>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<img src="<%=Constant.server_name%>img/icon-bottom4.png"/>--%>

            <%--<p>我的</p>--%>
        <%--</li>--%>
    <%--</ul>--%>
<%--</div>--%>


<div class="footer-nav" style="display: block;">
    <ul class="footer-menu"  id="wpt_foot">
        <li lay-href="module/main/main.jsp">
            <a>
                <i class="fa fa-home  fa-lg "></i>
            </a>

            <p>首页</p>
        </li>
        <li lay-href="module/fwzx/fwzxapp.jsp">
            <a>
                <i class="fa  fa-archive"></i>
            </a>

            <p>服务中心</p>
        </li>
        <li>
            <i class="fa fa-bell"></i>

            <p>消息</p>
        </li>
        <li>
            <i class="fa fa-user"></i>

            <p>我的</p>
        </li>
    </ul>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/wpt/foot.js"></script>


<%--<script src="<%=Constant.server_name%>js/jquery.min.js" type="text/javascript" charset="utf-8"></script>--%>

<script type="text/javascript">
    $(function(){
        var url = window.location.href;
//        var str = "123";
        console.log(url.search("main.jsp") != -1 );  // true
        if(url.search("main.jsp") != -1){
            $(".footer-menu li").eq(0).find("i").addClass("active");
        }   else if(url.search("fwzxapp.jsp") != -1){
            $(".footer-menu li").eq(1).find("i").addClass("active");
        }
//        $(".footer-menu li").click(function(){
//            $(this).find("i").addClass("active");
//            $(this).siblings().find("i").removeClass("active")
//        })
    })
</script>


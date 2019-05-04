<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="footer-nav" style="display: block;">
    <ul class="footer-menu" id="wpt_foot">
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
        <li lay-href="module/msg/msg.jsp">
            <i class="fa fa-bell"></i>

            <p>消息</p>
        </li>
        <li lay-href="module/my/my.jsp">
            <i class="fa fa-user"></i>

            <p>我的</p>
        </li>
    </ul>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/wpt/foot.js"></script>


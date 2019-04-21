<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="footer-nav" style="display: block;">
    <ul class="footer-menu" id="wpt_foot">
        <li lay-href="module/main/main.jsp">
            <img src="<%=Constant.server_name%>img/icon-bottom1.png"/>

            <p>首页</p>
        </li>
        <li lay-href="module/fwzx/fwzxapp.jsp">
            <img src="<%=Constant.server_name%>img/icon-bottom2.png"/>

            <p>服务中心</p>
        </li>
        <li>
            <img src="<%=Constant.server_name%>img/icon-bottom3.png"/>

            <p>通讯录</p>
        </li>
        <li>
            <img src="<%=Constant.server_name%>img/icon-bottom4.png"/>

            <p>我的</p>
        </li>
    </ul>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/wpt/foot.js"></script>


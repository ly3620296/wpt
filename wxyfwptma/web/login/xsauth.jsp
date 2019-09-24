<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
<%
    WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
    if (userInfo == null) {
%>
<script>
    if (!!(window.attachEvent && !window.opera)) {
        document.execCommand("stop");
    }
    else {
        window.stop();
    }
    window.location.href = wpt_serverName+"login/xslogin.jsp";
</script>
<%
    }
%>

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
    parent.location.reload();
//    window.location.href = wpt_serverName+"login/xslogin.jsp";
</script>
<%
    }
%>

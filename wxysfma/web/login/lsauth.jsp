<%@ page import="gka.lsjfgl.login.WptMaLSUserInfo" %>
<%
    WptMaLSUserInfo userInfo = (WptMaLSUserInfo) session.getAttribute("wptMaLSUserInfo");
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
//    window.location.href = wpt_serverName+"login/lslogin.jsp";
</script>
<%
    }
%>

<%@ page import="gka.controller.login.WptMaUserInfo" %>
<%
    WptMaUserInfo userInfo = (WptMaUserInfo) session.getAttribute("wptMaUserInfo");
    if (userInfo == null) {
%>
<script>
    if (!!(window.attachEvent && !window.opera)) {
        document.execCommand("stop");
    }
    else {
        window.stop();
    }
//    window.location.href = wpt_serverName;
    parent.location.reload();
</script>
<%
    }
%>

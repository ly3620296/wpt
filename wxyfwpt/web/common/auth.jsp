<%@ page import="gka.controller.login.WptUserInfo" %>
<%
    WptUserInfo userInfo = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (userInfo == null) {
%>
<script>
    if (!!(window.attachEvent && !window.opera)) {
        document.execCommand("stop");
    }
    else {
        window.stop();
    }
    window.location.href = wpt_serverName+"index.jsp";
</script>
<%
    }
%>

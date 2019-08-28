<%@ page import="gka.controller.login.WptUserInfo" %>
<%@ page import="gka.auth2.Auth2Util" %>
<%
    WptUserInfo userInfo = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (userInfo == null) {
        System.out.println(Auth2Util.createAuth2Url());
%>
<script>
    if (!!(window.attachEvent && !window.opera)) {
        document.execCommand("stop");
    }
    else {
        window.stop();
    }
    window.location.href = '<%=Auth2Util.createAuth2Url()%>';
</script>
<%
    }
%>

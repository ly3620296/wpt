package gka.controller.login;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.auth2.Auth2Util;

import javax.servlet.http.HttpSession;

@Clear
@ControllerBind(controllerKey = "/logout")
public class LogoutController extends Controller {
    public void index() {
        HttpSession session = getSession();
        WptUserInfo wptUserInfo = (WptUserInfo) session.getAttribute("wptUserInfo");
        if (session != null) {
            if (wptUserInfo != null) {
                session.removeAttribute("wptUserInfo");
                wptUserInfo = null;
                session.invalidate();
            }
        }
        String channel = getRequest().getParameter("channel");
        if (StringUtils.isEmpty(channel)) {
            renderJsp("/WEB-INF/index.jsp");
        } else if (channel.equals("wx")) {
            redirect(Auth2Util.createAuth2Url());
        } else {
            renderJsp("/WEB-INF/index.jsp");
        }

    }
}

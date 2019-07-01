package gka.controller.login;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;

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
        renderJsp("/WEB-INF/index.jsp");
    }
}

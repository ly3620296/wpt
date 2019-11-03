package gka.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import gka.controller.login.WptMaUserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller ctl = inv.getController();
        HttpServletRequest request = ctl.getRequest();
        HttpSession session = request.getSession();
        WptMaUserInfo userInfo = (WptMaUserInfo) session.getAttribute("wptMaUserInfo");
        if (userInfo == null) {
//            inv.getController().redirect("/");
            inv.getController().render("/login/login.jsp");
        } else {
            inv.invoke();
        }
    }
}

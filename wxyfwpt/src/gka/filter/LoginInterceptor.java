package gka.filter;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import gka.controller.login.WptUserInfo;
import gka.system.ReturnInfo;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        HttpSession session = controller.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        WptUserInfo userInfo = (WptUserInfo) session.getAttribute("wptUserInfo");
        if (userInfo == null) {
            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setReturn_code("-0001");
            returnInfo.setReturn_msg("登录超时，请刷新页面！");
            result.put("returnInfo", returnInfo);
            controller.renderJson(result);
        } else {
            inv.invoke();
        }

    }
}
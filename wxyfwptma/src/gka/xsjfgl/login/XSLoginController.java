package gka.xsjfgl.login;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.interceptor.LoginInterceptor;
import gka.system.ReturnInfo;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
@Clear({LoginInterceptor.class})
@ControllerBind(controllerKey = "/XSlogin")
public class XSLoginController extends Controller {
    private XSLoginDao SxloginDao = new XSLoginDao();
    @Before(XSLoginValidator.class)
    public void loginValidate() {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String account = getPara("account");
            String password = getPara("password");
            Record record = SxloginDao.loginValidate(account, password);
            if (record != null) {
                SxloginDao.setSession(record, getSession());
                returnInfo.setReturn_code("0");
                returnInfo.setReturn_msg("success");
            } else {
                returnInfo.setReturn_code("-1");
                returnInfo.setReturn_msg("用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("系统异常，请稍后重试");
        }
        renderJson(returnInfo);

    }

    public void loginOut() {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            getSession().removeAttribute("wptMaXSUserInfo");
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("系统异常，请稍后重试");
        }
        renderJson(returnInfo);
    }
    @Clear
    public void captcha() {
        renderCaptcha();
    }
}

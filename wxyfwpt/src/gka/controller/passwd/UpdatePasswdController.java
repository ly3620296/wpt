package gka.controller.passwd;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.controller.login.WptUserInfo;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


@ControllerBind(controllerKey = "/passwd/UpdatePasswdCon")
public class UpdatePasswdController extends Controller {
    WjmmDao wjmmDao = new WjmmDao();

    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String xh = wptUserInfo.getZh();
            String oldpwd = getPara("oldpwd");
            String newpwd = getPara("newpwd");
            String repwd = getPara("repwd");
            if (validate(oldpwd, newpwd, repwd)) {
                if (wjmmDao.validateOld(oldpwd, xh)) {
                    if (wjmmDao.updatePasswd(newpwd, xh) > 0) {
                        returnInfo.setReturn_code("0");
                        returnInfo.setReturn_msg("success");
                    } else {
                        returnInfo.setReturn_code("-3");
                        returnInfo.setReturn_msg("修改失败 !");
                    }
                } else {
                    returnInfo.setReturn_code("-2");
                    returnInfo.setReturn_msg("原密码不正确，请重新输入 !");
                }
            } else {
                returnInfo.setReturn_code("-1");
                returnInfo.setReturn_msg("非法请求!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("系统异常，请稍后重试");
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }


    private boolean validate(String oldpwd, String newpwd, String repwd) {
        String pattern = "[0-9A-Za-z!@#$%^&*()_]+";
        if (oldpwd.length() < 6) {
            return false;
        }
        if (newpwd == null || "".equals(newpwd) || !Pattern.matches(pattern, newpwd) || newpwd.length() < 8 || newpwd.length() > 18) {
            return false;
        }
        if (repwd != null && newpwd != null && !newpwd.equals(repwd)) {
            return false;
        }
        return true;
    }
}

package gka.controller.passwd;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
@Clear
@ControllerBind(controllerKey = "/passwd/passwdCon")
public class PasswdController extends Controller {
    WjmmDao wjmmDao = new WjmmDao();

    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String isLegal = (String) getSession().getAttribute("secLegal");
            String xh = (String) getSession().getAttribute("passXh");
            getSession().removeAttribute("secLegal");
            getSession().removeAttribute("passXh");
            if (isLegal != null && "0".equals(isLegal)) {
                String newpwd = getPara("newpwd");
                String repwd = getPara("repwd");
                if (validate(newpwd, repwd)) {
                    if (wjmmDao.updatePasswd(newpwd, xh) > 0) {
                        returnInfo.setReturn_code("0");
                        returnInfo.setReturn_msg("success");
                    } else {
                        returnInfo.setReturn_code("-2");
                        returnInfo.setReturn_msg("修改失败 !");
                    }
                } else {
                    returnInfo.setReturn_code("-1");
                    returnInfo.setReturn_msg("非法请求!");
                }
            } else {
                returnInfo.setReturn_code("-3");
                returnInfo.setReturn_msg("非法请求2!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("系统异常，请稍后重试");
        }

        result.put("returnInfo", returnInfo);
        renderJson(result);

    }

    private boolean validate(String newpwd, String repwd) {
        System.out.println(newpwd);
        String pattern = "[0-9A-Za-z!@#$%^&*()_]+";
        System.out.println(Pattern.matches(pattern, newpwd));
        if (newpwd == null || "".equals(newpwd) || !Pattern.matches(pattern, newpwd) || newpwd.length() < 8 || newpwd.length() > 18) {
            return false;
        }
        if (repwd != null && newpwd != null && !newpwd.equals(repwd)) {
            return false;
        }
        return true;
    }


}

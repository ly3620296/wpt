package gka.controller.passwd;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.Map;

@Clear
@ControllerBind(controllerKey = "/passwd/wjmmValidate")
public class WjmmValidateController extends Controller {
    private WjmmDao wjmmDao = new WjmmDao();

    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String name = getPara("name");
            String idCard = getPara("idCard");
            String xh = getPara("xh");
            if (validate(name, idCard, xh)) {
                if (wjmmDao.checkInfo(xh, idCard, name)) {
                    //设置校验合法标记，防止跳过验证
                    getSession().setAttribute("isLegal", "0");
                    getSession().setAttribute("passXh", xh);
                    returnInfo.setReturn_code("0");
                    returnInfo.setReturn_msg("success");
                } else {
                    returnInfo.setReturn_code("-2");
                    returnInfo.setReturn_msg("信息有误,禁止修改密码 !");
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

    private boolean validate(String name, String idCard, String xh) {
        if (name == null || "".equals(name)) {
            return false;
        }
        if (idCard == null || "".equals(idCard)) {
            return false;
        }
        if (xh == null || "".equals(xh)) {
            return false;
        }
        return true;
    }
}

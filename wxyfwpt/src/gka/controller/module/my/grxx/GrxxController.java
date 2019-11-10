package gka.controller.module.my.grxx;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.Map;

@ControllerBind(controllerKey = "/my/grxx")
public class GrxxController extends Controller {
    private GrxxDao grxxDao = new GrxxDao();

    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String xh = wptUserInfo.getZh();
            Record re = grxxDao.ldQuery(xh);
            result.put("ld", re);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }
}

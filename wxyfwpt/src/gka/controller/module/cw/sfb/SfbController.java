package gka.controller.module.cw.sfb;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.controller.login.WptUserInfo;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

@ControllerBind(controllerKey = "/cw/sfb")
public class SfbController extends Controller {
    private SfbDao sfbDao = new SfbDao();

    /**
     * 收费表
     */
    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String zjhm = wptUserInfo.getZjhm();
            //收费表
            List<Record> sfbList = sfbDao.sfb(zjhm);
            result.put("sfbList", sfbList);
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

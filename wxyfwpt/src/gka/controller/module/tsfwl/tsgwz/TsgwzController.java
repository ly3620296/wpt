package gka.controller.module.tsfwl.tsgwz;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/tsfwl/tsgwz")
public class TsgwzController extends Controller {
    private TsgwzDao tsgwzDao = new TsgwzDao();

    /**
     * 图书馆违章查询
     */
    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String sfzh = wptUserInfo.getZjhm();
            //收费表
            List<Record> tsgwzList = tsgwzDao.tsgwz(sfzh);
            result.put("tsgwzList", tsgwzList);
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

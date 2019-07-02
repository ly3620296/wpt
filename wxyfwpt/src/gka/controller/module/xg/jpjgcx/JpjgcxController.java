package gka.controller.module.xg.jpjgcx;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 综合评分查询
 */

@ControllerBind(controllerKey = "/xg/jpjgcx")
public class JpjgcxController extends Controller {
    private JpjgcxDao jpjgcxDao = new JpjgcxDao();


    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String xh = wptUserInfo.getZh();
            List<Record> jpjgcxList = jpjgcxDao.jpjgcx(xh);
            result.put("jpjgcxList", jpjgcxList);
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

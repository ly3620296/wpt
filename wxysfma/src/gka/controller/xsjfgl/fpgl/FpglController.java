package gka.controller.xsjfgl.fpgl;


import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.wyjf.JfInfo;
import gka.controller.xsjfgl.wyjf.WyjfDao;
import gka.xsjfgl.login.WptMaXSUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/xsjfgl/fpgl")
public class FpglController extends Controller {
    private FpglDao fpglDao = new FpglDao();
    private WyjfDao wyjfDao = new WyjfDao();

    /**
     * 订单查询
     */
    public void index() {
        Map map = new HashMap();
        try {
            WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
            String xh = userInfo.getZh();
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            Page<Record> paginate = fpglDao.getFpInfo(page, limit, xh);
            map.put("code", "0");
            map.put("msg", "success");
            map.put("data", paginate.getList());
            map.put("count", paginate.getTotalRow());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }


}

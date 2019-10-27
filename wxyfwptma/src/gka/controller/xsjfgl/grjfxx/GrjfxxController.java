package gka.controller.xsjfgl.grjfxx;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.controller.xsjfgl.wyjf.WyjfDao;
import gka.xsjfgl.login.WptMaXSUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/xsjfgl/grjfxx")
public class GrjfxxController extends Controller {
    GrjfxxDao grjfxxDao = new GrjfxxDao();
    WyjfDao wyjfDao = new WyjfDao();

    public void index() {
        Map map = new HashMap();
        try {
            WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
            String xh = userInfo.getZh();
            List<Record> titles = wyjfDao.queryTitle();
            List<Record> jfjl = getJfjl(titles, xh);
            map = ReKit.toMap(jfjl.size(), jfjl);
            map.put("titles", titles);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }

    public void title() {
        Map map = new HashMap();
        try {
            List<Record> titles = wyjfDao.queryTitle();
            map.put("titles", titles);
            map.put("code", "0");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }

    //缴费记录
    private List<Record> getJfjl(List<Record> titles, String xh) {
        //应收项目合计 已交和未交
        List<Record> totals = wyjfDao.queryTotal(titles, xh);
        List<Record> yj = new ArrayList<Record>();
        for (Record total : totals) {
            boolean flag = true;
            for (Record title : titles) {
                String val = total.getStr(title.getStr("JFXMID"));
                if (val.equals("0")) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                yj.add(total);
            }
        }
        return yj;
    }

}

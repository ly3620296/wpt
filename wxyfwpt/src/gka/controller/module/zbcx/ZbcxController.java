package gka.controller.module.zbcx;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.module.my.wdcy.WdcyDao;
import gka.kit.ReturnKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成绩查询
 */

@ControllerBind(controllerKey = "/zbcx")
public class ZbcxController extends Controller {
    private WdcyDao wdcyDao = new WdcyDao();

    public void table() {
        String id = getPara("id");
        String zh = getPara("zh");
        try {
            Map map = new HashMap();
            List<Record> list = Db.find("select zd,zdms from wptma_zbzdb t where id=? order by sx ", id);
            String sql = "select yhm aaaaa from wptma_zbsjb where id=? and yhm = ?";
            String aaaaa = "";
            for (int i = 0; i < list.size(); i++) {
                aaaaa += "," + (list.get(i).get("zd") == null ? "" : list.get(i).get("zd").toString());
            }
            sql = sql.replace("aaaaa", aaaaa);
            Record result = Db.findFirst(sql, id, zh);
            map.put("headList", list);
            map.put("bodyList", result);
            renderJson(ReturnKit.retOk(map));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(ReturnKit.retFail("查询失败！错误信息为" + (e.toString().length() > 50 ? e.toString().substring(0, 50) : e.toString())));
        }
    }

    public void list() {
        String zh = getPara("zh");
        try {
            Map map = new HashMap();
            List<Record> list = Db.find("select a.id,a.bt from wptma_zbbtb a,wptma_zbsjb b where a.id= b.id and b.yhm=? order by a.time desc", zh);
            map.put("list", list);
            renderJson(ReturnKit.retOk(map));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(ReturnKit.retFail("查询失败！错误信息为" + (e.toString().length() > 50 ? e.toString().substring(0, 50) : e.toString())));
        }
    }

}

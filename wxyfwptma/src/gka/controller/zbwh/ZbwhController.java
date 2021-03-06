package gka.controller.zbwh;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.controller.login.WptMaUserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/zbwh")
public class ZbwhController extends Controller {

    public void list() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            int count = ZbwhDao.searchCount(userInfo.getM_id());    // 查找数据条数
            int start = limit * page - limit + 1;
            int end = limit * page;
            List<Record> list = ZbwhDao.list(start, end, userInfo.getM_id());
            Map map = ReKit.toMap(count, list);
            renderJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del() {
        try {
            String[] array = getParaValues("id[]");
            if (array.length > 0) {
                for (int i = 0; i < array.length; i++) {
                    Db.update("delete wptma_zbbtb where id=?", array[i]);
                    Db.update("delete wptma_zbsjb where id=?", array[i]);
                    Db.update("delete wptma_zbzdb where id=?", array[i]);
                }
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("请选择需要删除的数据!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Clear
    public void test() {
        String zd = "zd2";
        try {
            int i = Db.update("alter table wptma_zbsjb add " + zd + " varchar(50)");
            renderJson(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void table() {
        String id = getPara("id");
        try {
            Map map = new HashMap();
            List<Record> list = Db.find("select zd,zdms from wptma_zbzdb t where id=? order by sx ", id);
            String sql = "select yhm aaaaa from wptma_zbsjb where id=?";
            String aaaaa = "";
            for (int i = 0; i < list.size(); i++) {
                aaaaa += "," + (list.get(i).get("zd") == null ? "" : list.get(i).get("zd").toString());
            }
            sql = sql.replace("aaaaa", aaaaa);
            List<Record> result = Db.find(sql, id);
            map.put("headList", list);
            map.put("bodyList", result);
            renderJson(ReturnKit.retOk(map));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(ReturnKit.retFail("查询失败！错误信息为" + (e.toString().length() > 50 ? e.toString().substring(0, 50) : e.toString())));
        }
    }

}

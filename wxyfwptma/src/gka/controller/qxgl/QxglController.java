package gka.controller.qxgl;

import com.alibaba.fastjson.JSONArray;
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
import java.util.Objects;

@ControllerBind(controllerKey = "/qxgl")
public class QxglController extends Controller {

    public void list() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            int count = QxglDao.searchCount(userInfo.getM_id());    // 查找数据条数
            int start = limit * page - limit + 1;
            int end = limit * page;
            List<Record> list = QxglDao.list(start, end, userInfo.getM_id());
            Map map = ReKit.toMap(count, list);
            renderJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            String q_name = getPara("q_name");
            String q_qx = getPara("q_qx");
            String q_ms = getPara("q_ms");
            int result = QxglDao.add(q_name, q_qx, q_ms);
            if (result > 0) {
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("添加失败，请稍后再试!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            String jsid = getPara("jsid");
            String[] menu = getParaValues("menu[]");
            Db.update("delete wptma_jscd where Q_ID=?",jsid);
            for (int i = 0; i < menu.length; i++) {
                Db.update("insert into wptma_jscd (Q_ID,M_ID) values(?,?)", jsid, menu[i]);
            }
            renderJson(ReturnKit.retOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del() {
        try {
            String[] array = getParaValues("id[]");
            if (array.length > 0) {
                for (int i = 0; i < array.length; i++) {
                    Db.update("delete wptma_qxgl where q_id=?", array[i]);
                    Db.update("delete wptma_jscd where q_id=?", array[i]);
                }
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("请选择需要删除的数据!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void query() {
        try {
            Map map = new HashMap<String, Objects>();
            String id = getPara("id");
            Record re = Db.findFirst("select Q_ID,Q_NAME from wptma_qxgl where q_id=?", id);
            if (re != null) {
                map.put("re", re);
                String menuTree = QxglDao.createMenuTree();
                map.put("menuTree", JSONArray.parseArray(menuTree));
                List<Record> this_re = Db.find("select m_id from wptma_jscd where q_id=?", id);
                map.put("this_re", this_re);
                renderJson(ReturnKit.retOk(map));
            } else {
                renderJson(ReturnKit.retFail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

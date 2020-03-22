package gka.controller.lsjfgl.qxgl;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerBind(controllerKey = "/qxgl")
public class QxglController extends Controller {

    public void list() {
        try {
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            int count = QxglDao.searchCount();    // 查找数据条数
            int start = limit * page - limit + 1;
            int end = limit * page;
            List<Record> list = QxglDao.list(start, end);
            Map map = ReKit.toMap(count, list);
            renderJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        try {
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
            String jsid = getPara("jsid");
            String[] menu = getParaValues("menu[]");
            Db.update("delete sfma_jscd where Q_ID=?",jsid);
            for (int i = 0; i < menu.length; i++) {
                Db.update("insert into sfma_jscd (Q_ID,M_ID) values(?,?)", jsid, menu[i]);
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
                    Db.update("delete sfma_qxgl where q_id=?", array[i]);
                    Db.update("delete sfma_jscd where q_id=?", array[i]);
                }
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("请选择需要删除的数据!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void queryQxgl() {
        try {
            List<Record> re = Db.find("select q_id,q_name from sfma_qxgl");
            if (re.size() > 0) {
                renderJson(ReturnKit.retOk(re));
            } else {
                renderJson(ReturnKit.retFail("暂无用户权限，请先添加权限!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void query() {
        try {
            Map map = new HashMap<String, Objects>();
            String id = getPara("id");
            Record re = Db.findFirst("select Q_ID,Q_NAME from sfma_qxgl where q_id=?", id);
            if (re != null) {
                map.put("re", re);
                String menuTree = QxglDao.createMenuTree();
                map.put("menuTree", JSONArray.parseArray(menuTree));
                List<Record> allFatherMenu = Db.find("select m_id from sfma_menu where m_level='1' and m_state='1'");
                map.put("allFatherMenu",allFatherMenu);
                List<Record> this_re = Db.find("select m_id from sfma_jscd where q_id=?", id);
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

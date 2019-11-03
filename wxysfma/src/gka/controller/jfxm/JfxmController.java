package gka.controller.jfxm;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.common.kit.controller.LController;
import gka.controller.login.WptMaUserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
@ControllerBind(controllerKey = "/jfxm")
public class JfxmController extends LController {

    public void list() {
        WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
        int page = Integer.parseInt(getPara("page"));
        int limit = Integer.parseInt(getPara("limit"));
        String title = getPara("title");
        int count = JfxmDao.searchCount(userInfo.getM_id(), title);    // 查找数据条数
        int start = limit * page - limit + 1;
        int end = limit * page;
        List<Record> list = JfxmDao.list(start, end, userInfo.getM_id(), title);
        Map map = ReKit.toMap(count, list);
        renderJson(map);
    }

    public void add() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            String xmmc = getPara("xmmc");
            String xmje = getPara("xmje");
            String xmlxid = getPara("xmlxid");
            String sfbx = getPara("sfbx");
            String id = "";
            while (true) {
                id = JfxmDao.createXmId();
                String sql = "select * from wpt_jfxm t where xmid=?";
                Record record = Db.findFirst(sql, id);
                if (record == null) {
                    break;
                }
            }
            int result = JfxmDao.add(id, xmmc, xmje, xmlxid, sfbx);
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
            String xmid = getPara("xmid");
            String xmmc = getPara("xmmc");
            String xmje = getPara("xmje");
            String xmlxid = getPara("xmlxid");
            String sfbx = getPara("sfbx");
            int result = JfxmDao.edit(xmid, xmmc, xmje, xmlxid, sfbx);
            if (result > 0) {
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("添加失败，请稍后再试!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del() {
        try {
            String[] array = getParaValues("id[]");
            if (array.length > 0) {
                for (int i = 0; i < array.length; i++) {
                    Db.update("delete wpt_jfxm where xmid=?", array[i]);
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
            Map<String, Object> map = new HashMap<String, Object>();
            String id = getPara("id");
            Record re = Db.findFirst("select * from wpt_jfxm where xmid=?", id);
            List<Record> list = Db.find("select * from wpt_jfxmlx");
            if (re != null) {
                map.put("jfxm", re);
                map.put("jfxmlx", list);
                renderJson(ReturnKit.retOk(map));
            } else {
                renderJson(ReturnKit.retFail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryjfxmlx() {
        try {
            List<Record> list = Db.find("select * from wpt_jfxmlx ");
            if (list.size() > 0) {
                renderJson(ReturnKit.retOk(list));
            } else {
                renderJson(ReturnKit.retFail("暂无缴费项目类型"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

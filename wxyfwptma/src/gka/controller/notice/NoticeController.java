package gka.controller.notice;

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

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
@ControllerBind(controllerKey = "/notice")
public class NoticeController extends Controller {

    public void list() {
        WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
        int page = Integer.parseInt(getPara("page"));
        int limit = Integer.parseInt(getPara("limit"));
        String title = getPara("title");
        String time = getPara("time");
        int count = NoticeDao.searchCount(userInfo.getM_id(),title,time);    // 查找数据条数
        int start = limit * page - limit + 1;
        int end = limit * page;
        List<Record> list = NoticeDao.list(start, end, userInfo.getM_id(),title,time);
        Map map = ReKit.toMap(count, list);
        renderJson(map);
    }

    public void add() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            String g_state = getPara("g_state");
            String g_title = getPara("g_title");
            String g_text = getPara("g_text");
            String g_xy = getPara("g_xy");
            String g_group = getPara("g_group");
            int result = NoticeDao.add(g_title, userInfo.getM_id(), g_text, g_state, g_xy,g_group);
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
            String g_id = getPara("g_id");
            String g_state = getPara("g_state");
            String g_title = getPara("g_title");
            String g_text = getPara("g_text");
            String g_xy = getPara("g_xy");
            String g_group = getPara("g_group");
            int result = NoticeDao.edit(g_title, userInfo.getM_id(), g_text, g_state, g_id, g_xy,g_group);
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
                    Db.update("delete wptma_gg where g_id=?", array[i]);
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
            Map map = new HashMap();
            String id = getPara("id");
            Record re = Db.findFirst("select * from wptma_gg where g_id=?", id);
            if (re != null) {
                map.put("re", re);
                List<Record> list = Db.find("select X_NAME,X_CODE from wptma_xygl");
                map.put("xy", list);
                renderJson(ReturnKit.retOk(map));
            } else {
                renderJson(ReturnKit.retFail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryXy() {
        try {
            List<Record> list = Db.find("select X_NAME,X_CODE from wptma_xygl");
            if (list.size() > 0) {
                renderJson(ReturnKit.retOk(list));
            } else {
                renderJson(ReturnKit.retFail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

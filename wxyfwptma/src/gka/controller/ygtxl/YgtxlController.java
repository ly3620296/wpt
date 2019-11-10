package gka.controller.ygtxl;

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
@ControllerBind(controllerKey = "/ygtxl")
public class YgtxlController extends LController {

    public void list() {
        try {


            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            String xm = getPara("xm");
            int count = YgtxlDao.searchCount(userInfo.getM_id(), xm);    // 查找数据条数
            int start = limit * page - limit + 1;
            int end = limit * page;
            List<Record> list = YgtxlDao.list(start, end, userInfo.getM_id(), xm);
            Map map = ReKit.toMap(count, list);
            renderJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    select t.*, t.rowid from wptma_ygtxl t
    public void add() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            String xm = getPara("xm");
            String dh = getPara("dh");
            String xyid = getPara("xyid");
            int result = YgtxlDao.add(xm, dh, xyid);
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
            String id = getPara("id");
            String xm = getPara("xm");
            String dh = getPara("dh");
            String xyid = getPara("xyid");
            int result = YgtxlDao.edit(id, xm, dh, xyid);
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
                    Db.update("delete wptma_ygtxl where id=?", array[i]);
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
            Record re = Db.findFirst("select * from wptma_ygtxl where id=?", id);
            List<Record> list = Db.find("select * from wptma_xygl");
            if (re != null) {
                map.put("ygtxl", re);
                map.put("xy", list);
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
            List<Record> list = Db.find("select * from wptma_xygl");
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

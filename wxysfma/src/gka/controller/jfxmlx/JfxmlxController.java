package gka.controller.jfxmlx;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.common.kit.controller.LController;
import gka.controller.login.WptMaUserInfo;
import gka.controller.user.UserDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerBind(controllerKey = "/jfxmlx")
public class JfxmlxController extends LController {

    public void list() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            String title = getPara("title");
            int count = JfxmlxDao.searchCount(userInfo.getM_id(),title);    // 查找数据条数
            int start = limit * page - limit + 1;
            int end = limit * page;
            List<Record> list = JfxmlxDao.list(start, end, userInfo.getM_id(),title);
            Map map = ReKit.toMap(count, list);
            renderJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            String xmlxmc = getPara("xmlxmc");
            int result = JfxmlxDao.add(xmlxmc);
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
            String xmlxid = getPara("xmlxid");
            String xmlxmc = getPara("xmlxmc");
            int result = JfxmlxDao.edit(xmlxid, xmlxmc);
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
                    Db.update("delete wpt_jfxmlx where xmlxid=?", array[i]);
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
            String id = getPara("id");
            Record re = Db.findFirst("select XMLXID,XMLXMC from wpt_jfxmlx where XMLXID=?", id);
            if (re != null) {
                renderJson(ReturnKit.retOk(re));
            } else {
                renderJson(ReturnKit.retFail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

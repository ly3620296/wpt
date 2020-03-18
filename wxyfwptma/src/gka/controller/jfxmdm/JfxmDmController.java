package gka.controller.jfxmdm;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.common.kit.controller.LController;
import gka.controller.jfxmlx.JfxmlxDao;
import gka.controller.login.WptMaUserInfo;

import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/jfxmdm")
public class JfxmDmController extends LController {
    private JfxmDmDao jfxmDmDao = new JfxmDmDao();

    public void list() {
        try {
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            String jfxmmc = getPara("JFXMMC");
            int count = JfxmDmDao.searchCount(jfxmmc);    // 查找数据条数
            int start = limit * page - limit + 1;
            int end = limit * page;
            List<Record> list = jfxmDmDao.list(start, end, jfxmmc);
            Map map = ReKit.toMap(count, list);
            renderJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        try {
            String jfxmid = getPara("JFXMID");
            String jfxmmc = getPara("JFXMMC");
            int result = jfxmDmDao.add(jfxmid, jfxmmc);
            if (result > 0) {
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("添加失败，缴费项目代码不可重复!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        try {
            String id = getPara("ID");
            String xmlxid = getPara("JFXMID");
            String xmlxmc = getPara("JFXMMC");
            int result = jfxmDmDao.edit(id, xmlxid, xmlxmc);
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
                    Db.update("DELETE JFXMDM WHERE ID=?", array[i]);
                }
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("请选择需要删除的数据!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateStatus() {
        try {
            String my_id = getPara("my_id");
            String my_type = getPara("my_type");
            String my_status = getPara("my_status");
            int result = jfxmDmDao.updateStatus(my_id, my_type, my_status);
            if (result > 0) {
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("修改失败，请稍后再试!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void query() {
        try {
            String id = getPara("id");
            Record re = Db.findFirst("SELECT ID,JFXMID,JFXMMC,SFBX FROM JFXMDM WHERE ID=?", id);
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

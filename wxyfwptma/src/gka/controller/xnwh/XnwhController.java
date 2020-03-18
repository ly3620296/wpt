package gka.controller.xnwh;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.common.kit.controller.LController;

import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/xnwh")
public class XnwhController extends LController {
    private XnwhDao xnwhDao = new XnwhDao();

    public void list() {
        try {
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            String jfxmmc = getPara("JFXMMC");
            int count = XnwhDao.searchCount();    // 查找数据条数
            int start = limit * page - limit + 1;
            int end = limit * page;
            System.out.println("start-----"+start);
            System.out.println("end-----"+end);
            List<Record> list = xnwhDao.list(start, end);
            Map map = ReKit.toMap(count, list);
            renderJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        try {
            String xnmc = getPara("XNMC");
            int result = xnwhDao.add(xnmc);
            if (result > 0) {
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("添加失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        try {
            String id = getPara("XNID");
            String xnmc = getPara("XNMC");
            int result = xnwhDao.edit(id, xnmc);
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
                    Db.update("DELETE FROM XN WHERE XNID=?", array[i]);
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
            Record re = Db.findFirst("SELECT XNID,XNMC FROM XN WHERE XNID=?", id);
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

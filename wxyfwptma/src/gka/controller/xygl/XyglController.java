package gka.controller.xygl;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.common.kit.controller.LController;
import gka.controller.login.WptMaUserInfo;

import java.util.List;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
@ControllerBind(controllerKey = "/xygl")
public class XyglController extends LController {

    public void list() {
        WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
        int page = Integer.parseInt(getPara("page"));
        int limit = Integer.parseInt(getPara("limit"));
        int count = XyglDao.searchCount(userInfo.getM_id());    // 查找数据条数
        int start = limit * page - limit + 1;
        int end = limit * page;
        List<Record> list = XyglDao.list(start, end, userInfo.getM_id());
        Map map = ReKit.toMap(count, list);
        renderJson(map);
    }

    public void add() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            String x_name = getPara("x_name");
            String x_code = getPara("x_code");
            int result = XyglDao.add(x_name, x_code);
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
            String x_id = getPara("x_id");
            String x_name = getPara("x_name");
            String x_code = getPara("x_code");
            int result = XyglDao.edit(x_id, x_name, x_code);
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
                    Db.update("delete wptma_xygl where x_id=?", array[i]);
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
            Record re = Db.findFirst("select X_ID,X_NAME,X_CODE from wptma_xygl where x_id=?", id);
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

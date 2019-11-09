package gka.controller.scyh;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.common.kit.controller.LController;
import gka.controller.login.WptMaUserInfo;
import gka.controller.qxgl.QxglDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerBind(controllerKey = "/scyh")
public class ScyhController extends LController {

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

}

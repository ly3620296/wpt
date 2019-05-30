package gka.controller.notice;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.controller.login.WptMaUserInfo;
import gka.system.ReturnInfo;

import java.util.ArrayList;
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
        int count = NoticeDao.searchCount(userInfo.getM_id());    // 查找数据条数
        int start = limit * page - limit + 1;
        int end = limit * page;
        List<Record> list = NoticeDao.list(start, end, userInfo.getM_id());
        Map map = ReKit.toMap(count, list);
        renderJson(map);
    }

}

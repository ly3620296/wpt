package gka.controller.user;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.controller.login.WptMaUserInfo;

import java.util.List;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
@ControllerBind(controllerKey = "/user")
public class UserController extends Controller {

    public void list() {
        WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
        int page = Integer.parseInt(getPara("page"));
        int limit = Integer.parseInt(getPara("limit"));
        int count = UserDao.searchCount(userInfo.getM_id());    // 查找数据条数
        int start = limit * page - limit + 1;
        int end = limit * page;
        List<Record> list = UserDao.list(start, end, userInfo.getM_id());
        Map map = ReKit.toMap(count, list);
        renderJson(map);
    }

    public void add() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            String m_name = getPara("m_name");
            String m_zh = getPara("m_zh");
            String m_qx = getPara("m_qx");
            int result = UserDao.add(m_name, m_zh, m_qx);
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
            String m_name = getPara("m_name");
            String m_id = getPara("m_id");
            String m_qx = getPara("m_qx");
            int result = UserDao.edit(m_id, m_name, m_qx);
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
                    Db.update("delete wptma_user where m_id=?", array[i]);
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
            Record re = Db.findFirst("select M_ID,M_NAME,M_ZH,M_QX from wptma_user where m_id=?", id);
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

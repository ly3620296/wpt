package gka.controller.menu;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.controller.login.WptMaUserInfo;
import gka.controller.notice.NoticeDao;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
@ControllerBind(controllerKey = "/menu")
public class MenuController extends Controller {

    public void list() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            int count = MenuDao.searchCount(userInfo.getM_id());    // 查找数据条数
            int start = limit * page - limit + 1;
            int end = limit * page;
            List<Record> list = MenuDao.list(start, end);
            Map map = ReKit.toMap(count, list);
            renderJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            String m_level = getPara("m_level");
            String m_name = getPara("m_name");
            String m_url = getPara("m_url");
            String m_state = getPara("m_state");
            String m_father = getPara("m_father");

            int result = MenuDao.add(m_level, m_name, m_url, m_state, m_father);
            if (result > 0) {
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("添加失败，请稍后再试!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void father() {
        try {
            List<Record> re = Db.find("select M_ID,M_NAME from wptma_menu where m_level='1' and m_state='1'");
            if (re.size() > 0) {
                renderJson(ReturnKit.retOk(re));
            } else {
                renderJson(ReturnKit.retFail("暂无父级菜单，请先添加父级菜单!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            String m_id = getPara("m_id");
            String m_level = getPara("m_level");
            String m_name = getPara("m_name");
            String m_url = getPara("m_url");
            String m_state = getPara("m_state");
            String m_father = getPara("m_father");
            int result = MenuDao.edit(m_id, m_level, m_name, m_url, m_state,m_father);
            if (result > 0) {
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("修改失败，请稍后再试!"));
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
                    Db.update("delete wptma_menu where m_id=?", array[i]);
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
            Record re = Db.findFirst("select * from wptma_menu where m_id=?", id);
            List<Record> list = Db.find("select M_ID,M_NAME from wptma_menu where m_level='1' and m_state='1'");
            if (re != null) {
                map.put("thMenu",re);
                map.put("allFather",list);
                renderJson(ReturnKit.retOk(map));
            } else {
                renderJson(ReturnKit.retFail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getMenu() {
        WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
        List<Record> list=null;
        try {
            String sql = "select m.* from wptma_user u,wptma_qxgl q,wptma_jscd j,wptma_menu m where u.m_qx=q.q_id and " +
                    "j.q_id=q.q_id and m.m_id = j.m_id and u.m_id=? order by m.m_level";
            list = Db.find(sql, userInfo.getM_id());
        } catch (Exception e) {
            e.printStackTrace();

        }
        renderJson(list);
    }

}

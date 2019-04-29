package gka.controller.module.fwzx;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.controller.module.my.wdcy.MenuChild;
import gka.controller.module.my.wdcy.MenuParent;
import gka.controller.module.my.wdcy.WdcyDao;
import gka.system.ReturnInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成绩查询
 */

@ControllerBind(controllerKey = "/fwzx")
public class FwzxController extends Controller {
    private WdcyDao wdcyDao = new WdcyDao();

    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String qx = wptUserInfo.getJsdm();
            List<Record> parentMenuMenus = wdcyDao.allParentMenu(qx);
            List<MenuParent> menuParents = parentMenus(parentMenuMenus, qx);
            result.put("menuParents", menuParents);
            returnInfo.setReturn_code("0000");
            returnInfo.setReturn_msg("success");
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    private List<MenuParent> parentMenus(List<Record> parentMenuMenus, String qx) {
        List<MenuParent> menuParents = new ArrayList<MenuParent>();
        for (int i = 0; i < parentMenuMenus.size(); i++) {
            Record re = parentMenuMenus.get(i);
            MenuParent menuParent = new MenuParent();
            menuParent.setMenu_id(re.getStr("ID"));
            menuParent.setMenu_name(re.getStr("NAME"));
            List<Record> childRes = wdcyDao.childMenu(qx, menuParent.getMenu_id());
            List<MenuChild> menuChilds = new ArrayList<MenuChild>();
            for (int j = 0; j < childRes.size(); j++) {
                MenuChild menuChild = new MenuChild();
                Record childRe = childRes.get(j);
                menuChild.setMenu_id((String) childRe.get("ID", ""));
                menuChild.setMenu_name((String) childRe.get("name", ""));
                menuChild.setMenu_parId((String) childRe.get("FATHER", ""));
                menuChild.setMenu_img((String) childRe.get("IMG", ""));
                menuChild.setMenu_url((String) childRe.get("URL", ""));
                menuChilds.add(menuChild);
            }
            menuParent.setMenuChildList(menuChilds);
            menuParents.add(menuParent);
        }
        return menuParents;
    }


}

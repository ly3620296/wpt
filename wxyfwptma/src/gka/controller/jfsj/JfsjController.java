package gka.controller.jfsj;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.common.kit.controller.LController;
import gka.controller.login.WptMaUserInfo;
import gka.controller.user.UserDao;

import java.util.List;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
@ControllerBind(controllerKey = "/jfsj")
public class JfsjController extends LController {

    public void list() {
        int count = 1;    // 查找数据条数
        List<Record> list = Db.find("select * from wptma_jfsj");
        Map map = ReKit.toMap(count, list);
        renderJson(map);
    }
    public void edit() {
        try {
            String dateStart = getPara("dateStart");
            String dateEnd = getPara("dateEnd");
            int i = Db.update("update wptma_jfsj set start_time=?,end_time=?",dateStart,dateEnd);
            if(i>0){
                renderJson(ReturnKit.retOk());
            }else {
                renderJson(ReturnKit.retFail("添加失败，请稍后再试!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(ReturnKit.retFail("系统异常，请稍后再试!"));
        }
    }
    public void query() {
        try {
            Record re = Db.findFirst("select * from wptma_jfsj");
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

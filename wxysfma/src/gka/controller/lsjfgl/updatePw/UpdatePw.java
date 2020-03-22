package gka.controller.lsjfgl.updatePw;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import gka.common.kit.ReturnKit;
import gka.lsjfgl.login.WptMaLSUserInfo;

/**
 * Created by 小鹏鹏 on 2019/7/17.
 */
@ControllerBind(controllerKey = "/pwd")
public class UpdatePw extends Controller {
    public void updatePw() {
        WptMaLSUserInfo userInfo = (WptMaLSUserInfo) getSession().getAttribute("wptMaLSUserInfo");
        String old_pw = getPara("old_pw");
        String new_pw_1 = getPara("new_pw_1");
        String new_pw_2 = getPara("new_pw_2");
        if (old_pw.equals(userInfo.getM_mm())) {
            if (new_pw_1.equals(new_pw_2)) {
                int i = Db.update("update sfma_user set m_mm=? where m_zh=?", new_pw_2, userInfo.getM_zh());
                if (i > 0) {
                    userInfo.setM_mm(new_pw_2);
                    getSession().setAttribute("wptMaLSUserInfo",userInfo);
                    renderJson(ReturnKit.retOk());
                } else {
                    renderJson(ReturnKit.retFail("密码修改失败!"));
                }
            } else {
                renderJson(ReturnKit.retFail("两次新密码输入不一致!"));
            }
        } else {
            renderJson(ReturnKit.retFail("原密码输入错误!"));
        }
    }
}

package gka.controller.login;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class LoginDao {

    public Record loginValidate(String account, String password) {
        String sql = "select * from wptma_user where m_zh=? and m_mm=?";
        Record re = Db.findFirst(sql, account, password);
        return re;
    }

}

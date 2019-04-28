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
        String sql = "SELECT ZH,XM,XB,MZ,ZZMM,ZJLX,ZJHM,JGDM,JGMC,ZYDM,ZYMC,BJDM,BJMC,NJDM,NJMC,XZ,SFZX,XJZT,LXDH,YX,JSDM,JSMC FROM WPT_YH WHERE ZH=? AND KL=?";
        Record re = Db.findFirst(sql, account, password);
        return re;
    }

}

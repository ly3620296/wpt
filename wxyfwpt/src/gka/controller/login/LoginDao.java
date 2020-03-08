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
        password = getMd5Pass(password);
        System.out.println("password====" + password);
        String sql = "SELECT ZH,XM,XB,MZ,ZZMM,ZJLX,ZJHM,JGDM,JGMC,ZYDM,ZYMC,BJDM,BJMC,NJDM,NJMC,XZ,SFZX,XJZT,LXDH,YX,JSDM,JSMC,ZYFXDM,ZYFXMC,OPENID FROM WPT_YH WHERE ZH=? AND KL=?";
        Record re = Db.findFirst(sql, account, password);
        return re;
    }

    public String getMd5Pass(String password) {
//        String sql = "SELECT MM FROM V_WPT_MD5";
        String sql = "SELECT '{MD5}'||utl_raw.cast_to_varchar2(utl_encode.base64_encode(Utl_Raw.Cast_To_Raw(sys.dbms_obfuscation_toolkit.md5(input_string => '" + password + "')))) mm FROM DUAL WHERE 1=1";
        Record first = Db.findFirst(sql);
        return first.getStr("MM");
    }


}

package gka.controller.passwd;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by Administrator on 2019/6/17 0017.
 */
public class WjmmDao {
    public boolean checkInfo(String zh, String zjhm, String xm) {
        String sql = "SELECT ZH  FROM WPT_YH WHERE ZH=? AND ZJHM=? AND XM=?";
        Record re = Db.findFirst(sql, zh, zjhm, xm);
        return re != null;
    }

    public int updatePasswd(String pass, String xh) {
        String sql = "UPDATE WPT_YH SET KL=? WHERE ZH=?";
        return Db.update(sql, pass, xh);
    }

    public boolean validateOld(String validateOld, String xh) {
        String sql = "SELECT KL FROM WPT_YH WHERE ZH=?";
        Record re = Db.findFirst(sql, xh);
        if (re != null) {
            String kl = re.getStr("KL");
            if (validateOld.equals(kl)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}

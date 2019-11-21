package gka.controller.xxts.oa;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class OaDao {

    public void upYd(String id, String zh) {
        String sql = "UPDATE WPT_OA_XX SET V_STATUS=? WHERE XXID=? AND JSR=?";
        Db.update(sql, "1", id, zh);
    }

    public Record xx(String id, String zh) {
        String sql = "SELECT CONTENT,TIME FROM WPT_OA_XX WHERE XXID=? AND JSR=? AND V_STATUS=?";
        Record re = Db.findFirst(sql, id, zh, "1");
        return re;

    }

}

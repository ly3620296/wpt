package gka.controller.xxts.tsgh.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class TsjyDao {

    public void upYd(String id, String sfzh) {
        String sql = "UPDATE T_TSGH_USER_LOG SET V_STATUS=? WHERE ID=? AND SFZH=?";
        Db.update(sql, "1", id, sfzh);
    }

    public Record xx(String id, String sfzh) {
        String sql = "SELECT *  FROM T_TSGH_USER_LOG WHERE ID=? AND SFZH=? AND V_STATUS=?";
        Record re = Db.findFirst(sql, id, sfzh, "1");
        return re;

    }

}

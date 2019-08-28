package gka.controller.xxts.ttk.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * Created by Administrator on 2019/7/20 0020.
 */
public class TtkDao {

    public List<Record> ttkxx(String zh, String sfzh) {
        String sql = "SELECT KCMC,TTKXX_ID,V_STATUS,TO_CHAR(D_TIPS_DATE,'yyyy/mm/dd hh24:mi:ss') D_TIPS_DATE,V_TYPE FROM T_TTK_USER_LOG WHERE V_XH=?  UNION ALL " +
                "SELECT ZTM,ID,V_STATUS,TO_CHAR(D_TIPS_DATE,'yyyy/mm/dd hh24:mi:ss') D_TIPS_DATE,V_TYPE FROM T_TSGH_USER_LOG WHERE  SFZH=? ORDER BY D_TIPS_DATE DESC";
        List<Record> list = Db.find(sql, zh, sfzh);
        return list;

    }

    public void upYd(String ttkxxId, String zh) {
        String sql = "UPDATE T_TTK_USER_LOG SET V_STATUS=? WHERE TTKXX_ID=? AND V_XH=?";
        Db.update(sql, "1", ttkxxId, zh);
    }

    public Record xx(String ttkxxId, String zh) {
        String sql = "SELECT *  FROM T_TTK_USER_LOG WHERE TTKXX_ID=? AND V_XH=? AND V_STATUS=?";
        Record re = Db.findFirst(sql, ttkxxId, zh, "1");
        return re;

    }

}

package gka.controller.xsjfgl.ddcx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class DdcxDao {
    public Page<Record> getOrderInfo(int page, int limit, String xh) {
        String selectSql = "SELECT T1.ORDER_NO,T1.SFXN, to_char(to_date(T1.TIME_START,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') TIME_START,T1.TOTAL_FEE,NVL(T1.TOTAL_FEE_CALLBACK,0) TOTAL_FEE_CALLBACK,T1.ORDER_STATE,T2.ZYMC,T2.JGMC,T2.BJMC,T2.XM,T2.ZH,T2.ZJHM ";
        String fromSql = " FROM WPT_WXZF_SPECIAL_ORDER T1 LEFT JOIN  WPT_YH T2 ON T1.XH=T2.ZH WHERE T2.ZH=? ORDER BY T1.TIME_START DESC";
        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql, xh);
        return paginate;
    }

    public int searchCount(String xh) {
        String sql = "select count(1) from WPT_WXZF_SPECIAL_ORDER WHERER ZH=?";
        Record re = Db.findFirst(sql, xh);
        return re.getInt("count(1)");
    }
}

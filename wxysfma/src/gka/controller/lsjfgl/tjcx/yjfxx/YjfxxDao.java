package gka.controller.lsjfgl.tjcx.yjfxx;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.lsjfgl.tjcx.xsddcx.XxddcxSearch;

public class YjfxxDao {
    public Page<Record> getOrderInfo(int page, int limit, XxddcxSearch search) {
        String selectSql = "SELECT T1.ORDER_NO,T1.SFXN, to_char(to_date(T1.TIME_START,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') TIME_START,to_char(to_date(T1.TIME_END,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') TIME_END,T1.TOTAL_FEE,NVL(T1.TOTAL_FEE_CALLBACK,0) TOTAL_FEE_CALLBACK,T1.ORDER_STATE,T2.ZYMC,T2.JGMC,T2.BJMC,T2.XM,T2.ZH,T2.ZJHM ";
        String fromSql = " FROM WPT_WXZF_SPECIAL_ORDER T1 LEFT JOIN  WPT_YH T2 ON T1.XH=T2.ZH  WHERE 1=1 ";
        if (!StringUtils.isEmpty(search.getDdbh())) {
            fromSql += " AND T1.ORDER_NO='" + search.getDdbh() + "'";
        }
        if (!StringUtils.isEmpty(search.getXn())) {
            fromSql += " AND T1.SFXN='" + search.getXn() + "'";
        }
        if (!StringUtils.isEmpty(search.getXm())) {
            fromSql += " AND T2.XM like '%" + search.getXm() + "%'";
        }
        if (!StringUtils.isEmpty(search.getDdzt())) {
            fromSql += " AND T1.ORDER_STATE='" + search.getDdzt() + "'";
        }
        if (!StringUtils.isEmpty(search.getXh())) {
            fromSql += " AND T2.ZH='" + search.getXh() + "'";
        }
        if (!StringUtils.isEmpty(search.getSfzh())) {
            fromSql += " AND T2.ZJHM='" + search.getSfzh() + "'";
        }
        if (!StringUtils.isEmpty(search.getDateStart())) {
            fromSql += " AND  to_date(TIME_END,'yyyymmddhh24miss')>=to_date('" + search.getDateStart() + "','yyyy-mm-dd') ";
        }
        if (!StringUtils.isEmpty(search.getDateEnd())) {
            fromSql += " AND  to_date(TIME_END,'yyyymmddhh24miss')<=to_date('" + search.getDateEnd() + "','yyyy-mm-dd')+1 ";
        }
        if (fromSql.contains("to_date")) {
            fromSql += " AND TIME_END IS NOT NULL";
        }
        fromSql += "  ORDER BY T1.TIME_START DESC";

        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql);
        return paginate;
    }


    public Record getInfo(String xh, String xn) {
        String sql = " SELECT * FROM XSSFB  WHERE XN=? AND XH=?";
        Record re = Db.findFirst(sql, xn, xh);
        return re;
    }

    public String getIds(String order_no) {
        String sql = "SELECT IDS FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?";
        Record re = Db.findFirst(sql, order_no);
        return re.getStr("IDS");
    }

    public Record successOrderInfo(String order_no) {
        String sql = "SELECT IDS,TOTAL_FEE_CALLBACK, to_char(to_date(TIME_END,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') TIME_END FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?";
        Record re = Db.findFirst(sql, order_no);
        return re;
    }

    public Record userInfo(String zh) {
        String sql = " SELECT XM,ZJHM,BJMC,ZYMC,JGMC,NJMC FROM WPT_YH WHERE ZH=?";
        Record re = Db.findFirst(sql, zh);
        return re;
    }
}

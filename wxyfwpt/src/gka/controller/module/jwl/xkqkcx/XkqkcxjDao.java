package gka.controller.module.jwl.xkqkcx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class XkqkcxjDao {

    public List<Record> xkqkcx(String jgh, String currXnxq) {
        String sql = "SELECT JXBMC,KCMC,JXBRS,KCXZ,JXB_ID FROM V_WPT_JSKB   WHERE XNXQ=? AND JGH=?";
        List<Record> xkqkcx = Db.find(sql, currXnxq, jgh);
        return xkqkcx;
    }

    public List<Record> xnxq(String jgh) {
        String sql = "SELECT DISTINCT(XNXQ) XNXQ FROM V_WPT_JSKB WHERE JGH=? ORDER BY XNXQ";
        List<Record> xnxq = Db.find(sql, jgh);
        return xnxq;
    }

    public List<Record> xkxs(String jxb_id) {
        String sql = "SELECT T1.CXBJ,T1.XH,T2.BJMC,T2.LXDH,T2.XM FROM V_WPT_XSXKB T1 LEFT JOIN WPT_YH T2 ON T1.XH=T2.ZH WHERE T1.JXB_ID=?";
        List<Record> xkxs = Db.find(sql, jxb_id);
        return xkxs;
    }

}

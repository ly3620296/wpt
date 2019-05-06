package gka.controller.module.jwl.jxjh;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class JxjhDao {

    public List<Record> jxjh(String nj, String zydm, String zyfxdm, String xnxq) {
        String sql = "";
        List<Record> jxjh = null;
        if (zyfxdm.equals("wfx") || zyfxdm.equals("")) {
            sql = "SELECT KCMC,KCXF,KCXS,KCXZ,KHFS,KSFS,KSXS,KCLB,KKBM FROM V_WPT_JXZXJH WHERE NJ=? AND DLZYDM=? AND XNXQ=? AND (ZYFXDM IS NULL OR ZYFXDM=?)";
            jxjh = Db.find(sql, nj, zydm, xnxq, "wfx");
        } else {
            sql = "SELECT  KCMC,KCXF,KCXS,KCXZ,KHFS,KSFS,KSXS,KCLB,KKBM FROM V_WPT_JXZXJH WHERE NJ=? AND DLZYDM=? AND XNXQ=? AND (ZYFXDM IS NULL OR ZYFXDM IN(?,?))";
            jxjh = Db.find(sql, nj, zydm, xnxq, zyfxdm, "wfx");
        }
        return jxjh;
    }

    public List<Record> xnxq(String nj, String dlzydm) {
        String sql = "SELECT DISTINCT(XNXQ) XNXQ FROM V_WPT_JXZXJH WHERE NJ=? AND DLZYDM=? ORDER BY XNXQ";
        List<Record> xnxq = Db.find(sql, nj, dlzydm);
        return xnxq;
    }

}

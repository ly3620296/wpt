package gka.controller.module.jwl.ccj;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class CcjDao {

    public List<Record> ccj(String xh, String currXnxq) {
        String sql = "SELECT KCMC,XF,ZPCJ,CJXZ,KCH,KCXZ,CJBZ FROM V_WPT_CJCX WHERE XH=? AND XNXQ=?";
        List<Record> cjs = Db.find(sql, xh, currXnxq);
        return cjs;
    }

    public List<Record> xnxq(String xh) {
        String sql = "SELECT DISTINCT(XNXQ) FROM V_WPT_CJCX WHERE XH=?";
        List<Record> xnxq = Db.find(sql, xh);
        return xnxq;
    }

}

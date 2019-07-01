package gka.controller.module.xg.jpjgcx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class JpjgcxDao {

    public List<Record> jpjgcx(String xh) {
        String sql = "SELECT  XNXQ,XMMC,XMJE,LXMC FROM V_WPT_PJJGCX T WHERE XH=?";
        List<Record> cjs = Db.find(sql, xh);
        return cjs;
    }

}

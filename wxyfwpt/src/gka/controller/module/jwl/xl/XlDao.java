package gka.controller.module.jwl.xl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class XlDao {
    public List<Record> queryXl(String xnxq) {
        String sql = "SELECT ZC,XQ,RQ FROM V_WPT_XL T  WHERE XNXQ=? ORDER BY RQ ";
        List<Record> list = Db.find(sql, xnxq);
        return list;
    }
}

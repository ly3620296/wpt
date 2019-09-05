package gka.controller.module.wstyzf.xzf;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/9/4
 * @Describe
 */
public class XzfDao {

    public List<Record> xzf(String xh) {
        String sql = "SELECT T1.ID,T1.XH,T1.SFJF,T1.XNXQ,T2.XMMC,T2.XMJE,T2.SFBX FROM WPT_YSFY T1 LEFT JOIN WPT_JFXM T2 " +
                "ON T1.XMID = T2.XMID  WHERE T1.XH=? AND T2.XMLXID=? ORDER BY T1.XNXQ DESC";
        List<Record> sfzhList = Db.find(sql, xh, "20001");
        return sfzhList;
    }

    public List<Record> xzfXnxq(String xh) {
        String sql = "SELECT DISTINCT(T1.XNXQ) FROM WPT_YSFY T1 LEFT JOIN WPT_JFXM T2 " +
                "ON T1.XMID = T2.XMID  WHERE T1.XH=? AND T2.XMLXID=? ORDER BY T1.XNXQ DESC";
        List<Record> xzfXnxq = Db.find(sql, xh, "20001");
        return xzfXnxq;
    }

}

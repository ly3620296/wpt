package gka.controller.xxts.gg;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class GgDao {

    public List<Record> ggList(String xy, String role) {
        String sql = "SELECT G_ID,TO_CHAR(G_TIME,'yyyy/mm/dd hh24:mi:ss') GTIME,G_TITLE,G_TEXT FROM WPTMA_GG WHERE G_STATE=? AND  (G_XY=? OR G_XY=?) AND (G_GROUP=? OR G_GROUP=?)  ORDER BY G_TIME DESC";
        List<Record> ggList = Db.find(sql, "1", "0", xy, "00", role);
        return ggList;
    }

    public List<Record> ggListMain(String xy, String role) {
        String sql = "SELECT * FROM (SELECT G_ID,TO_CHAR(G_TIME,'yyyy/mm/dd hh24:mi:ss') GTIME,G_TITLE,G_TEXT FROM WPTMA_GG WHERE G_STATE=? AND  (G_XY=? OR G_XY=?) AND (G_GROUP=? OR G_GROUP=?)  ORDER BY G_TIME DESC) WHERE ROWNUM<4";
        List<Record> ggList = Db.find(sql, "1", "0", xy, "00", role);
        return ggList;
    }
    public Record detail(String id) {
        String sql = "SELECT T2.X_NAME X_NAME,T1.* FROM WPTMA_GG T1 LEFT JOIN WPTMA_XYGL T2 ON  T1.G_XY= T2.X_CODE WHERE G_ID=? ";
        Record re = Db.findFirst(sql, id);
        return re;
    }
}

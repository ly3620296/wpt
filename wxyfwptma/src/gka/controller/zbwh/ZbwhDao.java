package gka.controller.zbwh;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class ZbwhDao {
    public static List<Record> list(Integer start, Integer end, String u_id) {
        String sql = "SELECT id,bt,TIME FROM (SELECT ROWNUM AS rowno,t.*  FROM wptma_zbbtb t WHERE ROWNUM <= ?) a WHERE a.rowno >= ?";
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount(String u_id) {
        String sql = "select count(1) from wptma_zbbtb";
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }
}

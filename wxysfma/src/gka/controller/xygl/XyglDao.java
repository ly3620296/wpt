package gka.controller.xygl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class XyglDao {

    public static List<Record> list(Integer start, Integer end, String u_id) {
        String sql = "SELECT X_ID,X_NAME,X_CODE FROM (SELECT ROWNUM AS rowno,t.*  FROM wptma_xygl t WHERE ROWNUM <= ?) a WHERE a.rowno >= ?";
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount(String u_id) {
        String sql = "select count(1) from wptma_xygl";
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

    public static Integer add(String x_name, String x_code) {
        String sql = "insert into wptma_xygl (x_id,x_name,x_code) values(SEQ_WPTMA_XYGL.NEXTVAL,?,?)";
        int i = Db.update(sql, x_name, x_code);
        return i;
    }
    public static Integer edit(String x_id, String x_name, String x_code) {
        String sql = "update wptma_xygl set x_name=?,x_code=? where x_id=?";
        int i = Db.update(sql, x_name, x_code, x_id);
        return i;
    }


}

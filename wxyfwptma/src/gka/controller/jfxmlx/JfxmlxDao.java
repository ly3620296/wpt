package gka.controller.jfxmlx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class JfxmlxDao {

    public static List<Record> list(Integer start, Integer end, String u_id,String title) {
        String sql = "SELECT XMLXID,XMLXMC FROM (SELECT ROWNUM AS rowno,t.*  FROM wpt_jfxmlx t WHERE ROWNUM <= ?) a WHERE a.rowno >= ?";
        if (title != null && !"".equals(title)) {
            sql += " and XMLXMC like '%" + title + "%'";
        }
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount(String u_id, String title) {
        String sql = "select count(1) from wpt_jfxmlx";
        if (title != null && !"".equals(title)) {
            sql += " where XMLXMC like '%" + title + "%'";
        }
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

    public static Integer add(String xmlxmc) {
        String sql = "insert into wpt_jfxmlx (xmlxid,xmlxmc) values(SEQ_wpt_jfxmlx.NEXTVAL,?)";
        int i = Db.update(sql, xmlxmc);
        return i;
    }

    public static Integer edit(String xmlxid, String xmlxmc) {
        String sql = "update wpt_jfxmlx set xmlxmc=? where xmlxid=?";
        int i = Db.update(sql, xmlxmc, xmlxid);
        return i;
    }


}

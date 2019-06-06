package gka.controller.notice;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class NoticeDao {

    public static List<Record> list(Integer start, Integer end, String u_id) {
        String sql = "SELECT m_name,g_id,g_title,g_text,DECODE(g_state,'1','在线','下线') as g_state FROM (SELECT ROWNUM AS rowno,d.m_name, t.*  FROM wptma_gg t,wptma_user d WHERE ROWNUM <= ? and d.m_id=t.g_uid) a WHERE a.rowno >= ?";
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount(String u_id) {
        String sql = "select count(1) from WPTMA_GG";
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

    public static Integer add(String g_title, String g_uid, String g_text, String g_state) {
        String sql = "insert into wptma_gg (g_id,g_title,g_uid,g_text,g_xy,g_state) values(SEQ_WPTMA_GG.NEXTVAL,?,?,?,'1',?)";
        int i = Db.update(sql, g_title, g_uid, g_text, g_state);
        return i;
    }
    public static Integer edit(String g_title, String g_uid, String g_text, String g_state, String g_id) {
        String sql = "update wptma_gg set g_title=?,g_text=?,g_state=? where g_id=?";
        int i = Db.update(sql, g_title, g_text, g_state,g_id);
        return i;
    }


}

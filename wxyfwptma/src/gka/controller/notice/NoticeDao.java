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

    public static List<Record> list(Integer start, Integer end, String u_id, String title, String time) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT m_name,g_id,g_title,g_text,DECODE(g_state,'1','在线','下线') as g_state FROM ");
        sb.append("(SELECT ROWNUM AS rowno,d.m_name, t.*  FROM wptma_gg t,wptma_user d WHERE ROWNUM <= ? and d.m_id=t.g_uid) a WHERE a.rowno >= ?");
        if (title != null && !"".equals(title)) {
            sb.append("and a.g_title like '%" + title + "%'");
        }
        if (time != null && !"".equals(time)) {
            sb.append("and to_char(a.g_time,'yyyy-MM-dd')='" + time + "'");
        }
        String sql = "SELECT m_name,g_id,g_title,g_text,DECODE(g_state,'1','在线','下线') as g_state FROM (SELECT ROWNUM AS rowno,d.m_name, t.*  FROM wptma_gg t,wptma_user d WHERE ROWNUM <= ? and d.m_id=t.g_uid) a WHERE a.rowno >= ?";
        List<Record> re = Db.find(sb.toString(), end, start);
        return re;
    }

    public static Integer searchCount(String u_id, String title, String time) {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(1) from WPTMA_GG");
        if (title != null && !"".equals(title)) {
            sb.append(" where g_title like '%" + title + "%'");
            if (time != null && !"".equals(time)) {
                sb.append(" and to_char(g_time,'yyyy-MM-dd')='" + time + "'");
            }
        } else {
            if (time != null && !"".equals(time)) {
                sb.append(" where to_char(g_time,'yyyy-MM-dd')='" + time + "'");
            }
        }
        Record re = Db.findFirst(sb.toString());
        return re.getInt("count(1)");
    }

    public static Integer add(String g_title, String g_uid, String g_text, String g_state, String g_xy) {
        String sql = "insert into wptma_gg (g_id,g_title,g_uid,g_text,g_xy,g_state) values(SEQ_WPTMA_GG.NEXTVAL,?,?,?,?,?)";
        int i = Db.update(sql, g_title, g_uid, g_text, g_xy, g_state);
        return i;
    }

    public static Integer edit(String g_title, String g_uid, String g_text, String g_state, String g_id, String g_xy) {
        String sql = "update wptma_gg set g_title=?,g_text=?,g_state=?,g_xy=? where g_id=?";
        int i = Db.update(sql, g_title, g_text, g_state, g_xy, g_id);
        return i;
    }


}

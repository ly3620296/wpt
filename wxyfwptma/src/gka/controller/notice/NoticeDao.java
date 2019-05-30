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
        String sql="SELECT m_name,g_id,g_title,g_text FROM (SELECT ROWNUM AS rowno,d.m_name, t.*  FROM wptma_gg t,wptma_user d WHERE ROWNUM <= ? and d.m_id=t.g_uid) a WHERE a.rowno >= ?";
        List<Record> re = Db.find(sql,end ,start);
        return re;
    }

    public static Integer searchCount(String u_id) {
        String sql = "select count(1) from WPTMA_GG";
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

}

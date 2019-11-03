package gka.controller.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class UserDao {

    public static List<Record> list(Integer start, Integer end, String u_id) {
        String sql = "SELECT m_id,m_name,m_zh,m_qx FROM (SELECT ROWNUM AS rowno,t.*  FROM wptma_user t WHERE ROWNUM <= ?) a WHERE a.rowno >= ?";
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount(String u_id) {
        String sql = "select count(1) from WPTMA_USER";
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

    public static Integer add(String m_name, String m_zh, String m_qx) {
        String sql = "insert into wptma_user (m_id,m_name,m_zh,m_mm,m_qx) values(SEQ_WPTMA_USER.NEXTVAL,?,?,'123456',?)";
        int i = Db.update(sql, m_name, m_zh, m_qx);
        return i;
    }
    public static Integer edit(String m_id, String m_name, String m_qx) {
        String sql = "update wptma_user set m_name=?,m_qx=? where m_id=?";
        int i = Db.update(sql, m_name, m_qx, m_id);
        return i;
    }


}

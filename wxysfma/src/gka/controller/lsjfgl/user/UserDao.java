package gka.controller.lsjfgl.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class UserDao {

    public static List<Record> list(Integer start, Integer end) {
        String sql = "SELECT m_id,m_name,m_zh,m_qx FROM (SELECT ROWNUM AS rowno,t.*  FROM sfma_user t WHERE ROWNUM <= ?) a WHERE a.rowno >= ?";
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount() {
        String sql = "select count(1) from sfma_user";
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

    public static Integer add(String m_name, String m_zh, String m_qx) {
        String sql = "insert into sfma_user (m_id,m_name,m_zh,m_mm,m_qx) values(SEQ_sfma_user.NEXTVAL,?,?,'123456',?)";
        int i = Db.update(sql, m_name, m_zh, m_qx);
        return i;
    }
    public static Integer edit(String m_id, String m_name, String m_qx) {
        String sql = "update sfma_user set m_name=?,m_qx=? where m_id=?";
        int i = Db.update(sql, m_name, m_qx, m_id);
        return i;
    }


}

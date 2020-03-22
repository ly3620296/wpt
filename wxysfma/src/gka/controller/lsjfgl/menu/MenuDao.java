package gka.controller.lsjfgl.menu;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class MenuDao {

    public static List<Record> list(Integer start, Integer end) {
        String sql = "SELECT M_ID,M_LEVEL,M_NAME,M_URL,DECODE(M_STATE,'1','在线','下线') as M_STATE FROM (SELECT ROWNUM AS rowno,t.*  FROM sfma_menu t WHERE ROWNUM <= ?) a WHERE a.rowno >= ?";
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount() {
        String sql = "select count(1) from sfma_menu";
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

    public static Integer add(String m_level, String m_name, String m_url, String m_state, String m_father) {
        String sql = "insert into sfma_menu (M_ID,M_LEVEL,M_NAME,M_URL,M_STATE,M_FATHER) values(SEQ_SFMA_MENU.NEXTVAL,?,?,?,?,?)";
        int i = Db.update(sql, m_level, m_name, m_url, m_state, m_father);
        return i;
    }

    public static Integer edit(String m_id, String m_level, String m_name, String m_url, String m_state, String m_father) {
        int i = 0;
        if (m_level.equals("1")) {
            String sql = "update sfma_menu set m_name=?,m_state=? where m_id=?";
            i = Db.update(sql, m_name, m_state, m_id);
        } else {
            String sql = "update sfma_menu set m_name=?,m_url=?,m_state=?,m_father=? where m_id=?";
            i = Db.update(sql, m_name, m_url, m_state, m_father, m_id);
        }
        return i;
    }


}

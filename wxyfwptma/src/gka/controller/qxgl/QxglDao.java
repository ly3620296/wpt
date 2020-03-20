package gka.controller.qxgl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class QxglDao {

    public static List<Record> list(Integer start, Integer end, String u_id) {
        String sql = "SELECT q_id,q_name,q_qx,q_ms FROM (SELECT ROWNUM AS rowno,t.*  FROM wptma_qxgl t WHERE ROWNUM <= ?) a WHERE a.rowno >= ?";
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount(String u_id) {
        String sql = "select count(1) from WPTMA_qxgl";
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

    public static Integer add(String q_name, String q_qx, String q_ms) {
        String sql = "insert into wptma_qxgl (q_id,q_name,q_qx,q_ms) values(SEQ_wptma_qxgl.NEXTVAL,?,?,?)";
        int i = Db.update(sql, q_name, q_qx, q_ms);
        return i;
    }

    public static Integer edit(String m_id, String m_name, String m_qx) {
        String sql = "update wptma_user set m_name=?,m_qx=? where m_id=?";
        int i = Db.update(sql, m_name, m_qx, m_id);
        return i;
    }

    public static String createMenuTree() throws Exception {
        StringBuffer str = new StringBuffer();
        List<Record> allMenu = Db.find("select m_id,m_name from wptma_menu where m_level='1' and m_state='1' and m_id in(select distinct(m_father) from wptma_menu where m_level='2' and m_state='1')");
        if (allMenu.size() > 0) {
            str.append("[{");
            for (int i = 0; i < allMenu.size(); i++) {
                Record re = allMenu.get(i);
                String m_id = re.get("m_id") == null ? "" : re.get("m_id").toString();
                String m_name = re.get("m_name") == null ? "" : re.get("m_name").toString();
                str.append("title:'" + m_name + "'");
                str.append(",id:'" + Integer.parseInt(m_id) + "'");
                str.append(",spread:true");
                List<Record> children = Db.find("select m_id,m_name from wptma_menu where m_level='2' and m_father=? and m_state='1'", m_id);
                if (children.size() > 0) {
                    str.append(",children:[{");
                    for (int a = 0; a < children.size(); a++) {
                        Record childrenRe = children.get(a);
                        String children_m_id = childrenRe.get("m_id") == null ? "" : childrenRe.get("m_id").toString();
                        String children_m_name = childrenRe.get("m_name") == null ? "" : childrenRe.get("m_name").toString();
                        str.append("title:'" + children_m_name + "'");
                        str.append(",id:'" + Integer.parseInt(children_m_id) + "'");
                        if ((a + 1) == children.size()) {
                            str.append("}]");
                        } else {
                            str.append("},{");
                        }
                    }
                }
                if ((i + 1) == allMenu.size()) {
                    str.append("}]");
                } else {
                    str.append("},{");
                }
            }
        } else {
            str.append("");
        }
        return str.toString();
    }
}

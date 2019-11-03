package gka.controller.login;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class LoginDao {

    public Record loginValidate(String account, String password) {
        String sql = "select * from wptma_user where m_zh=? and m_mm=?";
        Record re = Db.findFirst(sql, account, password);
        return re;
    }

    public static String getMenuTree(String id) {
        StringBuffer str = new StringBuffer();
        String sql = "select m.* from wptma_user u,wptma_qxgl q,wptma_jscd j,wptma_menu m where u.m_qx=q.q_id and " +
                "j.q_id=q.q_id and m.m_id = j.m_id and m.m_level='1' and m.m_state='1' and u.m_id=?";
        List<Record> allMenu = Db.find(sql, id);
        if (allMenu.size() > 0) {
            for (int i = 0; i < allMenu.size(); i++) {
                Record re = allMenu.get(i);
                String m_id = re.get("m_id") == null ? "" : re.get("m_id").toString();
                String m_name = re.get("m_name") == null ? "" : re.get("m_name").toString();
                if (i == 0) {
                    str.append("<li class=\"layui-nav-item layui-nav-itemed\"><a class=\"\" href=\"javascript:;\">" +
                            "<i class=\"layui-icon layui-icon-home\"></i><cite>" + m_name + "</cite></a>");
                } else {
                    str.append("<li class=\"layui-nav-item\"><a class=\"\" href=\"javascript:;\">" +
                            "<i class=\"layui-icon layui-icon-home\"></i><cite>" + m_name + "</cite></a>");
                }
                String sqlSon = "select m.* from wptma_user u,wptma_qxgl q,wptma_jscd j,wptma_menu m where u.m_qx=q.q_id and " +
                        "j.q_id=q.q_id and m.m_id = j.m_id and m.m_level='2' and m.m_state='1' and u.m_id=? and m.m_father=?";
                List<Record> children = Db.find(sqlSon, id, m_id);
                if (children.size() > 0) {
                    str.append("<dl class=\"layui-nav-child\">");
                    for (int a = 0; a < children.size(); a++) {
                        Record childrenRe = children.get(a);
                        String data_id=i+""+a;
                        String children_m_url = childrenRe.get("m_url") == null ? "" : childrenRe.get("m_url").toString();
                        String children_m_name = childrenRe.get("m_name") == null ? "" : childrenRe.get("m_name").toString();
                        str.append("<dd><a data-url=\"" + children_m_url + "\" data-id=\"" + data_id + "\" data-title=\"" + children_m_name + "\" data-type=\"tabAdd\">" + children_m_name + "</a></dd>");
                    }
                    str.append("</dl>");
                }
                str.append("</li>");
            }
        } else {
            str.append("");
        }
        return str.toString();
    }

}

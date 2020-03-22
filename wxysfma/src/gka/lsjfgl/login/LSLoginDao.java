package gka.lsjfgl.login;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class LSLoginDao {

    public Record loginValidate(String account, String password) {
//        password = getMd5Pass(password);
        String sql = "select * from sfma_user where m_zh=? and m_mm=?";
        Record re = Db.findFirst(sql, account, password);
        return re;
    }


    public String getMd5Pass(String password) {
        String sql = "SELECT '{MD5}'||utl_raw.cast_to_varchar2(utl_encode.base64_encode(Utl_Raw.Cast_To_Raw(sys.dbms_obfuscation_toolkit.md5(input_string => '" + password + "')))) mm FROM DUAL WHERE 1=1";
        Record first = Db.findFirst(sql);
        return first.getStr("MM");
    }
    public static void setSession(Record record, HttpSession session) {
        WptMaLSUserInfo userInfo = new WptMaLSUserInfo();
        userInfo.setM_id(record.getStr("m_id") == null ? "" : record.getStr("m_id"));
        userInfo.setM_name(record.getStr("m_name") == null ? "" : record.getStr("m_name"));
        userInfo.setM_zh(record.getStr("m_zh") == null ? "" : record.getStr("m_zh"));
        userInfo.setM_mm(record.getStr("m_mm") == null ? "" : record.getStr("m_mm"));
        userInfo.setM_qx(record.getStr("m_qx") == null ? "" : record.getStr("m_qx"));
        userInfo.setMenu(getMenuTree(userInfo.getM_id()));
        session.setAttribute("wptMaLSUserInfo", userInfo);
    }
    public static String getMenuTree(String id) {
        StringBuffer str = new StringBuffer();
        String sql = "select m.* from sfma_user u,sfma_qxgl q,sfma_jscd j,sfma_menu m where u.m_qx=q.q_id and " +
                "j.q_id=q.q_id and m.m_id = j.m_id and m.m_level='1' and m.m_state='1' and u.m_id=? order by to_number(m.m_id)";
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
                String sqlSon = "select m.* from sfma_user u,sfma_qxgl q,sfma_jscd j,sfma_menu m where u.m_qx=q.q_id and " +
                        "j.q_id=q.q_id and m.m_id = j.m_id and m.m_level='2' and m.m_state='1' and u.m_id=? and m.m_father=? order by to_number(m.m_id)";
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

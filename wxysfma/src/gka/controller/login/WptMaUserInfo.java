package gka.controller.login;

/**
 * 用户基本信息
 */
public class WptMaUserInfo {
    private String m_id;    //id
    private String m_name; //用户名称
    private String m_zh; //账号
    private String m_mm; //密码
    private String m_qx; //用户权限
    private String menu; //用户权限

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_zh() {
        return m_zh;
    }

    public void setM_zh(String m_zh) {
        this.m_zh = m_zh;
    }

    public String getM_mm() {
        return m_mm;
    }

    public void setM_mm(String m_mm) {
        this.m_mm = m_mm;
    }

    public String getM_qx() {
        return m_qx;
    }

    public void setM_qx(String m_qx) {
        this.m_qx = m_qx;
    }
}
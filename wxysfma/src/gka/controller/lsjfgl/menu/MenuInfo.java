package gka.controller.lsjfgl.menu;

/**
 * 用户基本信息
 */
public class MenuInfo {
    private String m_id;        //id
    private String m_level;    //级别
    private String m_name;      //菜单名称
    private String m_url;     //菜单链接
    private String m_state;       //状态1：在线，0下线
    private String m_time;     //录入时间

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getM_level() {
        return m_level;
    }

    public void setM_level(String m_level) {
        this.m_level = m_level;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_url() {
        return m_url;
    }

    public void setM_url(String m_url) {
        this.m_url = m_url;
    }

    public String getM_state() {
        return m_state;
    }

    public void setM_state(String m_state) {
        this.m_state = m_state;
    }

    public String getM_time() {
        return m_time;
    }

    public void setM_time(String m_time) {
        this.m_time = m_time;
    }
}
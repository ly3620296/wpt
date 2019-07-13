package gka.controller.qxgl;

/**
 * 用户基本信息
 */
public class QxglInfo {
    private String q_id;        //用户id
    private String q_name;    //用户名称
    private String q_qx;      //用户账号
    private String q_ms;     //用户密码

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }

    public String getQ_name() {
        return q_name;
    }

    public void setQ_name(String q_name) {
        this.q_name = q_name;
    }

    public String getQ_qx() {
        return q_qx;
    }

    public void setQ_qx(String q_qx) {
        this.q_qx = q_qx;
    }

    public String getQ_ms() {
        return q_ms;
    }

    public void setQ_ms(String q_ms) {
        this.q_ms = q_ms;
    }
}
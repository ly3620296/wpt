package gka.controller.notice;

/**
 * 用户基本信息
 */
public class NoticeInfo {
    private String g_id;        //公告id
    private String g_title;    //公告标题
    private String g_uid;      //创建用户id
    private String g_text;     //公告内容
    private String g_xy;       //学院代码
    private String g_time;     //创建时间

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getG_title() {
        return g_title;
    }

    public void setG_title(String g_title) {
        this.g_title = g_title;
    }

    public String getG_uid() {
        return g_uid;
    }

    public void setG_uid(String g_uid) {
        this.g_uid = g_uid;
    }

    public String getG_text() {
        return g_text;
    }

    public void setG_text(String g_text) {
        this.g_text = g_text;
    }

    public String getG_xy() {
        return g_xy;
    }

    public void setG_xy(String g_xy) {
        this.g_xy = g_xy;
    }

    public String getG_time() {
        return g_time;
    }

    public void setG_time(String g_time) {
        this.g_time = g_time;
    }
}
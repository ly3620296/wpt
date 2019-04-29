package gka.controller.module.my.wdcy;

/**
 * Created by Administrator on 2019/4/29 0029.
 */
public class MenuChild {
    //菜单id
    private String menu_id = "";
    //菜单名称
    private String menu_name = "";
    //菜单地址
    private String menu_url = "";
    //菜单图标
    private String menu_img = "";
    //父菜单id
    private String menu_parId = "";

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }

    public String getMenu_img() {
        return menu_img;
    }

    public void setMenu_img(String menu_img) {
        this.menu_img = menu_img;
    }

    public String getMenu_parId() {
        return menu_parId;
    }

    public void setMenu_parId(String menu_parId) {
        this.menu_parId = menu_parId;
    }

}

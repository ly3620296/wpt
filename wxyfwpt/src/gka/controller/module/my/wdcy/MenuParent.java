package gka.controller.module.my.wdcy;

import java.util.List;

/**
 * Created by Administrator on 2019/4/29 0029.
 */
public class MenuParent {
    //菜单id
    private String menu_id = "";
    //菜单名称
    private String menu_name = "";
    //二级菜单
    private List<MenuChild> menuChildList;

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


    public List<MenuChild> getMenuChildList() {
        return menuChildList;
    }

    public void setMenuChildList(List<MenuChild> menuChildList) {
        this.menuChildList = menuChildList;
    }
}

package gka.controller.module.my.wdcy;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class WdcyDao {
    public List<Record> allMenu(String qx) {
        String sql = "SELECT * FROM wpt_menu WHERE state='1' AND qx in('00',?) order by to_number(type) ,to_number(id) ";
        List<Record> menu = Db.find(sql, qx);
        return menu;
    }

    public List<Record> allParentMenu(String qx) {
        String sql = "SELECT * FROM WPT_MENU WHERE STATE=? AND FATHER=? AND QX IN('00',?) ORDER BY V_LEVEL";
        List<Record> menu = Db.find(sql, "1", "0", qx);
        return menu;
    }

    public List<Record> childMenu(String qx, String parId) {
        String sql = "SELECT * FROM WPT_MENU WHERE STATE=? AND FATHER=? AND QX IN('00',?) ORDER BY V_LEVEL";
        List<Record> menu = Db.find(sql, "1", parId, qx);
        return menu;
    }

    public List<Record> myMenu(String xh) {
        String sql = "select t.* from wpt_menu t,wpt_wdcy a where t.id=a.menuid and xh=? order by a.id";
        List<Record> menu = Db.find(sql, xh);
        return menu;
    }

    public List<Record> myMenuForMain(String xh) {
        String sql = "select t.* from wpt_menu t,wpt_wdcy a where t.id=a.menuid and xh=? and a.state='1' order by a.time";
        List<Record> menu = Db.find(sql, xh);
        return menu;
    }

    public Boolean addMyMenu(String xh, String MenuId) {
        String sql = "insert into wpt_wdcy (xh,menuid) values(?,?)";
        int i = Db.update(sql, xh, MenuId);
        return i > 0 ? true : false;
    }

    public void reMyMenu(String xh, String MenuId) {
        String sql = "delete wpt_wdcy where xh=? and menuid=?";
        Db.update(sql, xh, MenuId);
    }

    public Record checkMyMenu(String xh, String MenuId) {
        String sql = "select * from wpt_wdcy where xh=? and menuid=?";
        Record menu = Db.findFirst(sql, xh, MenuId);
        return menu;
    }

    public List<Record> checkMyMenuSum(String xh) {
        String sql = "select * from wpt_wdcy where xh=?";
        List<Record> menu = Db.find(sql, xh);
        return menu;
    }

    public void finish(String xh, String[] array) {
        String sql = "delete wpt_wdcy where xh=?";
        Db.update(sql, xh);
        for (int i = 0; i < array.length; i++) {
            Db.update("insert into wpt_wdcy (xh,menuid,id) values(?,?,seq_wpt_wdcy.nextval)", xh, array[i]);
        }
    }
}

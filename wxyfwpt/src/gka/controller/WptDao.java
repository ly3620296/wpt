package gka.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class WptDao {

    public Record loginByOpenId(String openId) {
        String sql = "SELECT ZH,XM,XB,MZ,ZZMM,ZJLX,ZJHM,JGDM,JGMC,ZYDM,ZYMC,BJDM,BJMC,NJDM,NJMC,XZ,SFZX,XJZT,LXDH,YX,JSDM,JSMC,ZYFXDM,ZYFXMC,OPENID FROM WPT_YH WHERE OPENID=?";
        Record re = Db.findFirst(sql, openId);
        return re;
    }


    public boolean isBindOpenId(String openId) {
        String sql = "SELECT OPENID FROM WPT_YH WHERE OPENID=?";
        Record re = Db.findFirst(sql, openId);
        return re != null;
    }

    public void unbind(String zh) {
        String sql = "UPDATE WPT_YH SET OPENID='' WHERE ZH=?";
        Db.update(sql, zh);
    }

    public void bindOpenId(String openId, String zh) {
        String sql = "UPDATE WPT_YH SET OPENID=? WHERE ZH=?";
        Db.update(sql, openId, zh);
    }
}

package gka.controller.module.jwl.djkscx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class DjkscxDao {

    public List<Record> djkscj(String xh) {
        String sql = "SELECT DJKSMC,ZHCJ,KSRQ,XNXQ,XMFXCJ1,XMFXCJ2,XMFXCJ3,XMFXCJ4,ZKZH FROM V_WPT_DJKS WHERE XH=?";
        List<Record> cjs = Db.find(sql, xh);
        return cjs;
    }

}

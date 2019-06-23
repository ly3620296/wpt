package gka.controller.module.xg.zhpfcx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class ZhpfcxDao {

    public List<Record> zhpfcx(String xh) {
        String sql = "SELECT XMMC,FS,XNXQ,CPZPM,NJZYPM,BJPM,QXPM,XYPM,NJXYPM FROM V_WPT_ZHPF T WHERE XH=?";
        List<Record> cjs = Db.find(sql, xh);
        return cjs;
    }

}

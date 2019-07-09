package gka.controller.module.cw.sfb;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class SfbDao {

    public List<Record> sfb(String sfzh) {
        String sql = "SELECT XNXQ,XMBH,XMMC,YSJE,SSJE,ZHGXSJ FROM V_WPT_SFB WHERE SFZH=? ORDER BY ZHGXSJ DESC";
        List<Record> sfbList = Db.find(sql, sfzh);
        return sfbList;
    }
}

package gka.controller.module.tsfwl.tsgwz;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class TsgwzDao {

    public List<Record> tsgwz(String sfzh) {
        String sql = "SELECT DZZH,ZTM,WZLB,WZYY,YFJE,SFJE,FKFS,BLSJ,ZHGXSJ FROM V_WPT_TSWZCX WHERE SFZH=?";
        List<Record> sfzhList = Db.find(sql, sfzh);
        return sfzhList;
    }
}

package gka.controller.module.tsfwl.tsjycx;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class TsjycxDao {

    public List<Record> tsjycx(String sfzh) {
        String sql = "SELECT DZZH,TXM,ZTM,JCSK,XHSK,XJCS,LTLX,WXLX,SQH,BB,CBZ,CBRQ,YJS,GHSJ,ZHGXSJ,SFGH FROM V_WPT_TSJYCX WHERE SFZH=? ORDER BY ZHGXSJ";
        List<Record> sfzhList = Db.find(sql, sfzh);
        return sfzhList;
    }
}

package gka.controller.module.zcgl.grzc;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class GrzcDao {

    public List<Record> grzc(String sfzh) {
        String sql = "SELECT MC,XH,GG,CPTS,LYDWH,LYDW,DJ,JE,FLH,CJ,CCH,CGXS,GZRQ,CCRQ,BXQX,GM,GJ,FPH,RYBH,XZ,JFKM,LYR,SBLY,SYFX,JSR,ZJFS,CFDBH,CFDMC FROM V_WPT_ZCGL WHERE SFZH=? ORDER BY GZRQ DESC";
        List<Record> sfbList = Db.find(sql, sfzh);
        return sfbList;
    }
}

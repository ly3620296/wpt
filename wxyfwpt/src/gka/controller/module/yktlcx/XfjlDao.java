package gka.controller.module.yktlcx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class XfjlDao {

    public List<Record> xfjl(String zjhm) {
        String sql = "SELECT * FROM (SELECT SH,JYLX,JYE,YE,JYSJ FROM V_WPT_YKTXFJL WHERE SFZH= ? ORDER BY JYSJ DESC) WHERE ROWNUM < 11";
        List<Record> xfjl = Db.find(sql, zjhm);
        return xfjl;
    }


}

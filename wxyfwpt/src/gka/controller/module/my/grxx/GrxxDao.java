package gka.controller.module.my.grxx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by Administrator on 2019/11/10 0010.
 */
public class GrxxDao {

    public Record ldQuery(String xh) {
        String sql = "SELECT LDMC,QSH,CWH FROM V_WPT_QSCX WHERE XH=?";
        Record record = Db.findFirst(sql, xh);
        return record;
    }
}

package gka.controller.module.xsfwl.bmtxl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class BmtxlDao {

    public List<Record> queryBm() {
        List<Record> list = Db.find("SELECT X_CODE,X_NAME FROM WPTMA_XYGL WHERE X_CODE!='0' ORDER BY X_CODE ASC");
        return list;
    }

    public List<Record> queryYgtxl() {
        List<Record> list = Db.find("SELECT XM,DH FROM WPTMA_YGTXL");
        return list;

    }

    public List<Record> queryYgtxlByBm(String bmCode) {
        List<Record> list = Db.find("SELECT XM,DH FROM WPTMA_YGTXL WHERE XYID=?", bmCode);
        return list;
    }
}

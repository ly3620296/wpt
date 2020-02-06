package gka.controller.lsjfgl.tjcx.dzqk;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.lsjfgl.tjcx.xsddcx.XxddcxSearch;

public class DzqkDao {
    public Page<Record> getOrderInfo(int page, int limit) {
        String selectSql = "select * ";
        String fromSql = " from wptma_dzmx ORDER BY to_number(dzsj) desc";
        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql);
        return paginate;
    }
}

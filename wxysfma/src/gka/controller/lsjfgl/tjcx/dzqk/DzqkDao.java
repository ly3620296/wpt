package gka.controller.lsjfgl.tjcx.dzqk;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class DzqkDao {
    public Page<Record> getOrderInfo(int page, int limit,String dateStart,String dateEnd) {
        String selectSql = "select DZSJ,XX,DZJG,TS,JE,WXTS,WXJE,YCBS ";
        String fromSql = " from wptma_dzmx where 1=1";
        if (!StringUtils.isEmpty(dateStart)) {
            fromSql += " AND  to_date(DZSJ,'yyyy-MM-dd')>=to_date('" + dateStart + "','yyyy-MM-dd') ";
        }
        if (!StringUtils.isEmpty(dateEnd)) {
            fromSql += " AND  to_date(DZSJ,'yyyy-MM-dd')<=to_date('" + dateEnd + "','yyyy-MM-dd') ";
        }
        fromSql+="ORDER BY to_date(dzsj,'yyyy-MM-dd') desc";
        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql);
        return paginate;
    }
}

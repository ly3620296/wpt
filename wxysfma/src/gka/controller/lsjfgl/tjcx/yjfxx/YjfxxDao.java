package gka.controller.lsjfgl.tjcx.yjfxx;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class YjfxxDao {

    public Page<Record> getOrderInfo(List<Record> title, int page, int limit, YjfxxSearch search) {

        String selectSql = "SELECT T1.XN,T1.XH,T1.XM,T1.XB,T1.NJ,T1.XYMC,T1.BJMC,T1.ZYMC,T1.YSHJ," + getSqlYjf(title);
        String fromSql = " FROM XSSFB T1 where 1=1 ";
        if (!StringUtils.isEmpty(search.getNj())) {
            fromSql += " AND T1.NJ='" + search.getNj() + "'";
        }
        if (!StringUtils.isEmpty(search.getXh())) {
            fromSql += " AND T1.XH='" + search.getXh() + "'";
        }
        if (!StringUtils.isEmpty(search.getXm())) {
            fromSql += " AND T1.XM like '%" + search.getXm() + "%'";
        }
        if (!StringUtils.isEmpty(search.getXymc())) {
            fromSql += " AND T1.XYMC like '%" + search.getXymc() + "%'";
        }
        if (!StringUtils.isEmpty(search.getZymc())) {
            fromSql += " AND T1.ZYMC like '%" + search.getZymc() + "%'";
        }
        if (!StringUtils.isEmpty(search.getBjmc())) {
            fromSql += " AND T1.BJMC='" + search.getBjmc() + "'";
        }
        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql);
        return paginate;
    }


    private String getSqlYjf(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);
            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append(jfxmId + ",");
            } else {
                sb.append(jfxmId);
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
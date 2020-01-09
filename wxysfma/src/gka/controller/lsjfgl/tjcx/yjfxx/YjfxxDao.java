package gka.controller.lsjfgl.tjcx.yjfxx;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.lsjfgl.tjcx.xsddcx.XxddcxSearch;

import java.util.List;

public class YjfxxDao {

    public Page<Record> getOrderInfo(List<Record> title, int page, int limit, YjfxxSearch search) {
        String selectSql = "SELECT T1.XN,T1.XH,T1.XM,T1.XB,T1.NJ,T1.XYMC,T1.BJMC,T1.ZYMC," + getSqlWjf(title);
        String fromSql = " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  where 1=1 ";
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


    private String getSqlWjf(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        StringBuffer hj = new StringBuffer("(T1.YSHJ");
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("(NVL(T1." + jfxmId + ",0)-");
                sb.append("NVL(T2." + jfxmId + ",0)) " + jfxmId + ",");
                hj.append("-" + "NVL(T2." + jfxmId + ",0)");
            } else {
                sb.append("(NVL(T1." + jfxmId + ",0)-");
                sb.append("NVL(T2." + jfxmId + ",0)) " + jfxmId + ",");
                hj.append("-" + "NVL(T2." + jfxmId + ",0)) YSHJ");
            }
        }
        sb.append(hj);
        return sb.toString();
    }
}

package gka.controller.lsjfgl.tjcx.qftj;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class QftjDao {

    public Page<Record> getOrderInfo(int page, int limit, QftjSearch search) {
        List<Record> title = queryTitle();
        String selectSql = "SELECT T3.*,(T3.YSHJ-T4.PC_TOTAL) PC_TOTAL ";
        StringBuffer fromSql = new StringBuffer(" FROM (SELECT T1.XN,T1.XH,T1.XM,T1.XB,T1.NJ,T1.XYMC,T1.BJMC,T1.ZYMC,T1.SFZH,");
        fromSql.append(getSqlWjf(title));
        fromSql.append(" FROM XSSFB T1 LEFT JOIN  (");
        fromSql.append(generateYjfSql(title));
        fromSql.append(") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN) T3");
        fromSql.append(" LEFT JOIN (");
        fromSql.append(pc_sql());
        fromSql.append(") T4 ON T3.XN=T4.XN AND T3.XH=T4.XH ");
        fromSql.append(" LEFT JOIN V_WPT_XSJBXXB T5 ON T3.XH=T5.XH");
        fromSql.append(" WHERE (T3.YSHJ-T4.PC_TOTAL)>0 ");
        if (!StringUtils.isEmpty(search.getXn())) {
            fromSql.append(" AND T3.XN='" + search.getXn() + "'");
        }
        if (!StringUtils.isEmpty(search.getNj())) {
            fromSql.append("AND T5.DQSZJ ='" + search.getNj() + "'");
        }
        if (!StringUtils.isEmpty(search.getXh())) {
            fromSql.append(" AND T3.XH='" + search.getXh() + "'");
        }
        if (!StringUtils.isEmpty(search.getXm())) {
            fromSql.append(" AND T3.XM like '%" + search.getXm() + "%'");
        }
        if (!StringUtils.isEmpty(search.getXymc())) {
            fromSql.append(" AND T3.XYMC like '%" + search.getXymc() + "%'");
        }
        if (!StringUtils.isEmpty(search.getZymc())) {
            fromSql.append(" AND T3.ZYMC like '%" + search.getZymc() + "%'");
        }
        if (!StringUtils.isEmpty(search.getBjmc())) {
            fromSql.append(" AND T3.BJMC like '%" + search.getBjmc() + "%'");
        }
        fromSql.append(" ORDER BY T3.XN DESC");
        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql.toString());
        return paginate;
    }


    public List<Record> getOrderInfo(SearchBean searchBean) {
        List<Record> title = queryTitle();
        String selectSql = "SELECT T3.*, (T3.YSHJ-T4.PC_TOTAL) PC_TOTAL ";
        StringBuffer fromSql = new StringBuffer(" FROM (SELECT T1.XN,T1.XH,T1.XM,T1.XB,T1.NJ,T1.XYMC,T1.BJMC,T1.ZYMC,T1.SFZH,");
        fromSql.append(getSqlWjf(title));
        fromSql.append(" FROM XSSFB T1 LEFT JOIN  (");
        fromSql.append(generateYjfSql(title));
        fromSql.append(") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN) T3");
        fromSql.append(" LEFT JOIN (");
        fromSql.append(pc_sql());
        fromSql.append(") T4 ON T3.XN=T4.XN AND T3.XH=T4.XH ");
        fromSql.append(" LEFT JOIN V_WPT_XSJBXXB T5 ON T3.XH=T5.XH");
        fromSql.append(" WHERE (T3.YSHJ-T4.PC_TOTAL)>0 ");
        String xn = searchBean.getXn();
        if (!StringUtils.isEmpty(xn)) {
            fromSql.append(" AND T3.XN='" + xn + "'");
        }
        String xh = searchBean.getXh();
        if (!StringUtils.isEmpty(xh)) {
            fromSql.append(" AND T3.XH='" + xh + "'");
        }
        String xm = searchBean.getXm();
        if (!StringUtils.isEmpty(xm)) {
            fromSql.append(" AND T3.XM LIKE '%" + xm + "%'");
        }
        String xymc = searchBean.getXymc();
        if (!StringUtils.isEmpty(xymc)) {
            fromSql.append(" AND T3.XYMC LIKE '%" + xymc + "%'");
        }
        String zymc = searchBean.getZymc();
        if (!StringUtils.isEmpty(zymc)) {
            fromSql.append(" AND T3.ZYMC LIKE '%" + zymc + "%'");
        }
        String bjmc = searchBean.getBjmc();
        if (!StringUtils.isEmpty(bjmc)) {
            fromSql.append(" AND T3.BJMC LIKE '%" + bjmc + "%'");
        }
        String nj = searchBean.getNj();
        if (!StringUtils.isEmpty(nj)) {
            fromSql.append("AND T5.DQSZJ ='" + nj + "'");
        }
        fromSql.append(" ORDER BY T3.XN DESC");
        List<Record> records = Db.find(selectSql + fromSql.toString());
        return records;
    }

    public List<Record> getOrderInfo() {
        List<Record> title = queryTitle();
        String selectSql = "SELECT T3.*, (T3.YSHJ-T4.PC_TOTAL) PC_TOTAL ";
        StringBuffer fromSql = new StringBuffer(" FROM (SELECT T1.XN,T1.XH,T1.XM,T1.XB,T1.NJ,T1.XYMC,T1.BJMC,T1.ZYMC,T1.SFZH,");
        fromSql.append(getSqlWjf(title));
        fromSql.append(" FROM XSSFB T1 LEFT JOIN  (");
        fromSql.append(generateYjfSql(title));
        fromSql.append(") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN) T3");
        fromSql.append(" LEFT JOIN (");
        fromSql.append(pc_sql());
        fromSql.append(") T4 ON T3.XN=T4.XN AND T3.XH=T4.XH ");
        fromSql.append(" WHERE (T3.YSHJ-T4.PC_TOTAL)>0 ");
        fromSql.append(" ORDER BY T3.XN DESC");
        List<Record> records = Db.find(selectSql + fromSql.toString());
        return records;
    }

    private String getSqlWjf(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("(NVL(T1." + jfxmId + ",0)-");
                sb.append("NVL(T2." + jfxmId + ",0)) " + jfxmId + ",");
            } else {
                sb.append("(NVL(T1." + jfxmId + ",0)-");
                sb.append("NVL(T2." + jfxmId + ",0)) " + jfxmId + ",");
                sb.append("(NVL(T1.YSHJ,0)-NVL(T2.SSHJ,0)) YSHJ");
            }
        }
        return sb.toString();
    }


    /**
     * 生成已缴每个学年总记录查询sql
     */
    private String generateYjfSql(List<Record> titles) {
        StringBuffer sb = new StringBuffer("SELECT XH,XN,");
        for (int i = 0; i < titles.size(); i++) {
            Record re = titles.get(i);
            String jfxmId = re.getStr("JFXMID");

            if (i < titles.size() - 1) {
                sb.append("SUM(" + jfxmId + ") ");
                sb.append(jfxmId);
                sb.append(",");
            } else {
                sb.append("SUM(" + jfxmId + ") ");
                sb.append(jfxmId);
                sb.append(",");
                sb.append("SUM(SSHJ) SSHJ ");
            }
        }
        sb.append("FROM YHSJB  GROUP BY XH,XN");
        return sb.toString();
    }

    public List<Record> queryTitle() {
//        String sql = "SELECT JFXMID,JFXMMC,SFBX FROM JFXMDM WHERE SFQY=? AND JFTJ=? ORDER BY JFXMID";
        String sql = "SELECT JFXMID,JFXMMC,SFBX FROM JFXMDM WHERE JFTJ=? ORDER BY JFXMID";
        List<Record> list = Db.find(sql, "1");
        return list;
    }

    private String pc_sql() {

//        SELECT  k1.XH,k1.XN,
// (NVL(k2.SFXM01,0)-NVL(K1.PC_TOTAL,0)) PC_TOTAL
// FROM XSSFB K2 RIGHT JOIN
//        (SELECT XH,XN,SUM((NVL(SFXM01,0))) PC_TOTAL FROM YHSJB  GROUP BY XH,XN) K1
//        ON K1.XH=K2.XH AND K1.XN=K2.XN
        StringBuffer sb1 = new StringBuffer("SELECT  K2.XH,K2.XN,((");
        StringBuffer sb = new StringBuffer("");


        String sql = "SELECT JFXMID FROM JFXMDM WHERE JFTJ=?";
        List<Record> pcs = Db.find(sql, "0");
        for (int i = 0; i < pcs.size(); i++) {
            if (i < pcs.size() - 1) {
                sb.append("SUM((NVL(" + pcs.get(i).getStr("JFXMID") + ",0)))");
                sb.append("+");
                sb1.append("NVL(k2." + pcs.get(i).getStr("JFXMID") + ",0)");
                sb1.append("+");
            } else {
                sb.append("SUM((NVL(" + pcs.get(i).getStr("JFXMID") + ",0))) PC_TOTAL");
                sb1.append("NVL(k2." + pcs.get(i).getStr("JFXMID") + ",0))-NVL(K1.PC_TOTAL,0)) PC_TOTAL ");
            }
        }
        if (pcs.size() == 0) {
            sb1.append("0)) PC_TOTAL");
            sb.append("0");
        }
        sb1.append(" FROM XSSFB K2 LEFT JOIN(SELECT XH,XN,");
        sb1.append(sb);
        sb1.append(" FROM YHSJB GROUP BY XH,XN ) K1 ON K1.XH=K2.XH AND K1.XN=K2.XN");
        return sb1.toString();
    }
}

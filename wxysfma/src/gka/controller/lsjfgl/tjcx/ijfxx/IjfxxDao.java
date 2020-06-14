package gka.controller.lsjfgl.tjcx.ijfxx;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.grjfxx.MyConstant;
import gka.controller.xsjfgl.wyjf.WyjfDao;

import java.util.ArrayList;
import java.util.List;

public class IjfxxDao {

    public Page<Record> yjfxx(int page, int limit, IjfxxSearch search, List<Record> titles) {
        String selectSql = "SELECT T1.XN,T1.XH,T1.XM,T1.XB,T1.XYMC,T1.ZYMC,T1.BJMC,T1.SSHJ," + generateTitleSql(titles) + ",DDH,TO_CHAR(TO_DATE(XDSJ,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') XDSJF,JFLX," +
                "DECODE(JFLX,'CASH','现金','CARD','刷卡','JSAPI','APP微信','NATIVE','微信扫码','GXZZ','高校转账','yl','银联') PAY_TYPE ";
        String fromSql = " FROM YHSJB T1 LEFT JOIN V_WPT_XSJBXXB T2 ON T1.XH=T2.XH WHERE SFTF=? ";
        if (!StringUtils.isEmpty(search.getSfxn())) {
            fromSql += " AND T1.XN='" + search.getSfxn() + "'";
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
            fromSql += " AND T1.BJMC like '%" + search.getBjmc() + "%'";
        }
        if (!StringUtils.isEmpty(search.getDateStart())) {
            fromSql += " AND to_date(XDSJ,'yyyymmddhh24miss')>=to_date('" + search.getDateStart() + "','yyyy-mm-dd') ";
        }
        if (!StringUtils.isEmpty(search.getDateEnd())) {
            fromSql += " AND to_date(XDSJ,'yyyymmddhh24miss')<=to_date('" + search.getDateEnd() + "','yyyy-mm-dd')+1 ";
        }
        if (!StringUtils.isEmpty(search.getPay_type())) {
            fromSql += " AND JFLX='" + search.getPay_type() + "'";
        }
        if (!StringUtils.isEmpty(search.getNj())) {
            fromSql += "AND T2.DQSZJ ='" + search.getNj() + "'";
        }
        fromSql += "ORDER BY TO_NUMBER(XDSJ) DESC";
        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql, MyConstant.SFTF_JF);
        return paginate;
    }

    public List<Record> yjfxxExport(IjfxxSearch search, List<Record> titles) {
        String selectSql = "SELECT T1.XN,T1.XH,T1.XM,T1.XB,T1.XYMC,T1.ZYMC,T1.BJMC,SSHJ,NJ," + generateTitleSql(titles) + ",DDH,TO_CHAR(TO_DATE(XDSJ,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') XDSJF,JFLX," +
                "DECODE(JFLX,'CASH','现金','CARD','刷卡','JSAPI','APP微信','NATIVE','微信扫码','GXZZ','高校转账','yl','银联') PAY_TYPE ";
        String fromSql = " FROM YHSJB T1 LEFT JOIN V_WPT_XSJBXXB T2 ON T1.XH=T2.XH WHERE SFTF=? ";
        if (!StringUtils.isEmpty(search.getSfxn())) {
            fromSql += " AND T1.XN='" + search.getSfxn() + "'";
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
            fromSql += " AND T1.BJMC like '%" + search.getBjmc() + "%'";
        }
        if (!StringUtils.isEmpty(search.getDateStart())) {
            fromSql += " AND to_date(XDSJ,'yyyymmddhh24miss')>=to_date('" + search.getDateStart() + "','yyyy-mm-dd') ";
        }
        if (!StringUtils.isEmpty(search.getDateEnd())) {
            fromSql += " AND to_date(XDSJ,'yyyymmddhh24miss')<=to_date('" + search.getDateEnd() + "','yyyy-mm-dd')+1 ";
        }
        if (!StringUtils.isEmpty(search.getPay_type())) {
            fromSql += " AND JFLX='" + search.getPay_type() + "'";
        }
        if (!StringUtils.isEmpty(search.getNj())) {
            fromSql += "AND T2.DQSZJ ='" + search.getNj() + "'";
        }
        fromSql += "ORDER BY TO_NUMBER(XDSJ) DESC";
        List<Record> lists = Db.find(selectSql + fromSql, MyConstant.SFTF_JF);
        return lists;
    }

    public List<Record> titles() {
        String sql = "SELECT JFXMID,JFXMMC,SFBX FROM JFXMDM";
        return Db.find(sql);

    }

    private String generateTitleSql(List<Record> titles) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < titles.size(); i++) {

            if (i < titles.size() - 1) {
                sb.append(titles.get(i).getStr("JFXMID"));
                sb.append(",");
            } else {
                sb.append(titles.get(i).getStr("JFXMID"));
            }
        }

        return sb.toString();
    }


}

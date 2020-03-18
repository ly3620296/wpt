package gka.controller.lsjfgl.tjcx.ijfxx;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.wyjf.WyjfDao;

import java.util.ArrayList;
import java.util.List;

public class IjfxxDao {

    public Page<Record> yjfxx(int page, int limit, IjfxxSearch search, List<Record> titles) {
        String selectSql = "SELECT XN,XH,XM,XB,XYMC,ZYMC,BJMC,SSHJ," + generateTitleSql(titles) + ",DDH,TO_CHAR(TO_DATE(XDSJ,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') XDSJF,JFLX," +
                "DECODE(JFLX,'CASH','现金','CARD','刷卡','JSAPI','APP微信','NATIVE','微信扫码','GXZZ','高校转账') PAY_TYPE ";
        String fromSql = " FROM YHSJB  WHERE 1=1 ";
        if (!StringUtils.isEmpty(search.getSfxn())) {
            fromSql += " AND XN='" + search.getSfxn() + "'";
        }
        if (!StringUtils.isEmpty(search.getXh())) {
            fromSql += " AND XH='" + search.getXh() + "'";
        }
        if (!StringUtils.isEmpty(search.getXm())) {
            fromSql += " AND XM like '%" + search.getXm() + "%'";
        }
        if (!StringUtils.isEmpty(search.getXymc())) {
            fromSql += " AND XYMC like '%" + search.getXymc() + "%'";
        }
        if (!StringUtils.isEmpty(search.getZymc())) {
            fromSql += " AND ZYMC like '%" + search.getZymc() + "%'";
        }
        if (!StringUtils.isEmpty(search.getBjmc())) {
            fromSql += " AND BJMC='" + search.getBjmc() + "'";
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
            fromSql += " AND nj='" + search.getNj() + "'";
        }
        fromSql += "ORDER BY TO_NUMBER(XDSJ) DESC";
        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql);
        return paginate;
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

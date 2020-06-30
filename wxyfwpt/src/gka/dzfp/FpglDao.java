package gka.dzfp;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class FpglDao {
    public Page<Record> getFpInfo(int page, int limit, String xh) {
        String selectSql = "SELECT KPRQ,XH,XN,JFXM,JFHJ,EBILLCODE,EBILLNO,CHECKCODE,CODE";
        String fromSql = " FROM WPTMA_CKDZFP  WHERE XH=? ORDER BY KPRQ DESC";
        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql, xh);
        return paginate;
    }

    public int searchCount(String xh) {
        String sql = "select count(1) from WPT_WXZF_SPECIAL_ORDER WHERER ZH=?";
        Record re = Db.findFirst(sql, xh);
        return re.getInt("count(1)");
    }


//    public void insertSendDzfp(FpglBean fpglBean) {
//        String sql = "INSERT INTO WPTMA_CKDZFP(ID,BUSNO,KPRQ,XH,XN,JFXM,JFXMID,JFHJ,RCBW,FPLX) VALUES(SEQ_WPTMA_CKDZFP.NEXTVAL,?,SYSDATE,?,?,?,?,?,?,?)";
//        Db.update(sql, fpglBean.getBUSNO(), fpglBean.getXH(), fpglBean.getXN(), fpglBean.getJFXM(), fpglBean.getJFXMID(), fpglBean.getJFHJ(), fpglBean.getRCBW(), fpglBean.getFPLX());
//    }
    public void insertSendDzfp(FpglBean fpglBean) {
        String sql = "INSERT INTO WPTMA_CKDZFP(ID,BUSNO,KPRQ,XH,XN,JFXM,JFXMID,JFHJ,RCBW,FPLX,CCBW,CODE,MSG,EBILLCODE,EBILLNO,CHECKCODE) VALUES(SEQ_WPTMA_CKDZFP.NEXTVAL,?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Db.update(sql, fpglBean.getBUSNO(), fpglBean.getXH(), fpglBean.getXN(), fpglBean.getJFXM(), fpglBean.getJFXMID(), fpglBean.getJFHJ(), fpglBean.getRCBW(), fpglBean.getFPLX(),
                fpglBean.getCCBW(), fpglBean.getCODE(), fpglBean.getMSG(), fpglBean.getEBILLCODE(), fpglBean.getEBILLNO(), fpglBean.getCHECKCODE());
    }

    public void insertBackDzfp(FpglBean fpglBean) {

    }


    public Record xhInfo(String oderId) {
        String sql = "SELECT XH,PAY_TYPE FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record xhRe = Db.findFirst(sql, oderId);
        Record xsInfo = null;

        if (xhRe != null) {
            sql = "SELECT XM,ZH,JGDM,JGMC,ZYDM,ZYMC,BJDM,BJMC,ZJHM,LXDH,YX FROM WPT_YH WHERE JSDM=? AND ZH=?";
            xsInfo = Db.findFirst(sql, "02", xhRe.getStr("XH"));
            xsInfo.set("PAY_TYPE", xhRe.getStr("PAY_TYPE"));
        }
        return xsInfo;
    }

    public Record sfInfo(String oderId) {
        String sql = "SELECT IDS,PAY_VAL,XH,SFXN,T2.ZYDM FROM WPT_WXZF_SPECIAL_ORDER T1 LEFT JOIN WPT_YH T2 ON T1.XH=T2.ZH WHERE OUT_TRADE_NO=? AND ORDER_STATE=?";
        Record sfRe = Db.findFirst(sql, oderId, "2");
        return sfRe;
    }
}

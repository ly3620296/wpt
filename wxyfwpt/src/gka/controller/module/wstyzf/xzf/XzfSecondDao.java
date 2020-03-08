package gka.controller.module.wstyzf.xzf;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import gka.pay.wxpay.controller.MyWxpayConstant;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/9/4
 * @Describe
 */
public class XzfSecondDao {

    public List<Record> queryTitle() {
        String sql = "SELECT JFXMID,JFXMMC,SFBX FROM JFXMDM ORDER BY JFXMID";
        List<Record> list = Db.find(sql);
        return list;
    }


    public List<Record> queryTotalNormal(String xh) {
        String sql = "SELECT XN,YSHJ,NVL(SFXM01,0) SFXM01,NVL(SFXM02,0) SFXM02,NVL(SFXM03,0) SFXM03,NVL(SFXM04,0) SFXM04," +
                "NVL(SFXM05,0) SFXM05,NVL(SFXM06,0) SFXM06,NVL(SFXM07,0) SFXM07 " +
                "FROM XSSFB WHERE XH =? ORDER BY XN DESC";
        List<Record> list = Db.find(sql, xh);
        return list;
    }


    public Record queryTotalNormalState(String xh, String xn) {
        String sql = "SELECT XN,YSHJ,NVL(SFXM01,0) SFXM01,NVL(SFXM02,0) SFXM02,NVL(SFXM03,0) SFXM03,NVL(SFXM04,0) SFXM04," +
                "NVL(SFXM05,0) SFXM05,NVL(SFXM06,0) SFXM06,NVL(SFXM07,0) SFXM07 " +
                "FROM XSSFB WHERE XH =? AND XN=?";
        Record re = Db.findFirst(sql, xh, xn);
        return re;
    }

    public List<Record> queryTotal(List<Record> title, String xh) {
        String sql = "SELECT T1.XN,T1.YSHJ," + getSql(title) +
                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? ORDER BY T1.XN DESC";
        List<Record> list = Db.find(sql, xh);
        return list;
    }

    private String getSql(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("NVL(T2." + jfxmId + ",0) " + jfxmId + ",");
            } else {
                sb.append("NVL(T2." + jfxmId + ",0) " + jfxmId);
            }
        }
        return sb.toString();
    }

    //查询是否存在未支付订单
    public boolean noPayOrder(String xh) {
        String sql = "SELECT IDS FROM WPT_WXZF_SPECIAL_ORDER WHERE XH=? AND ORDER_STATE=?";
        Record re = Db.findFirst(sql, xh, MyWxpayConstant.ORDER_STATE_NOPAY);
        return re != null;
    }

    //查询是否未缴费
    public boolean validateIsNoPay(String ids, String xn, String zh) {
        String sql = "SELECT " + ids + " FROM YHSJB WHERE XN=? AND XH=?";
        Record re = Db.findFirst(sql, xn, zh);
        boolean isNoPay = true;
        if (re != null) {
            String[] idArr = ids.split(",");
            for (int i = 0; i < idArr.length; i++) {
                if (!re.getStr(idArr[i]).equals("0"))
                    isNoPay = false;
                break;
            }
        }
        return isNoPay;
    }

    //查询未支付订单
    public Object[] queryNoPayOrder(String zh) {
        Object[] arr = null;
        String sql = "SELECT IDS,PREPAY_ID FROM WPT_WXZF_ORDER WHERE XH=? AND ORDER_STATE=?";
        Record re = Db.findFirst(sql, zh, MyWxpayConstant.ORDER_STATE_NOPAY);
        if (re != null) {
            arr = new Object[2];
            String ids = re.getStr("IDS");
            sql = "SELECT  T3.XNXQ,T3.XMMC,T3.XMJE,T4.XMLXMC FROM (SELECT T1.ID,T1.XH,T1.SFJF,T1.XNXQ,T2.XMMC,T2.XMJE,T2.SFBX,T2.XMLXID " +
                    "FROM WPT_YSFY  T1 LEFT JOIN WPT_JFXM T2 " +
                    "ON T1.XMID = T2.XMID  WHERE T1.XH=? AND T2.XMLXID=? AND ID IN(" + ids + ")) T3 LEFT JOIN WPT_JFXMLX T4 " +
                    "ON T3.XMLXID=T4.XMLXID";
            List<Record> list = Db.find(sql, zh, MyWxpayConstant.XMLXID_XZF);
            arr[0] = re.getStr("PREPAY_ID");
            arr[1] = list;
        }
        return arr;
    }


    public Record jf(String xh, String xn, List<Record> title) {
        String sql = "SELECT " + getSqlForJf(title) +
                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN=?";
        Record re = Db.findFirst(sql, xh, xn);
        return re;
    }

    public Record queryJxzf(String xh) {
        Record re = Db.findFirst("SELECT IDS,TOTAL_FEE,PAY_TYPE,PREPAY_ID,SFXN FROM WPT_WXZF_SPECIAL_ORDER WHERE XH=? AND ORDER_STATE=?", xh, MyWxpayConstant.ORDER_STATE_NOPAY);
        return re;
    }

    private String getSqlForJf(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("(NVL(T1." + jfxmId + ",0)-NVL(T2." + jfxmId + ",0)) " + jfxmId + ",");
            } else {
                sb.append("(NVL(T1." + jfxmId + ",0)-NVL(T2." + jfxmId + ",0)) " + jfxmId);
            }
        }
        return sb.toString();
    }

    public String queryOutTradeNo(String prepay_id) {
        String outTradeNo = "";
        String sql = "SELECT OUT_TRADE_NO FROM WPT_WXZF_SPECIAL_ORDER WHERE PREPAY_ID=?";
        Record re = Db.findFirst(sql, prepay_id);
        if (re != null) {
            outTradeNo = re.getStr("OUT_TRADE_NO");
        }
        return outTradeNo;
    }

    public String queryOrderState(String prepay_id) {
        String sql = "SELECT ORDER_STATE FROM WPT_WXZF_SPECIAL_ORDER WHERE PREPAY_ID=?";
        Record re = Db.findFirst(sql, prepay_id);
        return re == null ? "" : re.getStr("ORDER_STATE");
    }

    public  void updateIllegalMoneyOrder(final Map<String, String> repData) {
        Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String sql = "UPDATE WPT_WXZF_SPECIAL_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=?,TRANSACTION_ID=?,TOTAL_FEE_CALLBACK=?,OPENID=? WHERE OUT_TRADE_NO=?";
                int upOrder = Db.update(sql, repData.get("time_end"), MyWxpayConstant.ORDER_STATE_ILLEGALMONEY, MyWxpayConstant.RETURN_CODE_ERROR, MyWxpayConstant.RESULT_CODE_ILLEGALMONEY, repData.get("transaction_id"), repData.get("total_fee"), repData.get("openid"),  repData.get("out_trade_no"));
                int upYsf = updateOrder(repData.get("out_trade_no"));
                return upOrder * upYsf >= 1;
            }
        });
    }


    public void updateNormalOrder(final Map<String, String> repData) {
        Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String sql = "UPDATE WPT_WXZF_SPECIAL_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=?,TRANSACTION_ID=?,TOTAL_FEE_CALLBACK=?,OPENID=? WHERE OUT_TRADE_NO=?";
                int upOrder = Db.update(sql, repData.get("time_end"), MyWxpayConstant.ORDER_STATE_PAY, MyWxpayConstant.RETURN_CODE_SUCCESS, repData.get("result_code"), repData.get("transaction_id"), repData.get("total_fee"), repData.get("openid"), repData.get("out_trade_no"));
                int upYsf = updateOrder(repData.get("out_trade_no"));
                return upOrder * upYsf >= 1;
            }
        });
    }
    public int updateOrder(String out_trade_no) {
        String sql = "SELECT IDS,SFXN,XH FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, out_trade_no);
        int updateStat = 0;
        String ids = "";
        String sfxn = "";
        String xh = "";
        if (re != null) {
            ids = re.getStr("IDS");
            sfxn = re.getStr("SFXN");
            xh = re.getStr("XH");
            sql = "SELECT XN FROM YHSJB WHERE XN=? AND XH=?";
            re = Db.findFirst(sql, sfxn, xh);
            if (re != null) {
                sql = "UPDATE YHSJB " + getSql(ids, sfxn, xh) + " WHERE XH=? AND XN=?";
                updateStat = Db.update(sql, xh, sfxn);
            } else {
                sql = "INSERT INTO YHSJB  SELECT * FROM XSSFB WHERE XH=? AND XN=?";
                Db.update(sql, xh, sfxn);
                sql = "UPDATE YHSJB " + getSqlIns() + " WHERE XH=? AND XN=?";
                Db.update(sql, xh, sfxn);
                sql = "UPDATE YHSJB " + getSql(ids, sfxn, xh) + " WHERE XH=? AND XN=?";
                updateStat = Db.update(sql, xh, sfxn);
            }
        }

        return updateStat;

    }

    private String getSql(String ids, String xn, String xh) {
        StringBuffer sb = null;
        String sql = "SELECT " + ids + " FROM XSSFB WHERE XH=? AND XN=?";
        Record re = Db.findFirst(sql, xh, xn);
        if (re != null) {
            sb = new StringBuffer("SET ");
            String[] idArr = ids.split(",");
            for (int i = 0; i < idArr.length; i++) {
                if (i < idArr.length - 1) {
                    sb.append(idArr[i] + "=" + re.getStr(idArr[i]) + ",");
                } else {
                    sb.append(idArr[i] + "=" + re.getStr(idArr[i]));
                }
            }
        }
        return sb != null ? sb.toString() : null;
    }

    private String getSqlIns() {
        StringBuffer sb = null;
        String sql = "SELECT JFXMID FROM JFXMDM";
        List<Record> list = Db.find(sql);
        if (list != null) {
            sb = new StringBuffer("SET ");
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i).getStr("JFXMID") + "='0.00',");
                } else {
                    sb.append(list.get(i).getStr("JFXMID") + "='0.00'");
                }
            }
        }
        return sb != null ? sb.toString() : null;
    }

}

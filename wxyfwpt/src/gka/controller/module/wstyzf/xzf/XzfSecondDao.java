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
        String sql = "SELECT JFXMID,JFXMMC,SFBX FROM JFXMDM WHERE SFQY=? ORDER BY JFXMID";
        List<Record> list = Db.find(sql, "1");
        return list;
    }


    public List<Record> queryTotalNormal(String xh, List<Record> titles) {
        StringBuffer sql = new StringBuffer("SELECT XN,YSHJ,");
        for (int i = 0; i < titles.size(); i++) {
            if (i < titles.size() - 1) {
                sql.append("NVL(" + titles.get(i).getStr("JFXMID") + ",0) ");
                sql.append(titles.get(i).getStr("JFXMID"));
                sql.append(",");
            } else {
                sql.append("NVL(" + titles.get(i).getStr("JFXMID") + ",0) ");
                sql.append(titles.get(i).getStr("JFXMID"));
            }
        }
        sql.append(" FROM XSSFB WHERE XH =? ORDER BY XN DESC");
        List<Record> list = Db.find(sql.toString(), xh);
        return list;
    }


    public Record queryTotalNormalState(String xh, String xn, List<Record> titles) {
        StringBuffer sql = new StringBuffer("SELECT XN,YSHJ,");
        for (int i = 0; i < titles.size(); i++) {
            if (i < titles.size() - 1) {
                sql.append("NVL(" + titles.get(i).getStr("JFXMID") + ",0) ");
                sql.append(titles.get(i).getStr("JFXMID"));
                sql.append(",");
            } else {
                sql.append("NVL(" + titles.get(i).getStr("JFXMID") + ",0) ");
                sql.append(titles.get(i).getStr("JFXMID"));
            }
        }
        sql.append(" FROM XSSFB WHERE XH =? AND XN=?");
        Record re = Db.findFirst(sql.toString(), xh, xn);
        return re;
    }

    public List<Record> queryTotal(List<Record> title, String xh) {
//        String sql = "SELECT T1.XN,T1.YSHJ," + getSql(title) +
//                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? ORDER BY T1.XN DESC";
        String sql = generateYjfSql(title);
        List<Record> list = Db.find(sql, xh, xh);
        return list;
    }

    private String generateYjfSql(List<Record> titles) {

//        SELECT T2.XN, T2.XH, T1. * FROM(
//                SELECT XH, XN, SUM(SFXM01)SFXM01, SUM(SFXM02)SFXM02, SUM(SFXM03)SFXM03, SUM(SFXM04)SFXM04,
//                SUM(SFXM05)SFXM05, SUM(SFXM06)SFXM06, SUM(SFXM07)SFXM07, SUM(SSHJ)SSHJ FROM YHSJB WHERE XH = '20183519' GROUP BY XH, XN

        StringBuffer sb = new StringBuffer(" SELECT T2.XN,T2.XH,");
        StringBuffer sb1 = new StringBuffer();
        for (int i = 0; i < titles.size(); i++) {
            Record re = titles.get(i);
            String jfxmId = re.getStr("JFXMID");

            if (i < titles.size() - 1) {
                sb.append("NVL(T1." + jfxmId + ",0) ");
                sb.append(jfxmId);
                sb.append(",");

                sb1.append("SUM(" + jfxmId + ") ");
                sb1.append(jfxmId);
                sb1.append(",");
            } else {
                sb.append("NVL(T1." + jfxmId + ",0) ");
                sb.append(jfxmId);

                sb1.append("SUM(" + jfxmId + ") ");
                sb1.append(jfxmId);
                sb1.append(",");
                sb1.append("SUM(SSHJ) SSHJ ");
            }
        }
        sb.append(" FROM (SELECT XH,XN,");
        sb.append(sb1);
        sb.append(" FROM YHSJB WHERE XH=? GROUP BY XH,XN");
        sb.append(") T1 RIGHT JOIN XSSFB T2 ON T1.XH = T2.XH AND T1.XN = T2.XN WHERE T2.XH = ? ");
        sb.append("ORDER BY T2.XN DESC");
        return sb.toString();
    }

    private String generateYjfSqlSpe(List<Record> titles) {
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
        sb.append("FROM YHSJB WHERE XH=? GROUP BY XH,XN");
        return sb.toString();
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
    public boolean validateIsNoPay(List<Record> titles, String ids, String values, String xn, String xh) {
        Record record = queryTotalWjf(titles, xh, xn);
        boolean flag = true;
        if (record != null) {
            String[] idsArr = ids.split(",");
            String[] valuesArr = values.split(",");
            for (int i = 0; i < idsArr.length; i++) {
                if (Double.parseDouble(record.getStr(idsArr[i])) < Double.parseDouble((valuesArr[i]))) {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = false;
        }
        return flag;
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


    public Record queryJxzf(String xh) {
        Record re = Db.findFirst("SELECT IDS,ORDER_NO,TOTAL_FEE,PAY_TYPE,PREPAY_ID,SFXN FROM WPT_WXZF_SPECIAL_ORDER WHERE XH=? AND ORDER_STATE=?", xh, MyWxpayConstant.ORDER_STATE_NOPAY);
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

    public String queryOutTradeNoyL(String prepay_id) {
        String outTradeNo = "";
        String sql = "SELECT OUT_TRADE_NO FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?";
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

    public String queryOrderStateYl(String prepay_id) {
        String sql = "SELECT ORDER_STATE FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?";
        Record re = Db.findFirst(sql, prepay_id);
        return re == null ? "" : re.getStr("ORDER_STATE");
    }

    public void updateIllegalMoneyOrder(final Map<String, String> repData) {
//        Db.tx(new IAtom() {
//            @Override
//            public boolean run() throws SQLException {
        String sql = "UPDATE WPT_WXZF_SPECIAL_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=?,TRANSACTION_ID=?,TOTAL_FEE_CALLBACK=?,OPENID=?,ISBACK=? WHERE OUT_TRADE_NO=?";
        int upOrder = Db.update(sql, repData.get("time_end"), MyWxpayConstant.ORDER_STATE_ILLEGALMONEY, MyWxpayConstant.RETURN_CODE_ERROR, MyWxpayConstant.RESULT_CODE_ILLEGALMONEY, repData.get("transaction_id"), String.valueOf(Double.parseDouble(repData.get("total_fee")) / 100), repData.get("openid"), "1", repData.get("out_trade_no"));
//                int upYsf = updateOrder(repData.get("out_trade_no"));
//                return upOrder * upYsf >= 1;
//            }
//        });
    }


    public void updateNormalOrder(final Map<String, String> repData) {
        Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String sql = "UPDATE WPT_WXZF_SPECIAL_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=?,TRANSACTION_ID=?,TOTAL_FEE_CALLBACK=?,OPENID=?,ISBACK=? WHERE OUT_TRADE_NO=?";
                int upOrder = Db.update(sql, repData.get("time_end"), MyWxpayConstant.ORDER_STATE_PAY, MyWxpayConstant.RETURN_CODE_SUCCESS, repData.get("result_code"), repData.get("transaction_id"), String.valueOf(Double.parseDouble(repData.get("total_fee")) / 100), repData.get("openid"), "1", repData.get("out_trade_no"));
                int upYsf = updateOrder(repData.get("out_trade_no"), repData.get("trade_type"), repData.get("total_fee"));
                return upOrder * upYsf >= 1;
            }
        });
    }

//    public int updateOrder(String out_trade_no) {
//        String sql = "SELECT IDS,SFXN,XH FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
//        Record re = Db.findFirst(sql, out_trade_no);
//        int updateStat = 0;
//        String ids = "";
//        String sfxn = "";
//        String xh = "";
//        if (re != null) {
//            ids = re.getStr("IDS");
//            sfxn = re.getStr("SFXN");
//            xh = re.getStr("XH");
//            sql = "SELECT XN FROM YHSJB WHERE XN=? AND XH=?";
//            re = Db.findFirst(sql, sfxn, xh);
//            if (re != null) {
//                sql = "UPDATE YHSJB " + getSql(ids, sfxn, xh) + " WHERE XH=? AND XN=?";
//                updateStat = Db.update(sql, xh, sfxn);
//            } else {
//                sql = "INSERT INTO YHSJB  SELECT * FROM XSSFB WHERE XH=? AND XN=?";
//                Db.update(sql, xh, sfxn);
//                sql = "UPDATE YHSJB " + getSqlIns() + " WHERE XH=? AND XN=?";
//                Db.update(sql, xh, sfxn);
//                sql = "UPDATE YHSJB " + getSql(ids, sfxn, xh) + " WHERE XH=? AND XN=?";
//                updateStat = Db.update(sql, xh, sfxn);
//            }
//        }
//
//        return updateStat;
//
//    }

    public static String preMoney(String orderNo) {
        String preMoney = "";
        String sql = "SELECT TOTAL_FEE FROM  WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, orderNo);
        if (re != null) {
            preMoney = re.getStr("TOTAL_FEE");
        }
        return preMoney;
    }

    /**
     * 修改银行实缴表
     *
     * @param out_trade_no
     * @return
     */
    public int updateOrder(String out_trade_no, String pay_type, String fee) {
        String sql = "SELECT IDS,SFXN,XH,ORDER_NO,TIME_START,PAY_VAL FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, out_trade_no);
        int updateStat = 0;
        String ids = "";
        String sfxn = "";
        String xh = "";
        String ORDER_NO = "";
        String TIME_START = "";
        String pay_val = "";
        if (re != null) {
            ids = re.getStr("IDS");
            pay_val = re.getStr("PAY_VAL");
            sfxn = re.getStr("SFXN");
            xh = re.getStr("XH");
            ORDER_NO = re.getStr("ORDER_NO");
            TIME_START = re.getStr("TIME_START");
            sql = "SELECT XN,XH,XM,XB,BJMC,ZYMC,NJ,XYMC,SFZH FROM XSSFB WHERE XH=? AND XN=?";
            Record userInfo = Db.findFirst(sql, xh, sfxn);
            sql = "SELECT " + ids + "  FROM XSSFB WHERE XH=? AND XN=?";
            Record payInfo = Db.findFirst(sql, xh, sfxn);
            String[] xmids = ids.split(",");
            StringBuffer insVal = new StringBuffer();
//            for (int i = 0; i < xmids.length; i++) {
//                if (i < xmids.length - 1) {
//                    insVal.append(payInfo.getStr(xmids[i]));
//                    insVal.append(",");
//                } else {
//                    insVal.append(payInfo.getStr(xmids[i]));
//                }
//            }
            sql = "INSERT INTO YHSJB (XN,XH,XM,XB,BJMC,ZYMC,NJ,XYMC,SFZH,SSHJ,XDSJ,DDH,JFLX," + ids + ",LSH,CZLX,CZRQ,SFRQ,YH,SFLX) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?," + pay_val + ",?,?,TO_CHAR(SYSDATE,'YYYY-MM-DD hh24:mi:ss'),TO_CHAR(SYSDATE,'YYYY-MM-DD'),?,?)";
            updateStat = Db.update(sql, userInfo.getStr("XN"), userInfo.getStr("XH"), userInfo.getStr("XM"), userInfo.getStr("XB"), userInfo.getStr("BJMC"),
                    userInfo.getStr("ZYMC"), userInfo.getStr("NJ"), userInfo.getStr("XYMC"), userInfo.getStr("SFZH"), String.valueOf(Double.parseDouble(fee) / 100), TIME_START, ORDER_NO, pay_type,
                    out_trade_no, MyWxpayConstant.XSSFB_CZLX_WPT, "", pay_type);
        }

        return updateStat;

    }

    public Record queryTotalWjfByPay(String xh, String xn) {
        List<Record> title = queryTitle();
        String sql = "SELECT * FROM (SELECT T1.XN,T1.XH," + getSqlWjf(title) +
                " FROM XSSFB T1 LEFT JOIN  (" + generateYjfSqlSpe(title) + ") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN=?)" +
                " WHERE YSHJ!='0' AND XH=? AND XN=?";
        System.out.println("===" + sql);
        Record re = Db.findFirst(sql, xh, xh, xn, xh, xn);
        return re;
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


    public Record queryTotalWjf(List<Record> title, String xh, String xn) {
        String sql = "SELECT * FROM (SELECT T1.XN," + getSqlWjf(title) +
                " FROM XSSFB T1 LEFT JOIN  (" + generateYjfSqlDnkp(title) + ") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN=? )" +
                " WHERE YSHJ!='0'";
        Record re = Db.findFirst(sql, xh, xn, xh, xn);
        return re;
    }

    private String generateYjfSqlDnkp(List<Record> titles) {
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
        sb.append("FROM YHSJB WHERE XH=? AND XN=? GROUP BY XH,XN");
        return sb.toString();
    }

    public Record queryXnYjFyxx(List<Record> titles, String xh, String xn) {
        String sql = "SELECT T1.XN," + getSqlWjfLy(titles) +
                " FROM XSSFB T1 LEFT JOIN  (" + generateYjfSqlLy(titles) + ") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN =?";
        Record re = Db.findFirst(sql, xh, xh, xn);
        return re;
    }

//    public Record jf(String xh, String xn, List<Record> title) {
//        String sql = "SELECT " + getSqlForJf(title) +
//                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN=?";
//        Record re = Db.findFirst(sql, xh, xn);
//        return re;
//    }

    public Record jf(String xh, String xn, List<Record> title) {
        String sql = "SELECT " + getSqlForJfLy(title) +
                " FROM XSSFB T1 LEFT JOIN  (" + generateYjfSqlLy(title) + ") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN=?";
        Record re = Db.findFirst(sql, xh, xh, xn);
        return re;

    }

    private String getSqlForJfLy(List<Record> title) {
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


    private String getSqlWjfLy(List<Record> title) {
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

    private String generateYjfSqlLy(List<Record> titles) {
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
        sb.append("FROM YHSJB WHERE XH=? GROUP BY XH,XN");
        return sb.toString();
    }

    public boolean queryIsBack(String out_trade_no) {
        String sql = "SELECT ISBACK FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, out_trade_no);
        if (re == null) {
            return false;
        }
        return re.getStr("ISBACK").equals("0");
    }
}

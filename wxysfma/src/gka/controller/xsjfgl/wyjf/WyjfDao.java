package gka.controller.xsjfgl.wyjf;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.MyUtil;
import gka.controller.xsjfgl.grjfxx.MyConstant;
import gka.pay.wxpay.WXPayConstants;
import gka.pay.wxpay.controller.MyWxpayConstant;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class WyjfDao {

    public List<Record> queryTitle() {
        String sql = "SELECT JFXMID,JFXMMC,SFBX FROM JFXMDM WHERE SFQY=? ORDER BY JFXMID";
        List<Record> list = Db.find(sql, "1");
        return list;
    }


    public boolean queryIsBack(String orderId) {
        String sql = "SELECT ISBACK FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, orderId);
        if (re == null) {
            return false;
        }
        return re.getStr("ISBACK").equals("0");
    }

    public Page<Record> queryTotalPage(List<Record> title, String xh, int pageNum, int pageSize) {
        String selectSql = "SELECT T1.XN,T1.YSHJ," + getSql(title);
        String fromSql = " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? ORDER BY T1.XN";
        Page<Record> paginate = Db.paginate(pageNum, pageSize, selectSql, fromSql, xh);
        return paginate;
    }

    public List<Record> queryTotal(List<Record> title, String xh) {
        String sql = "SELECT T1.XN,T1.YSHJ," + getSql(title) +
                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? ORDER BY T1.XN";
        List<Record> list = Db.find(sql, xh);
        return list;
    }

    public List<Record> queryTotalWjf(List<Record> title, String xh) {
        String sql = "SELECT * FROM (SELECT T1.XN," + getSqlWjf(title) +
                " FROM XSSFB T1 LEFT JOIN  (" + generateYjfSql(title) + ") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =?)" +
                " WHERE YSHJ!='0' ORDER BY XN DESC";
        List<Record> list = Db.find(sql, xh, xh);
        return list;
    }

    public Record queryTotalWjfByPay(String xh, String xn) {
        List<Record> title = queryTitle();
        String sql = "SELECT * FROM (SELECT T1.XN,T1.XH," + getSqlWjf(title) +
                " FROM XSSFB T1 LEFT JOIN  (" + generateYjfSql(title) + ") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN=?)" +
                " WHERE YSHJ!='0' AND XH=? AND XN=?";
        Record re = Db.findFirst(sql, xh, xh, xn, xh, xn);
        return re;
    }

    public Record queryTotalWjf(List<Record> title, String xh, String xn) {
        String sql = "SELECT * FROM (SELECT T1.XN," + getSqlWjf(title) +
                " FROM XSSFB T1 LEFT JOIN  (" + generateYjfSqlDnkp(title) + ") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN=? )" +
                " WHERE YSHJ!='0'";
        Record re = Db.findFirst(sql, xh, xn, xh, xn);
        return re;
    }


    public Record jf(String xh, String xn, List<Record> title) {
        String sql = "SELECT " + getSqlForJf(title) +
                " FROM XSSFB T1 LEFT JOIN  (" + generateYjfSql(title) + ") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN=?";
        Record re = Db.findFirst(sql, xh, xh, xn);
        return re;

    }

    public static String preMoney(String orderNo) {
        String preMoney = "";
        String sql = "SELECT TOTAL_FEE FROM  WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, orderNo);
        if (re != null) {
            preMoney = re.getStr("TOTAL_FEE");
        }
        return preMoney;
    }

    public void updateIllegalMoneyOrder(final Map<String, String> repData) {
//        Db.tx(new IAtom() {
//            @Override
//            public boolean run() throws SQLException {
        String sql = "UPDATE WPT_WXZF_SPECIAL_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=?,TRANSACTION_ID=?,TOTAL_FEE_CALLBACK=?,OPENID=?,ISBACK=? WHERE OUT_TRADE_NO=?";
        int upOrder = Db.update(sql, repData.get("time_end"), MyWxpayConstant.ORDER_STATE_ILLEGALMONEY, MyWxpayConstant.RETURN_CODE_ERROR, MyWxpayConstant.RESULT_CODE_ILLEGALMONEY, repData.get("transaction_id"), String.valueOf(Double.parseDouble(repData.get("total_fee")) / 100), repData.get("openid"), "1", repData.get("out_trade_no"));
//                int upYsf = updateOrder(repData.get("out_trade_no"), repData.get("trade_type"), repData.get("total_fee"));
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
                int upYsf = updateOrder(repData.get("out_trade_no"), repData.get("trade_type"), Double.parseDouble(repData.get("total_fee")) / 100);
                return upOrder * upYsf >= 1;
            }
        });
    }

    public boolean updateNormalOrderYl(final Map<String, String> repData) {
        boolean tx = Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String sql = "UPDATE WPT_WXZF_SPECIAL_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=?,TRANSACTION_ID=?,TOTAL_FEE_CALLBACK=?,PREPAY_ID=?,ISBACK=? WHERE OUT_TRADE_NO=?";
                int upOrder = Db.update(sql, repData.get("time_end"), MyWxpayConstant.ORDER_STATE_PAY, MyWxpayConstant.RETURN_CODE_SUCCESS, "SUCCESS", repData.get("traceNo"), String.valueOf(Double.parseDouble(repData.get("settleAmt")) / 100), repData.get("queryId"), "1", repData.get("orderId"));
                int upYsf = updateOrder(repData.get("orderId"), "yl", Double.parseDouble(repData.get("settleAmt")) / 100);
                return upOrder * upYsf >= 1;
            }
        });
        return tx;
    }

    /**
     * 修改银行实缴表
     *
     * @param out_trade_no
     * @return
     */
    public int updateOrder(String out_trade_no, String pay_type, double fee) {
        int updateStat = 0;
        try {
            String sql = "SELECT IDS,SFXN,XH,ORDER_NO,TIME_START,PAY_VAL FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
            Record re = Db.findFirst(sql, out_trade_no);
            updateStat = 0;
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
                //            sql = "SELECT " + ids + "  FROM XSSFB WHERE XH=? AND XN=?";
                //            Record payInfo = Db.findFirst(sql, xh, sfxn);
                //            String[] xmids = ids.split(",");
                //            StringBuffer insVal = new StringBuffer();
                //            for (int i = 0; i < xmids.length; i++) {
                //                if (i < xmids.length - 1) {
                //                    insVal.append(payInfo.getStr(xmids[i]));
                //                    insVal.append(",");
                //                } else {
                //                    insVal.append(payInfo.getStr(xmids[i]));
                //                }
                //            }
                sql = "INSERT INTO YHSJB (XN,XH,XM,XB,BJMC,ZYMC,NJ,XYMC,SFZH,SSHJ,XDSJ,DDH,JFLX," + ids + ",LSH,CZLX,CZRQ,SFRQ,YH,SFLX) VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?," + pay_val + ",?,?,TO_CHAR(SYSDATE,'YYYY-MM-DD hh24:mi:ss'),TO_CHAR(SYSDATE,'YYYY-MM-DD'),?,?)";
                updateStat = Db.update(sql, userInfo.getStr("XN"), userInfo.getStr("XH"), userInfo.getStr("XM"), userInfo.getStr("XB"), userInfo.getStr("BJMC"),
                        userInfo.getStr("ZYMC"), userInfo.getStr("NJ"), userInfo.getStr("XYMC"), userInfo.getStr("SFZH"), String.valueOf(fee), TIME_START, ORDER_NO, pay_type,
                        out_trade_no, MyWxpayConstant.XSSFB_CZLX_XSHTJF, "", pay_type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateStat;
    }

    public Record xhInfo(String oderId) {
        String sql = "SELECT XH,PAY_TYPE FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record xhRe = Db.findFirst(sql, oderId);
        Record xsInfo = null;

        if (xhRe != null) {
            sql = "SELECT XM,ZH,JGDM,JGMC,ZYDM,ZYMC,BJDM,BJMC,ZJHM,LXDH,YX FROM  WPT_YH WHERE JSDM=? WHERE ZH=?";
            xsInfo = Db.findFirst(sql, "02", xhRe.getStr("XH"));
            xsInfo.set("PAY_TYPE", xhRe.getStr("PAY_TYPE"));
        }
        return xsInfo;
    }

    public Record sfInfo(String oderId) {
        String sql = "SELECT IDS,PAY_VAL FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record sfRe = Db.findFirst(sql, oderId);
        return sfRe;
    }

    public void fillDzfp(String out_trade_no) {
        try {
            String sql = "SELECT IDS,SFXN,XH,ORDER_NO,TIME_START,PAY_VAL FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
            Record re = Db.findFirst(sql, out_trade_no);
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

                //            sql = "SELECT " + ids + "  FROM XSSFB WHERE XH=? AND XN=?";
                //            Record payInfo = Db.findFirst(sql, xh, sfxn);
                //            String[] xmids = ids.split(",");
                //            StringBuffer insVal = new StringBuffer();
                //            for (int i = 0; i < xmids.length; i++) {
                //                if (i < xmids.length - 1) {
                //                    insVal.append(payInfo.getStr(xmids[i]));
                //                    insVal.append(",");
                //                } else {
                //                    insVal.append(payInfo.getStr(xmids[i]));
                //                }
                //            }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String getMoney(String ids, String xn, String zh) {
        String sql = "SELECT " + ids + " FROM XSSFB WHERE XN=? AND XH=?";
        Record re = Db.findFirst(sql, xn, zh);
        int sum = 0;
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            sum += Float.parseFloat(re.getStr(idArr[i]));
        }
        return String.valueOf(sum);
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

    public String queryOrderState(String out_trade_no) {
        String sql = "SELECT ORDER_STATE FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?";
        Record re = Db.findFirst(sql, out_trade_no);
        return re == null ? "" : re.getStr("ORDER_STATE");
    }

    //查询是否存在未支付订单
    public boolean noPayOrder(String xh) {
        String sql = "SELECT IDS FROM WPT_WXZF_SPECIAL_ORDER WHERE XH=? AND ORDER_STATE=?";
        Record re = Db.findFirst(sql, xh, MyWxpayConstant.ORDER_STATE_NOPAY);
        return re != null;
    }

    //查询是否存在未支付订单号
    public String noPayOrderNo(String xh) {
        String sql = "SELECT ORDER_NO FROM WPT_WXZF_SPECIAL_ORDER WHERE XH=? AND ORDER_STATE=?";
        Record re = Db.findFirst(sql, xh, MyWxpayConstant.ORDER_STATE_NOPAY);
        return re != null ? re.getStr("ORDER_NO") : "-1";
    }

    //查询关闭订单参数
    public Record queryOutTradeNo(String order_no) {
        String outTradeNo = "";
        String sql = "SELECT OUT_TRADE_NO,PAY_TYPE FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?";
        Record re = Db.findFirst(sql, order_no);
        return re;
    }

    public NoPayOrderInfo getNoPayOrderInfo(String zh) {
        String sql = "SELECT T1.PAY_TYPE,T1.ORDER_NO,T1.SFXN,T1.TIME_START,T1.TOTAL_FEE,T1.ORDER_STATE,T2.ZYMC,T2.JGMC,T2.BJMC,T2.XM,T2.ZH,T2.ZJHM FROM WPT_WXZF_SPECIAL_ORDER T1 LEFT JOIN  WPT_YH T2 ON T1.XH=T2.ZH WHERE T1.ORDER_STATE=? AND T2.ZH=?";
        Record re = Db.findFirst(sql, MyWxpayConstant.ORDER_STATE_NOPAY, zh);
        NoPayOrderInfo noPayOrderInfo = null;
        if (re != null) {
            noPayOrderInfo = new NoPayOrderInfo();
            noPayOrderInfo.setBjmc(re.getStr("BJMC"));
            noPayOrderInfo.setDdbh(re.getStr("ORDER_NO"));
            noPayOrderInfo.setDdhj(re.getStr("TOTAL_FEE"));
            noPayOrderInfo.setJfxn(re.getStr("SFXN"));
            noPayOrderInfo.setSfzh(re.getStr("ZJHM"));
            noPayOrderInfo.setXdsj(MyUtil.getMyDate(re.getStr("TIME_START")));
            noPayOrderInfo.setXh(re.getStr("ZH"));
            noPayOrderInfo.setXm(re.getStr("XM"));
            noPayOrderInfo.setXymc(re.getStr("JGMC"));
            noPayOrderInfo.setZt(re.getStr("ORDER_STATE").equals(String.valueOf(MyWxpayConstant.ORDER_STATE_NOPAY)) ? "待支付" : "");
            noPayOrderInfo.setZymc(re.getStr("ZYMC"));
            noPayOrderInfo.setPayType(re.getStr("PAY_TYPE"));
        }
        return noPayOrderInfo;
    }


    public Record queryJxzf(String order_no) {
        Record re = Db.findFirst("SELECT IDS,TOTAL_FEE,CODE_URL,ORDER_STATE FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=? AND ORDER_STATE=?", order_no, MyWxpayConstant.ORDER_STATE_NOPAY);
        return re;
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
        sb.append("FROM YHSJB WHERE XH=? GROUP BY XH,XN");
        return sb.toString();
    }

    /**
     * 生成已缴每个学年总记录查询sql
     */
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

    /**
     * 该学年应交费用信息
     *
     * @param titles
     * @param xh
     * @return
     */
    public Record queryXnYjFyxx(List<Record> titles, String xh, String xn) {
        String sql = "SELECT T1.XN," + getSqlWjf(titles) +
                " FROM XSSFB T1 LEFT JOIN  (" + generateYjfSql(titles) + ") T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN =?";
        Record re = Db.findFirst(sql, xh, xh, xn);
        return re;
    }


    /**
     * 已缴费用信息
     */
    public List<Record> queryYjFyxx(List<Record> titles, String xh) {
        String titleSql = parseTitleSql(titles);
        String sql = "SELECT XN,SSHJ," + titleSql + " FROM YHSJB WHERE XH=? AND SFTF=? ORDER BY XN DESC";
        List<Record> records = Db.find(sql, xh, MyConstant.SFTF_JF);
        return records;
    }

    /**
     * 已缴费用信息
     */
    public List<Record> queryYjFyxx(List<Record> titles, String xh, String xn) {
        String titleSql = parseTitleSql(titles);
        String sql = "SELECT TFBS,XH,XDSJ,DDH,DECODE(JFLX,'CASH','现金','CARD','刷卡','JSAPI','APP微信','NATIVE','微信扫码','GXZZ','高校转账','yl','银联') JFLX,XN,SSHJ," + titleSql + " FROM YHSJB WHERE XH=? AND XN=? AND SFTF=? ORDER BY XDSJ DESC";
        List<Record> records = Db.find(sql, xh, xn, MyConstant.SFTF_JF);
        return records;
    }

    private String parseTitleSql(List<Record> titles) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < titles.size(); i++) {
            Record re = titles.get(i);
            if (i < titles.size() - 1) {
                sb.append(re.getStr("JFXMID"));
                sb.append(",");
            } else {
                sb.append(re.getStr("JFXMID"));
            }
        }
        return sb.toString();
    }


    public String queryTotalFee(String order_no) {
        String sql = "SELECT TOTAL_FEE FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?";
        Record re = Db.findFirst(sql, order_no);
        return re.getStr("TOTAL_FEE");
    }
}

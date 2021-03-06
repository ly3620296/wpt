package gka.controller.xsjfgl.wyjf;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.MyUtil;
import gka.pay.wxpay.controller.MyWxpayConstant;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class WyjfDao_bak {

    public List<Record> queryTitle() {
        String sql = "SELECT JFXMID,JFXMMC,SFBX FROM JFXMDM WHERE SFQY=? ORDER BY JFXMID";
        List<Record> list = Db.find(sql, "1");
        return list;
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
        Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String sql = "UPDATE WPT_WXZF_SPECIAL_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=?,TRANSACTION_ID=?,TOTAL_FEE_CALLBACK=?,OPENID=? WHERE OUT_TRADE_NO=?";
                int upOrder = Db.update(sql, repData.get("time_end"), MyWxpayConstant.ORDER_STATE_ILLEGALMONEY, MyWxpayConstant.RETURN_CODE_ERROR, MyWxpayConstant.RESULT_CODE_ILLEGALMONEY, repData.get("transaction_id"), repData.get("total_fee"), repData.get("openid"), repData.get("out_trade_no"));
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

    /**
     * 修改银行实缴表
     *
     * @param out_trade_no
     * @return
     */
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
    public String queryOutTradeNo(String order_no) {
        String outTradeNo = "";
        String sql = "SELECT OUT_TRADE_NO FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?";
        Record re = Db.findFirst(sql, order_no);
        if (re != null) {
            outTradeNo = re.getStr("OUT_TRADE_NO");
        }
        return outTradeNo;
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
        String sql = "SELECT XN,SSHJ," + titleSql + " FROM YHSJB WHERE XH=? ORDER BY XN DESC";
        List<Record> records = Db.find(sql, xh);
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

}

package gka.controller.lsjfgl.tjcx.dnkp;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.grjfxx.MyConstant;
import gka.pay.wxpay.controller.MyWxpayConstant;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DnkpDao {

    public static List<Record> getOrderInfo(String sfxn, String xm, String xh, String sfzh) {
        String selectSql = "select XM,XH,XN ";
        String fromSql = " from xssfb where 1=1";
        if (!StringUtils.isEmpty(sfxn)) {
            fromSql += " AND xn='" + sfxn + "'";
        }
        if (!StringUtils.isEmpty(xm)) {
            fromSql += " AND XM like '%" + xm + "%'";
        }
        if (!StringUtils.isEmpty(xh)) {
            fromSql += " AND xh='" + xh + "'";
        }
        if (!StringUtils.isEmpty(sfzh)) {
            fromSql += " AND sfzh='" + sfzh + "'";
        }
        List<Record> list = Db.find(selectSql + fromSql);
        return list;
    }

    public void tf(final String ddh, final String xn, final String xh, final String yh) {
        Db.tx(new IAtom() {
            // todo  退费限制
            @Override
            public boolean run() throws SQLException {
                String sql = "select * from WPT_WXZF_SPECIAL_ORDER WHERE XH=? AND SFXN=? AND ORDER_NO=?";
                Record re = Db.findFirst(sql, xh, xn, ddh);
                String ids = re.getStr("IDS");
                String values = re.getStr("PAY_VAL");
                String pay_type = re.getStr("PAY_TYPE");
                String ORDER_NO = re.getStr("ORDER_NO");
                String out_trade_no = re.getStr("OUT_TRADE_NO");
                String fee = "-" + re.getStr("TOTAL_FEE");

                String TIME_START = re.getStr("TIME_START");

                sql = "UPDATE WPT_WXZF_SPECIAL_ORDER SET ORDER_STATE=? WHERE XH=? AND SFXN=? AND ORDER_NO=?";
                int upOrder = Db.update(sql, MyWxpayConstant.ORDER_STATE_TF, xh, xn, ddh);

                sql = "UPDATE YHSJB SET TFBS=? WHERE XH=? AND XN=? AND DDH=?";
                int upYhsjb = Db.update(sql, MyConstant.SFTF_TF, xh, xn, ddh);

                sql = "SELECT XN,XH,XM,XB,BJMC,ZYMC,NJ,XYMC,SFZH FROM XSSFB WHERE XH=? AND XN=?";
                Record userInfo = Db.findFirst(sql, xh, xn);
                sql = "INSERT INTO YHSJB (XN,XH,XM,XB,BJMC,ZYMC,NJ,XYMC,SFZH,SSHJ,XDSJ,DDH,JFLX," + ids + ",LSH,CZLX,CZRQ,SFRQ,YH,SFLX,SFTF) VALUES (" +
                        "?,?,?,?,?,?,?,?,?,?,?,?,?," + genVal(values) + ",?,?,TO_CHAR(SYSDATE,'YYYY-MM-DD hh24:mi:ss'),TO_CHAR(SYSDATE,'YYYY-MM-DD'),?,?,?)";
                int updateStat = Db.update(sql, userInfo.getStr("XN"), userInfo.getStr("XH"), userInfo.getStr("XM"), userInfo.getStr("XB"), userInfo.getStr("BJMC"),
                        userInfo.getStr("ZYMC"), userInfo.getStr("NJ"), userInfo.getStr("XYMC"), userInfo.getStr("SFZH"), fee, TIME_START, ORDER_NO, pay_type,
                        out_trade_no, MyWxpayConstant.XSSFB_CZLX_LSHTTF, yh, pay_type, "1");
                return upOrder * updateStat * upYhsjb >= 1;
            }
        });
    }

    private String genVal(String values) {
        StringBuffer bf = new StringBuffer();
        String[] val = values.split(",");
        for (int i = 0; i < val.length; i++) {
            if (i < val.length - 1) {
                bf.append("-" + val[i]);
                bf.append(",");
            } else {
                bf.append("-" + val[i]);
            }
        }
        return bf.toString();
    }


    public void insertOrder(final String xh, final String OUT_TRADE_NO, final String ids, final String pay_type, final String total_fee, final String ip, final String xn, final String orderNo, final String values, final String yh) {
        Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String sql = "INSERT INTO WPT_WXZF_SPECIAL_ORDER (OUT_TRADE_NO,IDS,PAY_TYPE,TOTAL_FEE,APPID,MCH_ID,OPENID,PAYIP,TIME_START,TIME_END,ORDER_STATE," +
                        "RETURN_CODE,RESULT_CODE,TRANSACTION_ID,XH,PREPAY_ID,SFXN,ORDER_NO,CODE_URL,TOTAL_FEE_CALLBACK,PAY_VAL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                int upOrder = Db.update(sql, OUT_TRADE_NO, ids, pay_type, total_fee, "", "", "", ip, getTime(), getTime(), MyWxpayConstant.ORDER_STATE_PAY,
                        "success", "SUCCESS", "", xh, "", xn, orderNo, "", total_fee, values);
                int upYsf = updateOrder(OUT_TRADE_NO, pay_type, total_fee, values, yh);
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
    public int updateOrder(String out_trade_no, String pay_type, String fee, String values, String yh) {
        String sql = "SELECT IDS,SFXN,XH,ORDER_NO,TIME_START FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, out_trade_no);
        int updateStat = 0;
        String ids = "";
        String sfxn = "";
        String xh = "";
        String ORDER_NO = "";
        String TIME_START = "";
        if (re != null) {
            ids = re.getStr("IDS");
            sfxn = re.getStr("SFXN");
            xh = re.getStr("XH");
            ORDER_NO = re.getStr("ORDER_NO");
            TIME_START = re.getStr("TIME_START");
            sql = "SELECT XN,XH,XM,XB,BJMC,ZYMC,NJ,XYMC,SFZH FROM XSSFB WHERE XH=? AND XN=?";
            Record userInfo = Db.findFirst(sql, xh, sfxn);
            sql = "INSERT INTO YHSJB (XN,XH,XM,XB,BJMC,ZYMC,NJ,XYMC,SFZH,SSHJ,XDSJ,DDH,JFLX," + ids + ",LSH,CZLX,CZRQ,SFRQ,YH,SFLX) VALUES (" +
                    "?,?,?,?,?,?,?,?,?,?,?,?,?," + values + ",?,?,TO_CHAR(SYSDATE,'YYYY-MM-DD hh24:mi:ss'),TO_CHAR(SYSDATE,'YYYY-MM-DD'),?,?)";
            updateStat = Db.update(sql, userInfo.getStr("XN"), userInfo.getStr("XH"), userInfo.getStr("XM"), userInfo.getStr("XB"), userInfo.getStr("BJMC"),
                    userInfo.getStr("ZYMC"), userInfo.getStr("NJ"), userInfo.getStr("XYMC"), userInfo.getStr("SFZH"), fee, TIME_START, ORDER_NO, pay_type,
                    out_trade_no, MyWxpayConstant.XSSFB_CZLX_LSHTJF, yh, pay_type);
        }

        return updateStat;

    }

    public static String getTime() {
        Date date = new Date();
        String str = "yyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        System.out.println(sdf.format(date));
        return sdf.format(date);
    }

    public Record getUserInfo(String xh, String xn) {
        String sql = "SELECT XH,XM,SFZH,XYMC,ZYMC,BJMC,NJ FROM XSSFB WHERE XH=? AND XN=?";
        Record userInfo = Db.findFirst(sql, xh, xn);
        return userInfo;
    }

    public List<Record> queryYjfList(String xh, String xn) {
        List<Record> list = Db.find("");
        return list;
    }

    public Page<Record> userInfo(int page, int limit, String xn, String xm, String sfzh, String xymc, String zymc, String bjmc) {
        String selectSql = "SELECT XH,XM,XB,SFZH,BJMC,ZYMC,XYMC,NJ ";
        String fromSql = " FROM XSSFB WHERE XN=? ";
        if (!StringUtils.isEmpty(xm)) {
            fromSql += " AND XM like '%" + xm + "%'";
        }
        if (!StringUtils.isEmpty(sfzh)) {
            fromSql += " AND SFZH like '%" + sfzh + "%'";
        }
        if (!StringUtils.isEmpty(xymc)) {
            fromSql += " AND XYMC like '%" + xymc + "%'";
        }
        if (!StringUtils.isEmpty(zymc)) {
            fromSql += " AND ZYMC like '%" + zymc + "%'";
        }
        if (!StringUtils.isEmpty(bjmc)) {
            fromSql += " AND BJMC like '%" + bjmc + "%'";
        }
        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql, xn);


        return paginate;
    }
}

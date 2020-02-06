package gka.thread.dzxc;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.pay.wxpay.MyWxConfig;
import gka.pay.wxpay.WXPay;
import gka.pay.wxpay.WXPayConfig;
import java.text.SimpleDateFormat;
import java.util.*;

public class DzxcThread implements Runnable {
        private final long TIME = 1000 * 60 * 30;
//    private final long TIME = 10 * 1000;

    public DzxcThread() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            sleep(TIME);
            startUp();
        }
    }

    private void startUp() {
        String xx = "";
        String time = getBeforeOneDay();
//        time = "20191104";
        Record record = Db.findFirst("select * from wptma_dzmx where dzsj = ?", time);
        try {

            if (record == null) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
//                hour = 2;
                if (1 < hour && hour < 4) {
                    String fTime = time + "%";
                    String sql = "SELECT o.* from wpt_wxzf_special_order o left join yhsjb y on y.xh=o.xh and y.xn=o.sfxn where o.return_code='success' " +
                            " and pay_type in ('NATIVE','JSAPI') AND o.TIME_START like ?";
                    List<Record> list = Db.find(sql, fTime);
                    if (list.size() > 0) {
                        Double total_fee = 0.0;
                        for (int i = 0; i < list.size(); i++) {
                            total_fee += Double.parseDouble(list.get(i).get("TOTAL_FEE").toString());
                        }
                        Double billMoney = getBillMoney(time);
                        if (total_fee == billMoney) {
                            xx = "对账正确";
                        } else {
                            xx = "对账错误";
                        }
                    } else {
                        xx = "无对账文件";
                    }
                    Db.update("insert into wptma_dzmx (DZSJ,XX) values(?,?)", time, xx);
                }
            }
        } catch (Exception e) {
            xx = "对账异常_" + (e.toString().length() > 50 ? e.toString().substring(0, 30) : e.toString());
            e.printStackTrace();
            Db.update("insert into wptma_dzmx (DZSJ,XX) values(?,?)", time, xx);
        }
    }


    private Double getBillMoney(String date) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        WXPayConfig wxPayConfig = new MyWxConfig();
        WXPay wxPay = new WXPay(wxPayConfig);

        map.put("bill_date", date);
        map.put("bill_type", "SUCCESS");
        Map<String, String> mapNew = wxPay.downloadBill(map);
//
        System.out.println("mapNewdata------" + mapNew.get("data"));
        String bill = mapNew.get("data");
//        String bill = "?交易时间,公众账号ID,商户号,特约商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,应结订单金额,代金券金额,商品名称,商户数据包,手续费,费率,订单金额,费率备注\\r\\n`2019-11-04 18:52:59,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000422201911044383886395,`1572864513058gxaivpd,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:57:05,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000423201911046892119860,`1572868323023nkfdudu,`o1gswuJ_mUWc0bQ8eLkBYPBtHpR8,`NATIVE,`SUCCESS,`GDB_CREDIT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:10:42,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000428201911047831786620,`1572865579667loevqgw,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:15:51,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000438201911041032765762,`1572865890364jryczgn,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:24:03,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000447201911041288502636,`1572866378743unbkuor,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n总交易单数,应结订单总金额,退款总金额,充值券退款总金额,手续费总金额,订单总金额,申请退款总金额\\r\\n`5,`0.05,`0.00,`0.00,`0.00000,`0.05,`0.00";
        String tradeRecords = bill.replace("`", "");
        String[] data = tradeRecords.split(",");
        System.out.println(data[data.length - 2]);
        String result = data[data.length - 2];
        return Double.parseDouble(result) * 100;
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {

        }
    }

    //获取当前系统时间的前一天  yyyyMMdd
    private static String getBeforeOneDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date currentdate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentdate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        currentdate = calendar.getTime();
        String dayTime = format.format(currentdate);
        return dayTime;
    }
}
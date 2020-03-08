package gka.pay.wxpay.thread;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.pay.wxpay.controller.MyWxpayConstant;
import gka.pay.wxpay.controller.WxPayDao;
import gka.pay.wxpay.controller.WxPayOrder;
import gka.pay.wxpay.controller.WxPayTool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Auther ly
 * @Date 2019/9/16
 * @Describe
 */
public class OrderCheck implements Runnable {

    //一个半点（毫秒）
    private int timeOut = 60 * 90 * 1000;

    //10分钟（毫秒）
    private int idellTime = 60 * 10 * 1000;

    public OrderCheck() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<Record> list = queryNoPayOrder();
                for (Record re : list) {
                    if (isInValidate(re.getStr("TIME_START"))) {
                        String out_trade_no = re.getStr("OUT_TRADE_NO");
                        //异步调用关闭订单接口
                        new Thread(new closeOrder(out_trade_no)).start();
                        //更改订单状态
                        WxPayDao.closeOrderDb(out_trade_no, "sys");
                    }
                }
                Thread.sleep(idellTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private List<Record> queryNoPayOrder() {
        String sql = "SELECT TIME_START,OUT_TRADE_NO FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_STATE=?";
        List<Record> list = Db.find(sql, MyWxpayConstant.ORDER_STATE_NOPAY);
        return list;
    }

    private boolean isInValidate(String begin) {
        boolean outDate = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            long beginTime = format.parse(begin).getTime();
            long curr = System.currentTimeMillis();
            if (curr - beginTime >= timeOut) {
                outDate = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return outDate;
    }

    class closeOrder implements Runnable {
        private String out_trade_no;

        public closeOrder(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        @Override
        public void run() {
            try {
                Map<String, String> req = new HashMap<String, String>();
                req.put("out_trade_no", out_trade_no);
                WxPayTool wxPayTool = WxPayTool.getInstance();
                wxPayTool.closeOrder(req);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

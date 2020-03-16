package gka.system.config;

import gka.controller.xxts.tsgh.thred.TsghThread;
import gka.controller.xxts.tsgh.thred.TsghUpdate;
import gka.controller.xxts.ttk.thread.TtkThread;
import gka.controller.xxts.ttk.thread.TtkUpdate;
import gka.pay.wxpay.thread.OrderCheck;

public class InitThread {

    public InitThread() {
        //调停课推送
        new TtkThread();
        new TtkUpdate();
        //图书归还推送
        new TsghUpdate();
        new TsghThread();
        //关闭过期订单
        new OrderCheck();
    }
}

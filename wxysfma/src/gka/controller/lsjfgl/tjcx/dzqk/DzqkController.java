package gka.controller.lsjfgl.tjcx.dzqk;


import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ExcelExportUtil;
import gka.common.kit.ReturnKit;
import gka.pay.wxpay.MyWxConfig;
import gka.pay.wxpay.WXPay;
import gka.pay.wxpay.WXPayConfig;
import gka.thread.Bill;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@ControllerBind(controllerKey = "/lsjfgl/tjcx/dzqk")
public class DzqkController extends Controller {
    private DzqkDao dzqkDao = new DzqkDao();

    public void index() {
        Map map = new HashMap();
        try {
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            String dateStart = getPara("dateStart");
            String dateEnd = getPara("dateEnd");
            Page<Record> paginate = dzqkDao.getOrderInfo(page, limit, dateStart, dateEnd);
            map.put("code", "0");
            map.put("msg", "success");
            map.put("data", paginate.getList());
            map.put("count", paginate.getTotalRow());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }

    public void sddz() {
        String time = getPara("myTime");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = dateFormat.format(date);
        String xx = "";//对账结果
        String zt = "";//状态
        Record record = Db.findFirst("select * from wptma_dzmx where dzsj = ?", time);
        try {
            if (!time.equals(now)) {
                boolean bool = false;
                if (record != null) {
                    String state = record.getStr("XX");
                    if (state.contains("对账异常")) {
                        bool = true;
                    }
                } else {
                    bool = true;
                }
                if (bool) {
                    String fTime = time.replaceAll("-", "") + "%";
                    String sql = "SELECT o.* from wpt_wxzf_special_order o left join yhsjb y on y.xh=o.xh and y.xn=o.sfxn where o.return_code='success' " +
                            " and pay_type in ('NATIVE','JSAPI') AND o.TIME_START like ?";
                    List<Record> list = Db.find(sql, fTime);
                    int sum = list.size();       //订单已支付条数
                    Double total_fee = 0.0;      //订单合计金额
                    int wxSum = 0;               //微信交易笔数
                    Double wxtotal_fee = 0.0;    //微信订单合计金额
                    int ycsum = 0;              //异常订单笔数
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            total_fee += Double.parseDouble(list.get(i).get("TOTAL_FEE").toString());
                        }
                        Map<String, String> map = new HashMap<String, String>();
                        WXPayConfig wxPayConfig = new MyWxConfig();
                        WXPay wxPay = new WXPay(wxPayConfig);
                        map.put("bill_date", time.replaceAll("-", ""));
                        map.put("bill_type", "SUCCESS");
                        Map<String, String> mapNew = wxPay.downloadBill(map);
                        String json = mapNew.toString();
//                        2019-11-03
//                        String json = "{\"data\":\"?交易时间,公众账号ID,商户号,特约商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,应结订单金额,代金券金额,商品名称,商户数据包,手续费,费率,订单金额,费率备注\\r\\n`2019-11-03 19:12:02,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000451201911032334502019,`1572779359488bfffqlj,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:54:24,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000438201911035408473990,`1572781802656bezthwq,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 13:16:39,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000424201911036747682838,`1572757964994sihzssv,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:13:38,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000428201911033904916624,`1572779456441njdpfjt,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:11:26,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000436201911032648215072,`1572779319691kbhmiyl,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 16:08:31,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000450201911030986775722,`1572768334142edddrtb,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:38:32,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000422201911036492621733,`1572780844897ryfinnu,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:32:55,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000447201911033582479277,`1572780486748jnqgptn,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 13:19:48,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000428201911036577176110,`1572758213015utessiv,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:41:51,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000444201911031335872945,`1572781045045rsrdpol,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 14:40:56,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000444201911039519361336,`1572762946905lliqtzn,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:43:02,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000448201911037207725385,`1572781118181lhjuwvs,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:55:47,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000448201911039779022415,`1572781885332gxovrik,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:12:52,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000431201911035458064465,`1572779408633psihmhd,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:35:52,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000435201911032803163219,`1572780686701iplnckv,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:10:56,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000436201911030714109414,`1572779286073fbwqfwo,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:54:54,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000426201911033037577837,`1572781833049avgjhgh,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 16:07:09,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000449201911030600420721,`1572768258784aywavgs,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:39:28,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000440201911032289264383,`1572780905283qvvqzap,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n总交易单数,应结订单总金额,退款总金额,充值券退款总金额,手续费总金额,订单总金额,申请退款总金额\\r\\n`19,`0.19,`0.00,`0.00,`0.00000,`0.19,`0.00\",\"return_msg\":\"ok\",\"return_code\":\"SUCCESS\"}";
//                        2019-11-04
//                        String json = "{\"data\":\"?交易时间,公众账号ID,商户号,特约商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,应结订单金额,代金券金额,商品名称,商户数据包,手续费,费率,订单金额,费率备注\\r\\n`2019-11-04 18:52:59,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000422201911044383886395,`1572864513058gxaivpd,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:57:05,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000423201911046892119860,`1572868323023nkfdudu,`o1gswuJ_mUWc0bQ8eLkBYPBtHpR8,`NATIVE,`SUCCESS,`GDB_CREDIT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:10:42,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000428201911047831786620,`1572865579667loevqgw,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:15:51,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000438201911041032765762,`1572865890364jryczgn,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:24:03,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000447201911041288502636,`1572866378743unbkuor,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n总交易单数,应结订单总金额,退款总金额,充值券退款总金额,手续费总金额,订单总金额,申请退款总金额\\r\\n`5,`0.05,`0.00,`0.00,`0.00000,`0.05,`0.00\",\"return_msg\":\"ok\",\"return_code\":\"SUCCESS\"}";
                        JSONObject jsonObject = JSONObject.parseObject(json);
                        if (jsonObject.get("return_msg").equals("ok")) {
                            List<Bill> billList = Bill.analyze(json, time);
                            wxSum = billList.size();
                            if (wxSum > 0) {
                                String tradeRecords = jsonObject.getString("data").replace("`", "");
                                String[] data = tradeRecords.split(",");
                                String wxje = data[data.length - 2];
                                wxtotal_fee = Double.valueOf(wxje.toString());
                            }
                            ycsum = Math.abs(sum - wxSum);
                            if (ycsum == 0) {
                                if (wxtotal_fee == total_fee) {
                                    xx = "对账正确";
                                    zt = "正确";
                                } else {
                                    xx = "对账异常";
                                    zt = "异常";
                                }
                            } else {
                                xx = "对账异常";
                                zt = "异常";
                            }
                        } else {
                            zt = "异常";
                            xx = "对账接口调用失败";
                        }
                    } else {
                        zt = "正确";
                        xx = "无对账文件";
                    }
                    Db.update("delete wptma_dzmx where DZSJ=?", time);
                    Db.update("insert into wptma_dzmx (DZSJ,XX,DZJG,TS,JE,WXTS,WXJE,YCBS) values(?,?,?,?,?,?,?,?)",
                            time, zt, xx, sum, String.valueOf(total_fee), wxSum, String.valueOf(wxtotal_fee), ycsum);
                    renderJson(ReturnKit.retOk());
                } else {
                    renderJson(ReturnKit.retFail("您选择的时间已经完成对账!"));
                }
            } else {
                renderJson(ReturnKit.retFail("对账时间不可以选择当前日期!"));
            }
        } catch (Exception e) {
            xx = "对账异常_" + (e.toString().length() > 50 ? e.toString().substring(0, 30) : e.toString());
            e.printStackTrace();
            Db.update("delete wptma_dzmx where DZSJ=?", time);
            Db.update("insert into wptma_dzmx (DZSJ,XX,DZJG,TS,JE,WXTS,WXJE,YCBS) values(?,?,?,'0','0','0','0','0')", time, "错误", xx);
            renderJson(ReturnKit.retFail("系统异常!"));
        }
    }

    public void export() {
        try {
            String dzsj = getPara("dzsj");
            String sql = "SELECT o.*,y.XM,y.XH from wpt_wxzf_special_order o left join yhsjb y on y.xh=o.xh and y.xn=o.sfxn where o.return_code='success' " +
                    " and pay_type in ('NATIVE','JSAPI') AND o.TIME_START like '" + dzsj + "%'" + "";
            Map<String, String> titleData = new HashMap<String, String>();//标题，后面用到
            titleData.put("ORDER_NO", "订单编号");//
            titleData.put("SFXN", "交费学年");//
            titleData.put("XM", "姓名");
            titleData.put("XH", "学号");
            titleData.put("TOTAL_FEE", "订单合计");
            titleData.put("TIME_END", "支付时间");//
            File file = new File(ExcelExportUtil.getTitle(dzsj + "对账数据"));
            file = ExcelExportUtil.saveFile(titleData, sql, file);
            this.renderFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            setAttr("errorMessage", "导出失败请稍后再试，错误信息：" + e.toString());
            render("/error/400.html");
        }
    }

    public void exportAll() {
        try {
            String selectSql = "select DZSJ,XX,DZJG,TS,JE,WXTS,WXJE,YCBS from wptma_dzmx";
            Map<String, String> titleData = new HashMap<String, String>();//标题，后面用到
            titleData.put("DZSJ", "账务日期");//
            titleData.put("XX", "状态");//
            titleData.put("DZJG", "对账结果");//
            titleData.put("TS", "订单已支付条数");
            titleData.put("JE", "订单合计金额");
            titleData.put("WXTS", "微信对账支付条数");
            titleData.put("WXJE", "微信对账合计金额");//
            titleData.put("YCBS", "异常订单数量");//
            File file = new File(ExcelExportUtil.getTitle("对账情况数据"));
            file = ExcelExportUtil.saveFile(titleData, selectSql, file);
            this.renderFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            setAttr("errorMessage", "导出失败请稍后再试，错误信息：" + e.toString());
            render("/error/400.html");
        }
    }
}

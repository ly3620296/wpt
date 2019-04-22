package gka.pay.wxpay;

/**
 * @Auther ly
 * @Date 2019/4/22
 * @Describe
 */
public class MyWxPayDomain implements IWXPayDomain {
    private DomainInfo domainInfo;
    private static MyWxPayDomain myWxPayDomain = null;

    private MyWxPayDomain() {
        domainInfo = new DomainInfo("api.mch.weixin.qq.com", true);
    }

    public static MyWxPayDomain getWxPayDomain() {
        if (myWxPayDomain == null) {
            myWxPayDomain = new MyWxPayDomain();
        }
        return myWxPayDomain;
    }

    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {

    }

    @Override
    public DomainInfo getDomain(WXPayConfig config) {
        return domainInfo;
    }
}

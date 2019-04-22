package gka.pay.wxpay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Auther ly
 * @Date 2019/4/22
 * @Describe
 */
public class MyWxConfig extends WXPayConfig {
    /**
     * 获取 App ID
     *
     * @return App ID
     */
    @Override
    String getAppID() {
        return "wxdf9968af00e458e0";
    }

    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    @Override
    String getMchID() {
        return "1393091302";
    }

    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    @Override
    String getKey() {
        return "xiekui13596073163zbin13944151952";
    }

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    InputStream getCertStream() {
        InputStream inputStream = null;
        try {
            File f = new File("D:/Program Files/WXCertUtil/cert/apiclient_cert.p12");
            System.out.println("#####################"+f.exists());
            inputStream = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     *
     * @return
     */
    @Override
    IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = MyWxPayDomain.getWxPayDomain();
        return iwxPayDomain;
    }
}

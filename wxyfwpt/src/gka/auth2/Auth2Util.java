package gka.auth2;


import gka.resource.properties.ProFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Auth2Util {
    //获取code接口地址
    private final static String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    //回调地址，既接取code地址

    private final static String REDIRECT_URI = ProFactory.use("gkean.properties").getStr("domain_name") + "/wpt/auth";
    private final static String APP_ID = ProFactory.use("gkean.properties").getStr("appId");
    private final static String APP_SECRET = ProFactory.use("gkean.properties").getStr("appSecret");
    private final static String RESPONSE_TYPE = "code";
    //snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
    //snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
    private final static String SCOPE = "snsapi_base";
    private final static String STATE = "state";
    private final static String WECHAT_REDIRECT = "wechat_redirect";

    public static String createAuth2Url() {
        StringBuffer stringBuffer = null;
        try {
            stringBuffer = new StringBuffer();
            stringBuffer.append(AUTHORIZE_URL);
            stringBuffer.append("?");
            stringBuffer.append("appid");
            stringBuffer.append("=");
            stringBuffer.append(APP_ID);
            stringBuffer.append("&");
            stringBuffer.append("redirect_uri");
            stringBuffer.append("=");
            stringBuffer.append(URLEncoder.encode(REDIRECT_URI, "UTF-8"));
            stringBuffer.append("&");
            stringBuffer.append("response_type");
            stringBuffer.append("=");
            stringBuffer.append(RESPONSE_TYPE);
            stringBuffer.append("&");
            stringBuffer.append("scope");
            stringBuffer.append("=");
            stringBuffer.append(SCOPE);
            stringBuffer.append("&");
            stringBuffer.append("state");
            stringBuffer.append("=");
            stringBuffer.append(STATE);
            stringBuffer.append("#");
            stringBuffer.append(WECHAT_REDIRECT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(createAuth2Url());
    }
}

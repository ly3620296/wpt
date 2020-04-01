package gka.auth2;


import com.alibaba.fastjson.JSONObject;
import gka.kit.HttpUtil;
import gka.resource.properties.ProFactory;

public class AccessToken {
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private final static String APP_ID = ProFactory.use("gkean.properties").getStr("appId");
    private final static String APP_SECRET = ProFactory.use("gkean.properties").getStr("appSecret");
    private static final String GRANT_TYPE = "authorization_code";

    public static String getAccessToken(String code) {
        StringBuffer stringBuffer = new StringBuffer(ACCESS_TOKEN_URL);
        stringBuffer.append("?");
        stringBuffer.append("appid");
        stringBuffer.append("=");
        stringBuffer.append(APP_ID);
        stringBuffer.append("&");
        stringBuffer.append("secret");
        stringBuffer.append("=");
        stringBuffer.append(APP_SECRET);
        stringBuffer.append("&");
        stringBuffer.append("code");
        stringBuffer.append("=");
        stringBuffer.append(code);
        stringBuffer.append("&");
        stringBuffer.append("grant_type");
        stringBuffer.append("=");
        stringBuffer.append(GRANT_TYPE);
        String json = HttpUtil.doGet(stringBuffer.toString());
        return json;
    }

    public static String getOpenId(String code) {
        StringBuffer stringBuffer = new StringBuffer(ACCESS_TOKEN_URL);
        String openId = null;
        stringBuffer.append("?");
        stringBuffer.append("appid");
        stringBuffer.append("=");
        stringBuffer.append(APP_ID);
        stringBuffer.append("&");
        stringBuffer.append("secret");
        stringBuffer.append("=");
        stringBuffer.append(APP_SECRET);
        stringBuffer.append("&");
        stringBuffer.append("code");
        stringBuffer.append("=");
        stringBuffer.append(code);
        stringBuffer.append("&");
        stringBuffer.append("grant_type");
        stringBuffer.append("=");
        stringBuffer.append(GRANT_TYPE);
        String jsonStr = HttpUtil.doGet(stringBuffer.toString());
        if (jsonStr != null) {
            JSONObject access_json = JSONObject.parseObject(jsonStr);
            openId = access_json.getString("openid");
        }
        return openId;
    }
}

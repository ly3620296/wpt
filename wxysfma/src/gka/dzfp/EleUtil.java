package gka.dzfp;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import gka.dzfp.anno.ElType;
import gka.dzfp.bean.InvoiceEBillByCollegeBean;
import gka.dzfp.bean.ItemDetail;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.UUID;

public class EleUtil {

    private String appId = "app1";
    private String version = "1.0";
    private String key = "192006250b4c09247ec02f6a2d";

    /**
     * 报文签名
     */
    public JSONObject sign(JSONObject jsonObject) {
        StringBuffer sb = new StringBuffer();
        sb.append("appid=");
        sb.append(appId);
        sb.append("&");
        sb.append("data=");
        sb.append(jsonObject.getString("data"));
        sb.append("&");
        sb.append("noise=");
        sb.append(jsonObject.getString("noise"));
        sb.append("&");
        sb.append("key=");
        sb.append(key);
        sb.append("&");
        sb.append("version=");
        sb.append(version);
        String sign = md5(sb.toString()).toUpperCase();
        jsonObject.put("sign", sign);
        return jsonObject;
    }


    /**
     * 数据编码
     */
    public JSONObject encData(Object object) {
        JSONObject jsonObject = new JSONObject();
        try {
            Class<?> clz = object.getClass();
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                String type = field.getGenericType().toString();
                // 如果类型是String
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                    String key = field.getName();
                    String val = (String) m.invoke(object);
                    if (!StringUtils.isEmpty(val)) {
                        jsonObject.put(key, val);
                    } else {
                        ElType elType = field.getDeclaredAnnotation(ElType.class);
                        if (elType != null) {
                            boolean require = elType.require();
                            if (require) {
                                jsonObject.put(key, val);
                            }
                        }

                    }
                } else if (type.equals("double") || type.equals("class java.lang.Double")) {
                    Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                    String key = field.getName();
                    double val = (Double) m.invoke(object);
                    if (val == 0.0) {
                        jsonObject.put(key, val);
                    } else {
                        ElType elType = field.getDeclaredAnnotation(ElType.class);
                        if (elType != null) {
                            boolean require = elType.require();
                            if (require) {
                                jsonObject.put(key, val);
                            }
                        }
                    }
                } else if (type.equals("int") || type.equals("class java.lang.Integer")) {
                    Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                    String key = field.getName();
                    int val = (Integer) m.invoke(object);
                    if (val == 0.0) {
                        jsonObject.put(key, val);
                    } else {
                        ElType elType = field.getDeclaredAnnotation(ElType.class);
                        if (elType != null) {
                            boolean require = elType.require();
                            if (require) {
                                jsonObject.put(key, val);
                            }
                        }
                    }
                } else {
                    Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                    String key = field.getName();
                    Object ob = (Object) m.invoke(object);
                    if (ob != null) {
                        JSONObject filedJson = encData(ob);
                        jsonObject.put(key, filedJson);
                    } else {
                        ElType elType = field.getDeclaredAnnotation(ElType.class);
                        if (elType != null) {
                            boolean require = elType.require();
                            if (require) {
                                jsonObject.put(key, new JSONObject());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 生成噪点
     */
    private String genNoise() {
        return UUID.randomUUID().toString().replaceAll("-", "");

    }

    private String base64(JSONObject jsonObject) {
        Base64 base64 = new Base64();
        String base64Sign = "";
        try {
            base64Sign = base64.encodeToString(jsonObject.toJSONString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return base64Sign;
    }

    /**
     * 生成请求报文
     */
    public JSONObject genRequestBody(Object object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid", appId);
        jsonObject.put("data", base64(encData(object)));
        jsonObject.put("noise", genNoise());
        jsonObject.put("version", version);
        sign(jsonObject);
        return jsonObject;
    }

    /**
     * md5加密
     *
     * @param s
     * @return
     */
    public String md5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        EleUtil eleUtil = new EleUtil();
        InvoiceEBillByCollegeBean i = new InvoiceEBillByCollegeBean();
        i.setItemDetail(new ItemDetail());
        JSONObject jsonObject = eleUtil.genRequestBody(i);
        System.out.println(jsonObject);

    }
}

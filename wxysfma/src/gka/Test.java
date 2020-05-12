package gka;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("billBatchCode", "0100000100");
        jsonObject.put("random", "DFG456");
        jsonObject.put("billNo", "0000000001");
        System.out.println(jsonObject.toJSONString());
        Base64 base64 = new Base64();
        String base64Sign = base64.encodeToString(jsonObject.toJSONString().getBytes("UTF-8"));
        System.out.println(base64Sign);
//        ewogICAgICAgICJlQmlsbEJhdGNoQ29kZSI6ICIwMTAwMDAwMTAwIiwKICAgICAgICAiZUJpbGxObyI6ICIwMDAwMDAwMDAxIiwKICAgICAgICAiZVJhbmRvbSI6ICJERkc0NTYiCiB9
//        eyJyYW5kb20iOiJERkc0NTYiLCJiaWxsTm8iOiIwMDAwMDAwMDAxIiwiYmlsbEJhdGNoQ29kZSI6IjAxMDAwMDAxMDAifQ==
    }
}
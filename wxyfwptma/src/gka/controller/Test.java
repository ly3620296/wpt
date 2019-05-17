package gka.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.system.ReturnInfo;

import java.util.Random;

/**
 * Created by Administrator on 2019/4/18 0018.
 */

@ControllerBind(controllerKey = "/test")
public class Test extends Controller {
    public void data() {
        StringBuffer buffer = new StringBuffer("{\"code\": 0, \"msg\": \"\",\"count\": 500,\"data\":[");
        int limit = Integer.parseInt(getPara("limit"));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int page = Integer.parseInt(getPara("page"));

        for (int i = limit * (page - 1); i < limit * page; i++) {
            if (i < limit * page - 1) {
                buffer.append("{");
                buffer.append("\"id\": " + i + ", ");
                buffer.append("\"username\": \"user-" + i + "\",");
                if (i % 2 == 0) {
                    buffer.append("\"sex\": \"女\", ");
                } else {
                    buffer.append("\"sex\": \"男\", ");
                }
                buffer.append("\"city\": \"城市-" + i + "\", ");
                buffer.append("\"sign\": \"签名-" + i + "\",");
                buffer.append("\"experience\": " + new Random().nextInt(1000) + ",");
                buffer.append("\"logins\": " + new Random().nextInt(500) + ", ");
                buffer.append("\"wealth\": " + new Random().nextInt(99999999) + ",");
                buffer.append("\"classify\": \"作家\",");
                buffer.append("\"score\": " + new Random().nextInt(100) + "");
                buffer.append("},");
            } else {
                buffer.append("{");
                buffer.append("\"id\": " + i + ", ");
                buffer.append("\"username\": \"user-" + i + "\",");
                if (i % 2 == 0) {
                    buffer.append("\"sex\": \"女\", ");
                } else {
                    buffer.append("\"sex\": \"男\", ");
                }
                buffer.append("\"city\": \"城市-" + i + "\", ");
                buffer.append("\"sign\": \"签名-" + i + "\",");
                buffer.append("\"experience\": " + new Random().nextInt(1000) + ",");
                buffer.append("\"logins\": " + new Random().nextInt(500) + ", ");
                buffer.append("\"wealth\": " + new Random().nextInt(99999999) + ",");
                buffer.append("\"classify\": \"作家\",");
                buffer.append("\"score\": " + new Random().nextInt(100) + "");
                buffer.append("}");
            }
        }
        buffer.append("]}");
        renderJson(buffer.toString());
    }
}

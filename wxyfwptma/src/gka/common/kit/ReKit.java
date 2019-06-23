package gka.common.kit;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 杨亮
 * Date: 2017/7/22
 * Time: 14:06
 * DESCRIPTION: JSON类型返回工具
 * To change this template use File | Settings | File Templates.;
 */
public class ReKit extends LinkedHashMap {

    public static Map toMap(Integer count, List list) {
        Map<String, Object> map = new HashMap<String, Object>();
        String code = "0";
        String msg = "";
        if (list.size() <= 0) {
            code = "-1";
            msg = "暂无数据!";
        }
        map.put("code",code);
        map.put("msg",msg);
        map.put("count",count);
        map.put("data",list);
        return map;
    }
}

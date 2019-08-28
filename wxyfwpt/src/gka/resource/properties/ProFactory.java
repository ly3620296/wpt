package gka.resource.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Auther ly
 * @Date 2019/3/14
 * @Describe
 */
public class ProFactory {
    private static Map<String, Pro> proFactory = new HashMap<String, Pro>();

    static {
        List<String> propNames = PropertiesUtil.getSrcPropertiesName();
        for (String propName : propNames) {
            proFactory.put(propName, new Pro(PropertiesUtil.initProperties(propName)));
        }
    }

    public static Pro use(String propName) {
        return proFactory.get(propName);
    }

    public static Map<String, Pro> getProFactory() {
        return proFactory;
    }
}

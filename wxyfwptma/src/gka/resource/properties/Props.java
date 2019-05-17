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
public class Props {
    private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

    public static void initProps() {
        List<String> propNames = PropertiesUtil.getSrcPropertiesName();
        for (String propName : propNames) {
            propertiesMap.put(propName, PropertiesUtil.initProperties(propName));
        }
    }

    /**
     * @param propertiesName 属性文件名
     * @return
     * @describe 根据属性文件名获取属性文件
     */
    public static Properties getProperty(String propertiesName) {
        return propertiesMap.get(propertiesName);
    }

}

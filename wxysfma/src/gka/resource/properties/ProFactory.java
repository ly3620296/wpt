package gka.resource.properties;

import gka.pay.ylpay.sdk.SDKConfig;

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
    private static Map<String, Properties> propertiesFactory = new HashMap<String, Properties>();

    static {
        List<String> propNames = ProKit.getSrcPropertiesName();
        SDKConfig.getConfig().loadPropertiesFromSrc();
        for (String propName : propNames) {
            if (!propName.equals("acp_sdk.properties")) {

                propertiesFactory.put(propName, ProKit.initProperties(propName));
            }
        }
    }

    public static Map<String, Properties> getPropertiesFactory() {
        return propertiesFactory;
    }
}

package gka.resource.properties;

import java.util.Properties;

/**
 * @Auther ly
 * @Date 2019/3/14
 * @Describe
 */
public class Pro {
    private Properties properties = null;

    public Pro(Properties properties) {
        this.properties = properties;
    }


    public String getStr(String propKey) {
        return properties.getProperty(propKey);
    }

    public Boolean getBoolean(String propKey) {
        return Boolean.parseBoolean(properties.getProperty(propKey));
    }

    public int getInt(String propKey) {
        return Integer.parseInt(properties.getProperty(propKey));
    }


}

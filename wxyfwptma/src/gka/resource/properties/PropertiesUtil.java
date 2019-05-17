package gka.resource.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Auther ly
 * @Date 2019/3/14
 * @Describe
 */
public class PropertiesUtil {
    private static Logger logger = LogManager.getLogger();

    /**
     * @param propName 配置文件名
     * @return
     */
    public static Properties initProperties(String propName) {
        Properties prop = null;
        try {
            InputStreamReader inputStream = new InputStreamReader(getClassLoader().getResourceAsStream("/" + propName), "utf-8");
            prop = new Properties();
            prop.load(inputStream);
        } catch (IOException e) {
            logger.error("加载配置文件[" + propName + "]失败:" + e.getMessage());
            e.printStackTrace();

        }
        return prop;
    }

    /**
     * @param path     文件路径
     * @param propName 文件名
     * @return
     */
    public static Properties initProperties(String path, String propName) {
        Properties prop = null;
        try {
            InputStreamReader inputStream = new InputStreamReader(getClassLoader().getResourceAsStream("/" + path + "/" + propName), "utf-8");
            prop = new Properties();
            prop.load(inputStream);
        } catch (IOException e) {
            logger.error("加载配置文件[/" + path + "/" + propName + "]失败:" + e.getMessage());
            e.printStackTrace();

        }
        return prop;
    }

    /**
     * @return
     * @Describe 获取类路径下所有properties文件名
     */
    public static List getSrcPropertiesName() {
        List<String> nameList = null;
        try {
            nameList = new ArrayList<String>();
            File classFile = new File(getClassLoader().getResource("").getPath());
            File[] files = classFile.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if ("properties".equals(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase())) {
                        nameList.add(fileName);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取类路径下所有properties文件名失败：" + e.getMessage());
            e.printStackTrace();
        }
        return nameList;
    }

    private static ClassLoader getClassLoader() {
        ClassLoader ret = Thread.currentThread().getContextClassLoader();
        return ret != null ? ret : PropertiesUtil.class.getClassLoader();
    }

    public static void main(String[] args) {
        String fileName = "gkean.properties";
        System.out.println();
    }
}

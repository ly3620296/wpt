package gka.resource.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * @Auther lxz 解析xml文件
 * @Date 2019/3/14
 * @Describe
 */
public class InitXml {
    private static Logger logger = LogManager.getLogger();
    //开发库
    private static final String DEV_FILENAME = "database_dev.xml";
    //生产库
    private static final String PRO_FILENAME = "database.xml";

    public static final String DEV_MODE = "dev";

    public static final String PRO_MODE = "pro";

    //加载xml文件
    public static Document initDbXml(String xmlName) {
        Document document = null;
        String path = InitXml.class.getResource("/").getPath();
        try {
            if (xmlName.equals(DEV_MODE)) {
                xmlName = DEV_FILENAME;
            } else if (xmlName.equals(PRO_MODE)) {
                xmlName = PRO_FILENAME;
            } else {
                String xmlNameBin = xmlName.substring(xmlName.length() - 4);
                if (!xmlNameBin.equals(".xml")) {
                    xmlName += ".xml";
                }
            }
                path += xmlName;
                File file = new File(path);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(file);
            }catch(Exception e){
                logger.error("读取数据库配置文件[" + xmlName + "]失败:" + e.getMessage());
                e.printStackTrace();
            }
            return document;
        }

}

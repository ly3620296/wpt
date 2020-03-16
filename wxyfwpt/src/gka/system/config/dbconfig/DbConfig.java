package gka.system.config.dbconfig;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Plugins;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import gka.resource.properties.ProFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.sql.Connection;

/**
 * * @Auther lxz 数据库配置
 *
 * @Date 2019/3/15
 * @Describe
 */
public class DbConfig {
    private static WallFilter wallFilter;
    private static Logger logger = LogManager.getLogger();

    public static void datebaseUse(Plugins plugins, Document document) {
        NodeList nl = document.getElementsByTagName("druid");
        for (int i = 0; i < nl.getLength(); i++) {
            String jdbcUrl = document.getElementsByTagName("jdbcUrl").item(i).getFirstChild().getNodeValue();
            String username = document.getElementsByTagName("username").item(i).getFirstChild().getNodeValue();
            String password = document.getElementsByTagName("password").item(i).getFirstChild().getNodeValue();
            String driver = document.getElementsByTagName("driver").item(i).getFirstChild().getNodeValue();
            String validationQuery = document.getElementsByTagName("validationQuery").item(i).getFirstChild().getNodeValue();
            int initialSize = Integer.parseInt(document.getElementsByTagName("initialSize").item(i).getFirstChild().getNodeValue());
            int maxActive = Integer.parseInt(document.getElementsByTagName("maxActive").item(i).getFirstChild().getNodeValue());
            int maxOpenPreparedStatements = Integer.parseInt(document.getElementsByTagName("maxOpenPreparedStatements").item(i).getFirstChild().getNodeValue());
            boolean testWhileIdle = Boolean.parseBoolean(document.getElementsByTagName("testWhileIdle").item(i).getFirstChild().getNodeValue());
            String dbType = document.getElementsByTagName("dbType").item(i).getFirstChild().getNodeValue();
            String dataSource = document.getElementsByTagName("dataSource").item(i).getFirstChild().getNodeValue();
            logger.info(ProFactory.use("gkean.properties").getStr("system.title") + "开始配置" + dataSource + "数据库连接信息....");
            logger.info("URL-->>" + jdbcUrl);
            logger.info("username-->>" + username);
            logger.info("password-->>" + password);
            DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, username, password, driver);
            druidPlugin.setValidationQuery(validationQuery);
            druidPlugin.setInitialSize(initialSize);
            druidPlugin.setMaxActive(maxActive);
            druidPlugin.setMaxPoolPreparedStatementPerConnectionSize(maxOpenPreparedStatements);
            druidPlugin.setTestWhileIdle(testWhileIdle);
            wallFilter = new WallFilter();  // 添加防御sql注入的filter，加强数据库安全
            wallFilter.setDbType(dbType);
//            druidPlugin.addFilter(wallFilter);
            druidPlugin.addFilter(new StatFilter()); // 添加监控统计StatFilter 才会有统计数据
            plugins.add(druidPlugin);
            ActiveRecordPlugin activeRecordPlugin;
            if (dataSource.equalsIgnoreCase(DbKit.MAIN_CONFIG_NAME)) {
                activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
            } else {
                activeRecordPlugin = new ActiveRecordPlugin(dataSource, druidPlugin);
            }
            if (dbType.equalsIgnoreCase("ORACLE")) {
                activeRecordPlugin.setDialect(new OracleDialect()); //配置ORACLE方言
            } else if (dbType.equalsIgnoreCase("MYSQL")) {
                activeRecordPlugin.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
                activeRecordPlugin.setDialect(new MysqlDialect()); //配置Mysql方言
            }

            if (dataSource.equalsIgnoreCase("main"))
                activeRecordPlugin.setContainerFactory(new CaseInsensitiveContainerFactory());//配置数据库字段名大小写不敏感
            activeRecordPlugin.setShowSql(ProFactory.use("gkean.properties").getBoolean("devMode"));//显示SQL语句
            plugins.add(activeRecordPlugin);
            logger.info(ProFactory.use("gkean.properties").getStr("system.title") + "开始配置" + dataSource + "数据库配置完毕....");
        }
    }
}

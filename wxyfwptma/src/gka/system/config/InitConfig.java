package gka.system.config;


import com.jfinal.config.*;
import com.jfinal.ext.route.ControllerRoute;
import com.jfinal.template.Engine;
import gka.resource.properties.ProKit;
import gka.resource.xml.InitXml;
import gka.system.config.dbconfig.DbConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

public class InitConfig extends JFinalConfig {
    private Logger logger = LogManager.getLogger(InitConfig.class);
    private int MAX_POST_SIZE = 1024 * 1024 * 1000;
    Document document = InitXml.initDbXml(InitXml.DEV_MODE);

    public InitConfig() {

    }

    public void configConstant(Constants constants) {
        logger.info(ProKit.use("gkean.properties").getStr("system.title") + "常数配置开始....");
        constants.setDevMode(ProKit.use("gkean.properties").getBoolean("devMode"));
        //文件上传大限制 (Byte)
        constants.setMaxPostSize(MAX_POST_SIZE);
        //http 异常页面
        constants.setError404View("/error/404.html");
        constants.setError500View("/error/500.html");
        logger.info(ProKit.use("gkean.properties").getStr("system.title") + "常数配置完毕....");
    }

    //路由配置
    public void configRoute(Routes routes) {
        ControllerRoute.register(routes, "gka");
        logger.info(ProKit.use("gkean.properties").getStr("system.title") + "路由配置完成....");
    }


    /**
     * 加载数据库xml文件
     *
     * @param plugins
     */
    public void configPlugin(Plugins plugins) {
        DbConfig.datebaseUse(plugins, document);
    }

    /**
     * 模板引擎配置
     *
     * @param engine
     */
    public void configEngine(Engine engine) {
    }

    public void configInterceptor(Interceptors interceptors) {

    }

    public void configHandler(Handlers handlers) {

    }

    /**
     * 本方法会在 jfinal 启动过程完成之后被回调，详见 jfinal 手册
     */
    public void afterJFinalStart() {
    }

    /**
     * 本方法会在 jfinal 关闭之前被回调，详见 jfinal 手册
     */
    public void beforeJFinalStop() {

    }

}
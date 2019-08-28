package gka.resource;

import gka.resource.properties.ProFactory;

public class Constant {
    //服务名
    public static String server_name = ProFactory.use("gkean.properties").getStr("server_name");
}

package gka;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class aa {
//    74824C581BF2D216B05EB5D2B7FBD40B
    public static void main(String[] args) throws IOException {
//        String ur = "http://10.161.126.30:30087/jlsgswx/order/sendMsg?mobile=2476b54f22ac77334a5085f7e0e0ee5c58096fca6c1d85e31e3e10124deb40b4acf43e255b7e73bf94b456b26853381abbea175805c6e38e857be127dff9f7210044a80613051766034b8b96f8dd6d74566940f22004fd9bfc03f46769fe120c753f12104837434104203ff967f20f2743aa09bbf18c2e441cd916f05a03439d415d6584fe7395727231c78ceda666b4e80b0699b8f69e4ffec8046aa3af106edc1e7ac8aed1a8b33aa5847e9d1bff94347b48621e84edbf27671168ef8bf824c69700ab8821421619d27bd247adf45bfd33fce02156795d9c1b705d56817ef0ae9d1e92f901c8ae0e33e918c60975f8b985d8585b3efbef85b17b8480e8afe2";
        String ur = "http://10.161.253.237:10087/jlsgswx/weixin/Liaoyuanliul/sendMsg?mobile=2476b54f22ac77334a5085f7e0e0ee5c58096fca6c1d85e31e3e10124deb40b4acf43e255b7e73bf94b456b26853381abbea175805c6e38e857be127dff9f7210044a80613051766034b8b96f8dd6d74566940f22004fd9bfc03f46769fe120c753f12104837434104203ff967f20f2743aa09bbf18c2e441cd916f05a03439d415d6584fe7395727231c78ceda666b4e80b0699b8f69e4ffec8046aa3af106edc1e7ac8aed1a8b33aa5847e9d1bff94347b48621e84edbf27671168ef8bf824c69700ab8821421619d27bd247adf45bfd33fce02156795d9c1b705d56817ef0ae9d1e92f901c8ae0e33e918c60975f8b985d8585b3efbef85b17b8480e8afe2";
//        String ur = "http://www.baidu.com";
        URL url = new URL(ur);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);
//        connection.setRequestProperty("Cookie","JSESSIONID=DB3F0021AA24E8F7460E32DDF99AE9F6");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String len;
        while ((len = br.readLine()) != null) {
            System.out.println(len);
        }

        Map<String, List<String>> map = connection.getHeaderFields();
//            Map<String, List<String>> map2 = conn.getRequestProperties();

        System.out.println("显示响应Header信息...\n");

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() +
                    " ,Value : " + entry.getValue());
        }


        System.out.println("Authorization---->"+map.get("Authorization"));

        System.out.println("\n使用key获得响应Header信息 \n");
        List<String> server = map.get("Server");

        if (server == null) {
            System.out.println("Key 'Server' is not found!");
        } else {
            for (String values : server) {
                System.out.println(values);
            }
        }
    }

}

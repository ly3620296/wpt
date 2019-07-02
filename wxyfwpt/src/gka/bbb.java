package gka;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2019/6/16 0016.
 */
public class bbb implements Runnable {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 125; i++) {
            new Thread(new bbb()).start();
        }

    }

    public static String get() {
        String message = "";
        try {
            URL url = new URL("http://localhost:8080/wpt/index2.jsp");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5 * 1000);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            byte[] data = new byte[1024];
            StringBuffer sb = new StringBuffer();
            int length = 0;
            while ((length = inputStream.read(data)) != -1) {
                String s = new String(data, Charset.forName("utf-8"));
                sb.append(s);
            }
            message = sb.toString();
            inputStream.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("1111111111");
                Thread.sleep(2000);
                bbb.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

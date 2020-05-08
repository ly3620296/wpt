package gka.kit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;


public class HttpUtil {
    private static final int READ_TIMEOUT = 30 * 1000;
    private static final int CONNECT_TIMEOUT = 30 * 1000;

    public static void main(String[] args) throws Exception {
    }


    /**
     * POST请求
     */
    public static String doPost(String url) {
        OutputStream out = null;
        InputStream in = null;
        URLConnection urlConnection = null;
        try {
            urlConnection = new URL(url).openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            // 设置是否向httpUrlConnection输出，post请求，参数要放在http正文内，因此需要设为true,
            // 默认情况下是false;
            httpUrlConnection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpUrlConnection.setDoInput(true);
            // 忽略缓存
            httpUrlConnection.setUseCaches(false);
            // 设定请求的方法为"POST"，默认是GET
            httpUrlConnection.setRequestMethod("POST");
            //读超时时间
            httpUrlConnection.setReadTimeout(READ_TIMEOUT);
            httpUrlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpUrlConnection.connect();
            // 建立输入流，向指向的URL传入参数
            out = httpUrlConnection.getOutputStream();
            out.write("aa".getBytes());
            out.flush();
            out.close();
            // 获得响应状态
            int responseCode = httpUrlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

            }
            System.out.println(responseCode);
            byte[] buffer = new byte[1024];
            int len = 0;
            in = httpUrlConnection.getInputStream();
            while ((len = in.read(buffer)) != -1) {
                System.out.println(new String(buffer, "UTF-8"));
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {

        }
    }

    /**
     * GET请求
     */

    public static String doGet(String url) {
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        URLConnection urlConnection = null;
        try {
            urlConnection = new URL(url).openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            // 设置是否向httpUrlConnection输出，post请求，参数要放在http正文内，因此需要设为true,
            // 默认情况下是false;
            httpUrlConnection.setDoOutput(false);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpUrlConnection.setDoInput(true);
            // 忽略缓存
            httpUrlConnection.setUseCaches(false);
            // 设定请求的方法为"POST"，默认是GET
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.connect();
            // 获得响应状态
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            in = httpUrlConnection.getInputStream();
            while ((len = in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
                baos.flush();
            }
            return baos.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
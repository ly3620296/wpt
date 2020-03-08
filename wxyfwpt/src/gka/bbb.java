package gka;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2019/6/16 0016.
 */
public class bbb {


    private static bbb b = new bbb();
    static int i = 100;
    static int j;


    public bbb() {
        i++;
        j++;
        System.out.println(i);
        System.out.println(j);
//        i = i + 12;
    }


    public static void main(String[] args) {
//        bbb b = new bbb();
//        bbb b1 = new bbb();

        System.out.println(bbb.i);
        System.out.println(bbb.j);
//        System.out.println(bbb.geti());
    }
}

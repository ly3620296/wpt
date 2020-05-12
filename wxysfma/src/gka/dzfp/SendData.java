package gka.dzfp;

/**
 * Created by Administrator on 2020/5/12 0012.
 */
public class SendData implements Runnable {
    private String url;
    private String data;

    public SendData(String url, String data) {
        this.url = url;
        this.data = data;
    }

    @Override
    public void run() {

    }


}

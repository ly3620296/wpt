package gka.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class Servlet extends HttpServlet {

    public StringBuffer init(int limit, int page) {
        StringBuffer buffer = new StringBuffer("{\"code\": 0, \"msg\": \"\",\"count\": 500,\"data\":[");

        for (int i = limit * (page - 1); i < limit * page; i++) {
            if (i < limit * page - 1) {
                buffer.append("{");
                buffer.append("\"id\": " + i + ", ");
                buffer.append("\"username\": \"user-" + i + "\",");
                if (i % 2 == 0) {
                    buffer.append("\"sex\": \"女\", ");
                } else {
                    buffer.append("\"sex\": \"男\", ");
                }
                buffer.append("\"city\": \"城市-" + i + "\", ");
                buffer.append("\"sign\": \"签名-" + i + "\",");
                buffer.append("\"experience\": " + new Random().nextInt(1000) + ",");
                buffer.append("\"logins\": " + new Random().nextInt(500) + ", ");
                buffer.append("\"wealth\": " + new Random().nextInt(99999999) + ",");
                buffer.append("\"classify\": \"作家\",");
                buffer.append("\"score\": " + new Random().nextInt(100) + "");
                buffer.append("},");
            } else {
                buffer.append("{");
                buffer.append("\"id\": " + i + ", ");
                buffer.append("\"username\": \"user-" + i + "\",");
                if (i % 2 == 0) {
                    buffer.append("\"sex\": \"女\", ");
                } else {
                    buffer.append("\"sex\": \"男\", ");
                }
                buffer.append("\"city\": \"城市-" + i + "\", ");
                buffer.append("\"sign\": \"签名-" + i + "\",");
                buffer.append("\"experience\": " + new Random().nextInt(1000) + ",");
                buffer.append("\"logins\": " + new Random().nextInt(500) + ", ");
                buffer.append("\"wealth\": " + new Random().nextInt(99999999) + ",");
                buffer.append("\"classify\": \"作家\",");
                buffer.append("\"score\": " + new Random().nextInt(100) + "");
                buffer.append("}");
            }
        }
        buffer.append("]}");
        return buffer;
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        String limit = req.getParameter("limit");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String page = req.getParameter("page");
        out.print(init(Integer.parseInt(limit), Integer.parseInt(page)));
        out.flush();
        out.close();
    }

}

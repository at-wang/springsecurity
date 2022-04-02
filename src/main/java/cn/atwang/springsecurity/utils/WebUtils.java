package cn.atwang.springsecurity.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtils {
    public static String sendString(HttpServletResponse response, String s) {

        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

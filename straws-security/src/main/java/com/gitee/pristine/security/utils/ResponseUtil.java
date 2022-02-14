package com.gitee.pristine.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Pristine Xu
 * @date 2022/2/3 3:19
 * @description:
 */
public class ResponseUtil {

    // 默认的Json转换器
    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 响应数据
     * @param response
     * @param data
     * @throws IOException
     */
    public static <T> void writeJSON(HttpServletResponse response,
                                     HttpStatus status,
                                     @NonNull T data) throws IOException {
        // 构建响应数据
        String jsonResult = objectMapper.writeValueAsString(data);
        // 设置响应类型
        if (status != null) {
            response.setStatus(status.value());
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        // 输出
        PrintWriter pw = response.getWriter();
        pw.write(jsonResult);
        pw.flush();
        pw.close();
    }
}

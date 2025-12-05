package com.gzy.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 请求成功的响应体
    public static <T> void success(HttpServletResponse response, JSON jsonObject){
        PrintWriter out = null;
        try {
            // 确保响应头声明为JSON格式
            if (response.getContentType() == null || !response.getContentType().contains("application/json")) {
                response.setContentType("application/json;charset=utf-8");
            }
            
            // 确保不使用分块传输
            response.setHeader("Content-Length", "0");
            
            ResultVO<Object> resp = new ResultVO<>(jsonObject);
            // json返回
            out = response.getWriter();
            String jsonStr = JSONObject.toJSONString(resp, SerializerFeature.WriteMapNullValue);
            response.setHeader("Content-Length", String.valueOf(jsonStr.getBytes("UTF-8").length));
            out.write(jsonStr); // 响应体写入json对象
            out.flush();
        } catch (IOException e) { // 遇到错误则抛出异常
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
    // 请求失败的响应体
    public static void fail(HttpServletResponse response, ErrorCode errorCode){
        PrintWriter out = null;
        try {
            // 确保响应头声明为JSON格式
            if (response.getContentType() == null || !response.getContentType().contains("application/json")) {
                response.setContentType("application/json;charset=utf-8");
            }
            
            // 确保不使用分块传输
            response.setHeader("Content-Length", "0");
            
            ResultVO<Object> resp = new ResultVO<>();
            resp.setSuccess(false);
            resp.setCode(errorCode.getCode());
            resp.setMessage(errorCode.getMessage());
            // json返回
            out = response.getWriter();
            String jsonStr = JSONObject.toJSONString(resp);
            response.setHeader("Content-Length", String.valueOf(jsonStr.getBytes("UTF-8").length));
            out.write(jsonStr);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
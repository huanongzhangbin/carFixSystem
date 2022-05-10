package com.example.carfixsystem.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class JSONResult<T> extends ResponseEntity<T> {

    public JSONResult(HttpStatus status) {
        super(status);
    }

    public JSONResult(int code,T data) {
        super(data, HttpStatus.valueOf(code));
    }

    /**
     *
     * @param data
     * @param <T>
     * @return 返回成功的数据
     */
    public static <T> JSONResult<T> success(T data) {
        return new JSONResult(200, data);
    }


    public static <T> JSONResult<T> success(String msg) {
        HashMap<String,String> result=new HashMap<>();
        result.put("msg",msg);
        return new JSONResult(200, result);
    }

    public static <T> JSONResult<T> failMsg(String msg) {
        HashMap<String,String> result=new HashMap<>();
        result.put("msg",msg);
        return new JSONResult(202, result);
    }

    public static <T> JSONResult<T> custom(int code,String key,String value) {
        HashMap<String,String> result=new HashMap<>();
        result.put(key,value);
        return new JSONResult(code, result);
    }
    /**
     *
     * @param code
     * @param data 返回data
     * @param <T>
     * @return
     */
    public static <T> JSONResult<T> custom(int code,T data) {
        return new JSONResult(code,data);
    }
    public static <T> JSONResult<T> custom(int code, String msg,T data) {
        HashMap<String,Object> result=new HashMap();
        result.put("msg",msg);
        result.put("data",data);
        return new JSONResult(code,result);
    }
}


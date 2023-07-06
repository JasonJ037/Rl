package com.jhh.rl.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@Data
public class Result <T>{
    private Integer status;

    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    public Result(Integer status, String msg, T data, Integer count) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }
    private Result(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static <T> Result<T> ok(String msg, T data){
        return new Result<>(0,msg,data);
    }

    public static <T> Result<T> ok(String msg, T data,Integer count){
        return new Result<>(0,msg,data,count);
    }



    public static <T> Result<T> ok(String msg){
        return new Result<>(0,msg);
    }

    public static <T> Result<T> fail(String msg){
        return new Result<>(1,msg);
    }



}

package com.teradata.utils;

import com.teradata.model.common.Result;

/**
 * 统一返回格式
 * code为0时表示成功
 * 其他code，可自己定义
 */
public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success(Object object,Integer total){
        Result result = new Result();
        result.setCode(0);
        result.setTotal(total);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}

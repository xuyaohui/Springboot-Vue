package com.teradata.exp;

import com.teradata.model.common.Result;
import com.teradata.utils.ResultUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jimo on 17-12-12.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    public Result<String> handleException(HttpServletRequest req, HttpServletResponse res, Exception e) {
        return ResultUtil.error(-1,e.getMessage());
    }

}

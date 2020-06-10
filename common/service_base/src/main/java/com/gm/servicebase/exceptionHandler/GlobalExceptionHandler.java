package com.gm.servicebase.exceptionHandler;


import com.gm.commonutils.R;
import com.gm.servicebase.entity.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j//把异常信息输入到文件中
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)//指定出现什么异常执行该方法
    @ResponseBody
    public R error(Exception e){

        e.printStackTrace();

        return R.error().message("全局异常处理");

    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){


        e.printStackTrace();

        return R.error().message("特定异常处理，处理已知的存在的异常");

    }

    //自定义异常处理
    @ExceptionHandler(MyException.class)
    @ResponseBody()
    public R error(MyException e){

        log.error(e.getMessage());//把错误信息输入到文件
//        log.info(e.getMessage());
//        log.debug(e.getMessage());

        e.printStackTrace();

        return R.error().code(e.getCode()).message(e.getMsg());

    }

}

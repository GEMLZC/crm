package cn.wolfcode.crm.utils;

import cn.wolfcode.crm.domain.JsonResult;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义异常处理,(开发中不建议使用，开发完成再加上)
 */
@ControllerAdvice
public class CRMExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String runtimeException(Model model, Exception ex) {
        model.addAttribute("ex", ex);
        ex.printStackTrace();//打印异常信息
        return "common/error";
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ModelAndView unauthorizedException(Exception ex, HandlerMethod method,HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ex",ex);
        ex.printStackTrace();//打印异常信息
        //判断请求方式为异步请求
        if (method.getMethod().isAnnotationPresent(ResponseBody.class)){
            //设置响应头
            response.setContentType("text/json;charset=UTF-8");
            JsonResult json = new JsonResult();
            json.mark("没有该权限");
            //响应json数据
            response.getWriter().write(JSON.toJSONString(json));
            return null;
        }else {
            //普通请求方式
            modelAndView.setViewName("common/nopermission");
            return modelAndView;
        }
    }
}

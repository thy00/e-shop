package cn.thyonline.taotao.search.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
public class GlobalExceptionReslover implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //1、日志写入到日志文件
        System.out.println(ex.getMessage());
        ex.printStackTrace();
        //2、通知开发人员（短信、邮件）
        System.out.println("发短信");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("error/exception");
        modelAndView.addObject("message","您的网络有异常，请重试");
        return modelAndView;
    }
}

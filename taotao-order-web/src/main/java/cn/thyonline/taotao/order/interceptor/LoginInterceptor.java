package cn.thyonline.taotao.order.interceptor;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.common.utils.CookieUtils;
import cn.thyonline.taotao.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在订单提交之前进行身份校验
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserLoginService loginService;
    @Value("${TT_TOKEN_KEY}")
    private String TT_TOKEN_KEY;
    @Value("${SSO_URL_KEY}")
    private String SSO_URL_KEY;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、比较cookie信息判断是否登录
        String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
        //2、没有登录则转跳登录界面（带上URL使得登录之后转跳回来）
        if (StringUtils.isEmpty(token)){
            response.sendRedirect(SSO_URL_KEY+"/page/login?redirect="+request.getRequestURI().toString());
            return false;
        }
        //3、登录则放过
        TaotaoResult result = loginService.getUserByToken(token);
        if (result.getStatus()!=200){
            response.sendRedirect(SSO_URL_KEY+"/page/login?redirect="+request.getRequestURI().toString());
            return false;
        }
        //需要在request中这是用户信息
        request.setAttribute("USER_INFO",result.getData());
        return true;//可以执行其他拦截操作
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

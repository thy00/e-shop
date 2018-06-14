package cn.thyonline.taotao.sso.controller;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.common.utils.CookieUtils;
import cn.thyonline.taotao.common.utils.JsonUtils;
import cn.thyonline.taotao.pojo.TbUser;
import cn.thyonline.taotao.sso.service.UserLoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录
 */
@Controller
public class UserLoginController {

    @Value("${TT_TOKEN_KEY}")
    private String TT_TOKEN_KEY;
    @Resource
    private UserLoginService loginService;

    /**
     * 登录功能
     * @param request
     * @param response
     * @param user 储存登录名和密码
     * @return
     */
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(HttpServletRequest request, HttpServletResponse response, TbUser user){
        TaotaoResult result = loginService.login(user);
        //将登录信息储存到cookie中回显
        if (result.getStatus()==200){
            CookieUtils.setCookie(request,response,TT_TOKEN_KEY,result.getData().toString());
        }
        return result;
    }

    /**
     * 在下订单等操作时校验是否登录
     * @param token
     * @return
     */
    @RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public String getUserByToken(@PathVariable String token){
        TaotaoResult result = loginService.getUserByToken(token);
        System.out.println(JsonUtils.objectToJson(result));
        return JsonUtils.objectToJson(result);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @RequestMapping(value = "/user/logout/{token}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult logOut(@PathVariable String token){
        TaotaoResult result=loginService.logOut(token);
        return result;
    }
}

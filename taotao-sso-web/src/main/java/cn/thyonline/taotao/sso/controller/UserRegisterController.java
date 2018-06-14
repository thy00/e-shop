package cn.thyonline.taotao.sso.controller;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.pojo.TbUser;
import cn.thyonline.taotao.sso.service.UserRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 单点登录
 */
@Controller
public class UserRegisterController {
    @Resource
    private UserRegisterService service;

    /**
     * 核对注册数据
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value="/user/check/{param}/{type}",method=RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkData(@PathVariable String param, @PathVariable Integer type){
        return service.checkData(param,type);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user){
        return service.register(user);
    }
}

package cn.thyonline.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制页面转跳
 */
@Controller
public class PageController {
    /**
     * 转跳注册或者登陆页面
     * @param page
     * @return
     */
    @RequestMapping("/page/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}

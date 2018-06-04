package cn.thyonline.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制
 */
@Controller
public class PageController {

    /**
     * 展示首页
     * @return
     */
    @RequestMapping("/index")
    public String showIndex(){
        return "/index";
    }
}

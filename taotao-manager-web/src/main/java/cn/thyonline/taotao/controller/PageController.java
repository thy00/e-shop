package cn.thyonline.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    /**
     * 首页
     * @return
     */
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/{page}")
    public String showItemList(@PathVariable("page") String page){
        return page;
    }

    @RequestMapping("/rest/page/{page}")
    public String showItempageList(@PathVariable("page") String page){
        return page;
    }
}

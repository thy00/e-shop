package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestController {
    @Resource
    private TestService testservice;
    /**
     * 测试dubbo配置是否正常
     * @return
     */
    @RequestMapping("/test/queryNow")
    @ResponseBody
    public String queryNow(){
        return testservice.queryNow();
    }
}


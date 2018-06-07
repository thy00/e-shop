package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.search.service.SearchItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class SearchItemController {

    @Resource
    private SearchItemService service;
    /**
     * url:/index/import
     * 参数：不需要
     * 返回值TaotaoResult
     */
    @RequestMapping("/index/import")
    @ResponseBody
    public TaotaoResult importAllItems(){
        try {
            service.importAllItems();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,"导入数据失败");
        }
        return TaotaoResult.ok();
    }
}

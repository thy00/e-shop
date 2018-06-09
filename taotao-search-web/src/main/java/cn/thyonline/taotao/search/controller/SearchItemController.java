package cn.thyonline.taotao.search.controller;

import cn.thyonline.taotao.common.pojo.SearchResult;
import cn.thyonline.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class SearchItemController {
    @Value("${ITEM_ROWS}")
    private Integer ITEM_ROWS;
    @Resource
    private SearchItemService service;
    /**
     * url:/search
     * 参数：q、page
     * 返回值：modelAndView
     */
    @RequestMapping("/search")
    public String searchItem(@RequestParam("q") String queryStr, @RequestParam(defaultValue = "1") Integer page, Model model)throws Exception{
            queryStr=new String(queryStr.getBytes("iso8859-1"),"utf-8");
//            int i=1/0;
            SearchResult result = service.searchItem(queryStr, page, ITEM_ROWS);
            model.addAttribute("query",queryStr);
            model.addAttribute("totalPages", result.getPageCount());
            model.addAttribute("itemList", result.getItemList());
            System.out.println("数据的数量是："+result.getItemList().size());
            model.addAttribute("page", page);
        return "search";
    }
}

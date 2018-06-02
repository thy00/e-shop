package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ItemController {

    @Resource
    private ItemService itemService;
    /**
     * 响应商品列表
     */
    @RequestMapping(value="/item/list",method=RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
//        System.out.println("页面传来的多少页"+page+"多少行"+rows);
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
//        System.out.println("传出去的多少行"+result.getRows().size());
        return result;
    }
}

package cn.thyonline.taotao.item.controller;

import cn.thyonline.taotao.item.pojo.Item;
import cn.thyonline.taotao.pojo.TbItem;
import cn.thyonline.taotao.pojo.TbItemDesc;
import cn.thyonline.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class ItemController {

    @Resource
    private ItemService service;
    /**
     * url:/item/{itemId}
     *参数：id
     * 返回值：逻辑视图
     */
    @RequestMapping("/item/{itemId}")
    public String getItem(Long itemId, Model model){
        TbItem item=service.getItemById(itemId);
        TbItemDesc itemDesc=service.getItemDescById(itemId);
        //由于TbItem里面图片是“,”，使用时取不出来，则重新封装一个pojo
        Item item1=new Item(item);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);
        return "item";
    }
}

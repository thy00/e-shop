package cn.thyonline.taotao.item.controller;

import cn.thyonline.taotao.item.pojo.Item;
import cn.thyonline.taotao.pojo.TbItem;
import cn.thyonline.taotao.pojo.TbItemDesc;
import cn.thyonline.taotao.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String getItem(@PathVariable("itemId") Long itemId, Model model){
        System.out.println("商品的id是："+itemId);
        TbItem item=service.getItemById(itemId);
        TbItemDesc itemDesc=service.getItemDescById(itemId);
        //由于TbItem里面图片是“,”，使用时取不出来，则重新封装一个pojo
        Item item1=null;
        if (item!=null){
            System.out.println("商品的名字是："+item.getTitle());
            item1=new Item(item);
        }
        model.addAttribute("item",item1);
        model.addAttribute("itemDesc",itemDesc);
        return "item";
    }
}

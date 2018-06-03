package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.pojo.TbItem;
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

    /**
     * url：/item/save
     * 接收参数TbItem和商品描述desc（因为文件比较大独立出了一个表）
     * 返回数据TaotaoResult
     */
    @RequestMapping("/item/save")
    @ResponseBody
    public TaotaoResult saveItem(TbItem item,String desc){
        TaotaoResult result = itemService.saveItem(item, desc);
        return result;
    }
    /**
     * url:/rest/item/delete
     * 接收参数：商品ID---ids
     * 返回参数TaotaoResult
     */
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public TaotaoResult deleteItem(String ids){
        System.out.println("商品id为："+ids);
        TaotaoResult result = itemService.deleteItem(Long.valueOf(ids));
        return result;
    }
}

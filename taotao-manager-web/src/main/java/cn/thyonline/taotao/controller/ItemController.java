package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.pojo.TbItem;
import cn.thyonline.taotao.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
     * 保存商品
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
     * 删除商品
     * url:/rest/item/delete
     * 接收参数：商品ID---ids
     * 返回参数TaotaoResult
     */
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public TaotaoResult deleteItem(String ids){
//        System.out.println("商品id为："+ids);
        TaotaoResult result = itemService.deleteItem(Long.valueOf(ids));
        return result;
    }
    /**
     * url:/rest/item/query/item/desc/{id}
     * 接收参数：商品ID
     * 返回参数TaotaoResult
     */
    @RequestMapping("/rest/item/query/item/desc/{id}")
    @ResponseBody
    public TaotaoResult toEditItemDesc(@PathVariable(value = "id") Long id){
//        System.out.println("商品id为："+id);
        TaotaoResult result = itemService.toEditItemDesc(id);
        return result;
    }
    /**
     * url:/rest/item/param/item/query/{id}
     * 接收参数：商品ID
     * 返回参数TaotaoResult
     */
    @RequestMapping("/rest/item/param/item/query/{id}")
    @ResponseBody
    public TaotaoResult toEditItemQuery(@PathVariable(value = "id") Long id){
//        System.out.println("商品id为："+id);
        TaotaoResult result = itemService.toEditItemDesc(id);
        return result;
    }
    /**
     * 商品编辑
     * url：/rest/item/update
     * 接收参数TbItem和商品描述desc（因为文件比较大独立出了一个表）
     * 返回数据TaotaoResult
     */
    @RequestMapping("/rest/item/update")
    @ResponseBody
    public TaotaoResult updateItem(TbItem item,String desc){
//        System.out.println("商品信息："+item.getTitle()+"商品详细信息："+desc);
        TaotaoResult result = itemService.updateItem(item, desc);
        return result;
    }
    /**
     * 商品下架
     * url:/rest/item/instock
     * 接收参数：商品ID---ids
     * 返回参数TaotaoResult
     */
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public TaotaoResult instockItem(String ids){
        System.out.println("商品id为："+ids);
        TaotaoResult result = itemService.instockItem(Long.valueOf(ids));
        return result;
    }
    /**
     * 商品上架
     * url:/rest/item/reshelf
     * 接收参数：商品ID---ids
     * 返回参数TaotaoResult
     */
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public TaotaoResult reshelfItem(String ids){
        System.out.println("商品id为："+ids);
        TaotaoResult result = itemService.reshelfItem(Long.valueOf(ids));
        return result;
    }
}

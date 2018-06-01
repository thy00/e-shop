package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.service.ItemService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class ItemController {

    @Resource
    private ItemService itemService;
    /**
     * 响应商品列表
     */
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }
}

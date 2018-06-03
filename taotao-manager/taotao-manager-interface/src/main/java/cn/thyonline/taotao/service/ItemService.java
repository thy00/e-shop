package cn.thyonline.taotao.service;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.pojo.TbItem;

/**
 * 商品
 */
public interface ItemService {
    EasyUIDataGridResult getItemList(Integer page,Integer rows);
    /*保存商品信息
     * 接收参数TbItem和商品描述（因为文件比较大独立出了一个表）
     * 返回数据TaotaoResult
     */
    TaotaoResult saveItem(TbItem item,String desc);
    /**
     * url:/rest/item/delete
     * 接收参数：商品ID
     * 返回参数TaotaoResult
     */
    TaotaoResult deleteItem(Long id);
}

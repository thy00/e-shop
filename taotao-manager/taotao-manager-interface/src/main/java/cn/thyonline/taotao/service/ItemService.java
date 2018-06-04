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
    /**
     * url:/rest/page/item-edit
     * 接收参数：商品ID---ids
     * 返回参数TaotaoResult
     */
    TaotaoResult toEditItemDesc(Long id);
    /**
     * url:/rest/item/param/item/query/{id}
     * 接收参数：商品ID
     * 返回参数TaotaoResult
     */
    TaotaoResult toEditItemQuery(Long id);
    /**
     * url：/rest/item/update
     * 接收参数TbItem和商品描述desc（因为文件比较大独立出了一个表）
     * 返回数据TaotaoResult
     */
    TaotaoResult updateItem(TbItem item,String desc);
    /**
     * 商品下架
     * url:/rest/item/instock
     * 接收参数：商品ID---ids
     * 返回参数TaotaoResult
     */
    public TaotaoResult instockItem(Long id);
    /**
     * 商品上架
     * url:/rest/item/reshelf
     * 接收参数：商品ID---ids
     * 返回参数TaotaoResult
     */
    public TaotaoResult reshelfItem(Long id);
}

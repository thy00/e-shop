package cn.thyonline.taotao.service;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;

/**
 * 商品
 */
public interface ItemService {
    EasyUIDataGridResult getItemList(Integer page,Integer rows);
}

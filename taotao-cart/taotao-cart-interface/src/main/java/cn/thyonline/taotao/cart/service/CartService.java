package cn.thyonline.taotao.cart.service;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.pojo.TbItem;

import java.util.List;

/**
 * 购物车
 */
public interface CartService {
    /**
     * 添加购物车
     * @param userId
     * @param item
     * @param num 购买商品数量
     * @return
     */
    TaotaoResult addItemCart(Long userId, TbItem item,Integer num);

    /**
     * 查询redis中是否存在该用户的该商品
     * @param userId
     * @param itemId
     * @return
     */
    TbItem queryTbItemByUserIdAndItemId(Long userId,Long itemId);

    /**
     * 查询redis中该用户的商品
     * @param userId
     * @return
     */
    List<TbItem> queryTbItemByUserId(Long userId);

}

package cn.thyonline.taotao.order.service;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.order.pojo.OrderInfo;

/**
 * 订单
 */
public interface OrderService {
    /**
     * 创建订单
     * @param info
     * @return
     */
    TaotaoResult createOrder(OrderInfo info);
}

package cn.thyonline.taotao.order.pojo;

import cn.thyonline.taotao.pojo.TbOrder;
import cn.thyonline.taotao.pojo.TbOrderItem;
import cn.thyonline.taotao.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * 将订单、商品、物流信息整合形成pojo
 */
public class OrderInfo extends TbOrder implements Serializable {
    private List<TbOrderItem> orderItems;//名字和页面统一
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}

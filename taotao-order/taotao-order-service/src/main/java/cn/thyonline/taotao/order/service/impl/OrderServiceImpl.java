package cn.thyonline.taotao.order.service.impl;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.mapper.TbOrderItemMapper;
import cn.thyonline.taotao.mapper.TbOrderMapper;
import cn.thyonline.taotao.mapper.TbOrderShippingMapper;
import cn.thyonline.taotao.order.jedis.JedisClient;
import cn.thyonline.taotao.order.pojo.OrderInfo;
import cn.thyonline.taotao.order.service.OrderService;
import cn.thyonline.taotao.pojo.TbOrderItem;
import cn.thyonline.taotao.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("${GEN_ORDER_ID_KEY}")
    private String GEN_ORDER_ID_KEY;
    @Value("${GEN_ORDER_ID_INIT}")
    private String GEN_ORDER_ID_INIT;
    @Value("${GEN_ORDER_ID_ITEM_KEY}")
    private String GEN_ORDER_ID_ITEM_KEY;

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient client;
    @Override
    public TaotaoResult createOrder(OrderInfo info) {
        //1、在插入订单表，将订单信息写入数据库的同时写入缓存
        if (!client.exists(GEN_ORDER_ID_KEY)){
            client.set(GEN_ORDER_ID_KEY,GEN_ORDER_ID_INIT);
        }
        String orderId = client.incr(GEN_ORDER_ID_KEY).toString();//将值自增
        Date date = new Date();
        info.setOrderId(orderId);
        info.setCreateTime(date);
        info.setUpdateTime(date);
        info.setPostFee("0");
        info.setStatus(1);
        orderMapper.insert(info);//将数据写入数据库
        //2、订单商品
        List<TbOrderItem> orderItems = info.getOrderItems();
        for (TbOrderItem item:orderItems){
            String itemId = client.incr(GEN_ORDER_ID_ITEM_KEY).toString();//从0开始自增
            item.setId(itemId);
            item.setOrderId(orderId);
            orderItemMapper.insert(item);//将数据写入数据库
        }
        //3、物流信息
        TbOrderShipping orderShipping = info.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShippingMapper.insert(orderShipping);//将数据写入数据库
        return TaotaoResult.ok(orderId);
    }
}

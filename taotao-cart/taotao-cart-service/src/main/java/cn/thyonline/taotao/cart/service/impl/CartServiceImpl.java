package cn.thyonline.taotao.cart.service.impl;

import cn.thyonline.taotao.cart.jedis.JedisClient;
import cn.thyonline.taotao.cart.service.CartService;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.common.utils.JsonUtils;
import cn.thyonline.taotao.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private JedisClient client;
    @Value("${TT_CART_REDIS_PRE_KEY}")
    private String TT_CART_REDIS_PRE_KEY;

    @Override
    public List<TbItem> queryTbItemByUserId(Long userId) {
        Map<String, String> map = client.hgetAll(TT_CART_REDIS_PRE_KEY + ":" + userId);
        Set<Map.Entry<String, String>> set = map.entrySet();
        if (set!=null){
            List<TbItem> items=new ArrayList<>();
            for (Map.Entry<String,String> entry:set){
                TbItem item = JsonUtils.jsonToPojo(entry.getValue(), TbItem.class);//得到商品信息
                items.add(item);
            }
            return items;
        }
        return null;
    }

    @Override
    public TaotaoResult addItemCart(Long userId, TbItem item, Integer num) {
        //1、从redis中查询数据
        TbItem tbItem = queryTbItemByUserIdAndItemId(userId, item.getId());
        //2、判断数据库中是否有数据
        if (tbItem==null){
            //添加商品
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)){
                item.setImage(image.split(",")[0]);
            }
            item.setNum(num);
            client.hset(TT_CART_REDIS_PRE_KEY+":"+userId, String.valueOf(item.getId()),JsonUtils.objectToJson(item));
        }
        tbItem.setNum(tbItem.getNum()+num);
        client.hset(TT_CART_REDIS_PRE_KEY+":"+userId, String.valueOf(tbItem.getId()),JsonUtils.objectToJson(tbItem));
        return TaotaoResult.ok();
    }

    @Override
    public TbItem queryTbItemByUserIdAndItemId(Long userId, Long itemId) {
        //在redis中查找数据
        String s = client.hget(TT_CART_REDIS_PRE_KEY + ":" + userId, String.valueOf(itemId));
        if (StringUtils.isNotBlank(s)){
            TbItem item = JsonUtils.jsonToPojo(s, TbItem.class);
            return item;
        }
        return null;
    }
}


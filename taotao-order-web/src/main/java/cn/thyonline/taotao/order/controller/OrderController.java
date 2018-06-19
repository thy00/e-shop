package cn.thyonline.taotao.order.controller;

import cn.thyonline.taotao.cart.service.CartService;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.order.pojo.OrderInfo;
import cn.thyonline.taotao.order.service.OrderService;
import cn.thyonline.taotao.pojo.TbItem;
import cn.thyonline.taotao.pojo.TbUser;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单系统
 */
@Controller
public class OrderController {
    @Resource
    private CartService cartService;
    @Resource
    private OrderService orderService;

    /**
     * 确认订单页面
     * @param request
     * @return
     */
    @RequestMapping("/order/order-cart")
    public String showOrder(HttpServletRequest request){
        //需要获得商品信息
        TbUser user = (TbUser) request.getAttribute("USER_INFO");
        List<TbItem> items = cartService.queryTbItemByUserId(user.getId());
        request.setAttribute("cartList",items);
        return "order-cart";
    }

    /**
     * 创建新的订单
     * @param info
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo info,HttpServletRequest request){//获得订单信息和登录信息
        TbUser user = (TbUser) request.getAttribute("USER_INFO");
        //将info信息补上用户信息
        info.setUserId(user.getId());
        info.setBuyerNick(user.getUsername());
        TaotaoResult result = orderService.createOrder(info);//写入数据库
        request.setAttribute("orderId",result.getData());
        DateTime dateTime = new DateTime();
        DateTime plusDays = dateTime.plusDays(3);
        request.setAttribute("date",plusDays);
        return "success";
    }

}

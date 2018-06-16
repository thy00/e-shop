package cn.thyonline.taotao.order.controller;

import cn.thyonline.taotao.cart.service.CartService;
import cn.thyonline.taotao.pojo.TbItem;
import cn.thyonline.taotao.pojo.TbUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

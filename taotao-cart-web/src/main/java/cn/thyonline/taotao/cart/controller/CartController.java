package cn.thyonline.taotao.cart.controller;

import cn.thyonline.taotao.cart.service.CartService;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.common.utils.CookieUtils;
import cn.thyonline.taotao.common.utils.JsonUtils;
import cn.thyonline.taotao.pojo.TbItem;
import cn.thyonline.taotao.pojo.TbUser;
import cn.thyonline.taotao.service.ItemService;
import cn.thyonline.taotao.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 */
@Controller
public class CartController {
    @Resource
    private UserLoginService loginService;
    @Resource
    private ItemService itemService;
    @Resource
    private CartService cartService;
    @Value("${TT_TOKEN_KEY}")
    private String TT_TOKEN_KEY;
    @Value("${TT_CART_KEY}")
    private String TT_CART_KEY;
    /**
     * 将商品添加到购物车
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response){
        //1、获得用户信息
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN_KEY");
        TaotaoResult result = loginService.getUserByToken(token);
        //2、判断是否登录
        TbItem item = itemService.getItemById(itemId);
        if (result.getStatus()==200){
            TbUser user = (TbUser) result.getData();
            cartService.addItemCart(user.getId(), item, num);
        }else {
            //未登录存放于本地cookie
            List<TbItem> items = getCookieCartList(request);//获得本地原有cookie
            boolean flag = false;
            for (TbItem item1 : items) {
                //找到相同商品与没找到
                if (itemId.equals(item1.getId())) {
                    item1.setNum(item1.getNum() + num);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                //修改数据还需要将信息返回
                CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(items), 3600 * 24 * 7, true);
            } else {
                //直接添加，需要设置参数
                item.setNum(num);
                if (item.getImage() != null) {
                    item.setImage(item.getImage().split(",")[0]);
                }
                items.add(item);
                CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(items), 3600 * 24 * 7, true);
            }
        }
        return "cartSuccess";
    }


    /**
     * 查询购物车
     * @param request 获得cookie信息
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCart(HttpServletRequest request, Model model){
        //判断是否登录，并分情况处理
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN_KEY");
        if (StringUtils.isNotBlank(token)){
            TaotaoResult result = loginService.getUserByToken(token);
            if (result.getStatus()==200){//已经登录的情况
                TbUser user = (TbUser) result.getData();
                List<TbItem> items = cartService.queryTbItemByUserId(user.getId());
                model.addAttribute("cartList",items);
            }else {//没有登录
                List<TbItem> items = getCookieCartList(request);
                model.addAttribute("cartList",items);
            }
        }
        return "cart";
    }

    private List<TbItem> getCookieCartList(HttpServletRequest request) {
        String cookieValue = CookieUtils.getCookieValue(request, TT_CART_KEY, true);
        if (StringUtils.isNotBlank(cookieValue)){
            List<TbItem> items = JsonUtils.jsonToList(cookieValue, TbItem.class);
            return items;
        }
        return new ArrayList<>();
    }

}

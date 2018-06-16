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
import org.springframework.web.bind.annotation.ResponseBody;

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
        String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
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
        String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
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
        }else {
            List<TbItem> items = getCookieCartList(request);
            model.addAttribute("cartList",items);
        }
        return "cart";
    }

    /**
     * 修改购物车
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateCartItemByItemId(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request, HttpServletResponse response){
        //1、判断是否登录，并分情况处理
        String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
        TaotaoResult cartResult=null;
        if (StringUtils.isNotBlank(token)){
            TaotaoResult result = loginService.getUserByToken(token);
            if (result.getStatus()==200){//登录的情况
                TbUser user=(TbUser) result.getData();
                cartResult = cartService.updateCartItemByItemId(user.getId(), itemId, num);
            }else {
                cartResult=updateItemCartCookie(itemId,num,request,response);
            }
        }else {
            cartResult=updateItemCartCookie(itemId,num,request,response);
        }
        return cartResult;
    }

    /**
     * 删除购物车商品
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String  deleteCartItemByItemId(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
        //1、判断是否登录，并分情况处理
        String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
        if (StringUtils.isNotBlank(token)){
            TaotaoResult result = loginService.getUserByToken(token);
            if (result.getStatus()==200){//登录成功
                TbUser user=(TbUser) result.getData();
                cartService.deleteByItemId(user.getId(), itemId);
            }else {
                deleteItemCartCookie(itemId,request,response);
            }
        }else {
            deleteItemCartCookie(itemId,request,response);
        }
        return "redirect:/cart/cart.html";
    }

    private TaotaoResult deleteItemCartCookie(Long itemId, HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("商品标题是："+itemId);
        //获取商品列表
        List<TbItem> items = getCookieCartList(request);
        boolean flag=false;
        for (TbItem item:items){
            if (item.getId()==itemId.longValue()){
//                System.out.println("已经查到商品！");
                items.remove(item);//删除商品
                flag=true;
                break;
            }
        }
//        System.out.println("商品列表长度是："+items.size());
        if (flag){
            CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(items), 3600 * 24 * 7, true);
        }else {
            return TaotaoResult.build(400,"添加购物车失败");
        }
        return TaotaoResult.ok();
    }


    private TaotaoResult updateItemCartCookie(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        //获取商品列表
        List<TbItem> items = getCookieCartList(request);
        boolean flag=false;
        for (TbItem item:items){
            if (item.getId()==itemId.longValue()){
                item.setNum(num);
                flag=true;
                break;
            }
        }
        if (flag){
            CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(items), 3600 * 24 * 7, true);
        }else {
            return TaotaoResult.build(400,"添加购物车失败");
        }
        return TaotaoResult.ok();
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

package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.utils.JsonUtils;
import cn.thyonline.taotao.content.service.ContentService;
import cn.thyonline.taotao.pojo.Ad1Node;
import cn.thyonline.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面跳转控制
 */
@Controller
public class PageController {

    /**
     * 数据
     * {"srcB":"http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg",
     * "height":240,
     * "alt":"",
     * "width":670,
     * "src":"http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg",
     * "widthB":550,
     * "href":"http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE",
     * "heightB":240},
     */
    //注入
    @Resource
    private ContentService service;

    @Value("${AD1_CATEGORYID}")
    private Long AD1_CATEGORYID;
    @Value("${AD1_WIDTH}")
    private String AD1_WIDTH;
    @Value("${AD1_WIDTHB}")
    private String AD1_WIDTHB;
    @Value("${AD1_HEIGHT}")
    private String AD1_HEIGHT;
    @Value("${AD1_HEIGHTB}")
    private String AD1_HEIGHTB;
    /**
     * 展示首页
     * @return
     */
    @RequestMapping("/index")
    public String showIndex(Model model){
        //调用service得到list集合
        List<TbContent> contents = service.getContentListById(AD1_CATEGORYID);
        //转换成ad1的list集合（补全数据）
        List<Ad1Node> nodes=new ArrayList<>();
        for (TbContent content:contents){
            Ad1Node node=new Ad1Node();
            node.setAlt(content.getSubTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHTB);
            node.setHref(content.getUrl());
            node.setSrc(content.getPic());
            node.setSrcB(content.getPic2());
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTHB);
            nodes.add(node);
        }
        //转换成json对象
        String json = JsonUtils.objectToJson(nodes);
        model.addAttribute("ad1",json);
        return "/index";
    }
}

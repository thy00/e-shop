package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.pojo.EasyUITreeNode;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.content.service.ContentCatService;
import cn.thyonline.taotao.pojo.TbContentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 内容分类管理
 */
@Controller
public class ContentCatController {

    @Resource
    private ContentCatService service;
    /**
     * 查询内容服务分类
     * url:/content/category/list
     * 参数：id
     * 返回：EasyUITreeNode的list集合
     */
    @RequestMapping(value = "/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> nodes = service.getContentCatList(parentId);
        return nodes;
    }

    /**
     * 查询内容服务
     * url:/content/query/list
     * 参数：categoryId
     * 返回：EasyUITreeNode的list集合
     */

    /**
     * 添加内容服务分类
     * url:/content/category/create
     * 参数：parentId，name
     * 返回：TaotaoResult
     */
    @RequestMapping("/content/category/create")
    @ResponseBody
    public TaotaoResult createContentCat(TbContentCategory category){
        System.out.println("id为："+category.getParentId()+"名字为："+category.getName());
        TaotaoResult result = service.createContentCat(category);
        return  result;
    }


}

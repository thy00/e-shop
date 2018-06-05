package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.content.service.ContentService;
import cn.thyonline.taotao.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ContentController {

    @Resource
    private ContentService service;
    /**
     * 查询内容服务
     * url:/content/query/list
     * 参数：categoryId
     * 返回：EasyUITreeNode的list集合
     */
    @RequestMapping(value="/content/query/list",method=RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult getContentList(Integer page, Integer rows,Integer categoryId){
//        System.out.println("页面传来的多少页"+page+"多少行"+rows+"categoryId"+categoryId);
        EasyUIDataGridResult result = service.getContentList(page, rows,categoryId);
//        System.out.println("传出去的多少行"+result.getRows().size());
        return result;
    }

    /**
     * 保存内容服务
     * url:/content/save
     * 参数：TbContent
     * 返回：TaotaoResult
     */
    @RequestMapping("/content/save")
    @ResponseBody
    public TaotaoResult saveContent(TbContent content){
        TaotaoResult result = service.saveContent(content);
        return result;
    }

    /**
     * 内容编辑
     * url：/rest/content/edit
     * 参数：TbContent
     * 返回数据TaotaoResult
     */
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public TaotaoResult updateContent(TbContent content){
//        System.out.println("商品信息："+item.getTitle()+"商品详细信息："+desc);
        TaotaoResult result = service.updateContent(content);
        return result;
    }

    /**
     * 内容删除
     * url:/content/delete
     * 接收参数：内容ID---ids
     * 返回参数TaotaoResult
     */
    @RequestMapping("/content/delete")
    @ResponseBody
    public TaotaoResult deleteContent(String ids){
//        System.out.println("商品id为："+ids);
        TaotaoResult result = service.deleteContent(Long.valueOf(ids));
        return result;
    }
}

package cn.thyonline.taotao.content.service;


import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.pojo.TbContent;

import java.util.List;

public interface ContentService {
    /**
     * 查询内容服务
     * url:/content/query/list
     * 参数：categoryId
     * 返回：EasyUITreeNode的list集合
     */
    EasyUIDataGridResult getContentList(Integer page, Integer rows, Integer categoryId);
    /**
     * 保存内容服务
     * url:/content/save
     * 参数：TbContent
     * 返回：TaotaoResult
     */
    TaotaoResult saveContent(TbContent content);

    /**
     * 内容编辑
     * url：/rest/content/edit
     * 参数：TbContent
     * 返回数据TaotaoResult
     */
    TaotaoResult updateContent(TbContent content);

    /**
     * 内容删除
     * url:/content/delete
     * 接收参数：内容ID---ids
     * 返回参数TaotaoResult
     */
    TaotaoResult deleteContent(Long id);
    /**
     * 门户页面大广告显示
     * url:/index
     * 接收参数：categoryId
     * 返回参数List<TbContent>
     */
    List<TbContent> getContentListById(Long categoryId);
}

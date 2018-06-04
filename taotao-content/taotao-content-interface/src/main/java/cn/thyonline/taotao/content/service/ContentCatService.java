package cn.thyonline.taotao.content.service;

import cn.thyonline.taotao.common.pojo.EasyUITreeNode;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.pojo.TbContentCategory;

import java.util.List;

public interface ContentCatService {
    /**
     * url:/content/category/list
     * 参数：id
     * 返回：EasyUITreeNode的list集合
     */
    List<EasyUITreeNode> getContentCatList(Long parentId);
    /**
     * 添加内容服务分类
     * url:/content/category/create
     * 参数：parentId，name
     * 返回：TaotaoResult
     */
    TaotaoResult createContentCat(TbContentCategory category);
}

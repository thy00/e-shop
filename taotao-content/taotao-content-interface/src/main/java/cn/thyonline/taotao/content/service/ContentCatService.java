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
    /**
     * 修改内容服务分类
     * url:/content/category/update
     * 参数：parentId，name（传参的时候包装成pojo）
     * 返回：TaotaoResult
     */
    public TaotaoResult updateContentCat(TbContentCategory category);

    /**
     * 删除内容服务分类
     * url:/content/category/delete/
     * 参数：id
     * 返回：TaotaoResult
     */
    public TaotaoResult deleteContentCat(Long id);
}

package cn.thyonline.taotao.service;

import cn.thyonline.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    //根据父节点ID查询EasyUITreeNode
    List<EasyUITreeNode> getItemCatList(Long parentId);
}

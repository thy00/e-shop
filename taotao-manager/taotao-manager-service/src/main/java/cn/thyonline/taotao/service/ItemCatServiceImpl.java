package cn.thyonline.taotao.service;

import cn.thyonline.taotao.common.pojo.EasyUITreeNode;
import cn.thyonline.taotao.mapper.TbItemCatMapper;
import cn.thyonline.taotao.pojo.TbItemCat;
import cn.thyonline.taotao.pojo.TbItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService{

    @Autowired
    private TbItemCatMapper mapper;
    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        //1、根据parentId查询节点列表
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> itemCats = mapper.selectByExample(example);
        //2、转换成EasyUITreeNode对象
        List<EasyUITreeNode> nodes=new ArrayList<>();
        for (TbItemCat cat:itemCats){
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(cat.getId());
            node.setState(cat.getIsParent()?"closed":"open");
            node.setText(cat.getName());
            nodes.add(node);
        }
        return nodes;
    }
}

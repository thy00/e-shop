package cn.thyonline.taotao.content.service.Impl;

import cn.thyonline.taotao.common.pojo.EasyUITreeNode;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.content.service.ContentCatService;
import cn.thyonline.taotao.mapper.TbContentCategoryMapper;
import cn.thyonline.taotao.pojo.TbContentCategory;
import cn.thyonline.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCatServiceImpl implements ContentCatService {

    @Autowired
    private TbContentCategoryMapper mapper;
    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        //使用parentId查询出ContentCategory的集合
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> contentCategories = mapper.selectByExample(example);
        //将集合转换成EasyUITreeNode对象
        List<EasyUITreeNode> nodes=new ArrayList<>();
        for (TbContentCategory contentCategory:contentCategories){
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setState(contentCategory.getIsParent()?"closed":"open");
            node.setText(contentCategory.getName());
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public TaotaoResult createContentCat(TbContentCategory category) {
        //补全信息，并添加ContentCategory
        Date date=new Date();
        category.setCreated(date);
        category.setUpdated(date);
        category.setStatus(1);
        category.setIsParent(false);
        category.setSortOrder(1);
        mapper.insert(category);
        //如果父分类是叶分类则修改
        TbContentCategory contentCategory = mapper.selectByPrimaryKey(category.getParentId());
        if (!contentCategory.getIsParent()){
            contentCategory.setIsParent(true);
            contentCategory.setUpdated(date);
            mapper.updateByPrimaryKeySelective(contentCategory);
        }
        return TaotaoResult.ok();
    }
}

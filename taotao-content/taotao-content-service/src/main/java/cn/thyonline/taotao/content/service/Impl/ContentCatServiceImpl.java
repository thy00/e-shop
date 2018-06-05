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
        return TaotaoResult.build(200,"创建成功",category);
    }

    @Override
    public TaotaoResult updateContentCat(TbContentCategory category) {
        //补全信息
        category.setUpdated(new Date());
        //更新contentCategory
        mapper.updateByPrimaryKeySelective(category);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContentCat(Long id) {
        //判断是不是父节点，如果是父节点则不允许删除
        TbContentCategory category = mapper.selectByPrimaryKey(id);
        if (category.getIsParent())return TaotaoResult.build(405,"该节点为父节点不允许删除");
        mapper.deleteByPrimaryKey(id);
        //判断该删除节点是不是父节点唯一节点，如果是的话需要修改isparent属性
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(category.getParentId());
        List<TbContentCategory> categories = mapper.selectByExample(example);//判断集合是否为空
        System.out.println("是否为空："+categories.isEmpty());
        if (categories.isEmpty()){
            System.out.println("分类信息："+category.getParentId());
            TbContentCategory contentCategory=new TbContentCategory();
            if (category.getParentId()!=0) {
                contentCategory.setId(category.getParentId());
                contentCategory.setIsParent(false);
                contentCategory.setUpdated(new Date());
                mapper.updateByPrimaryKeySelective(contentCategory);
            }
        }
        return TaotaoResult.ok();
    }
}

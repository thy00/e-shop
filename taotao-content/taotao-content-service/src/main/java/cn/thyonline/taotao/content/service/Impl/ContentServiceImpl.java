package cn.thyonline.taotao.content.service.Impl;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.content.service.ContentService;
import cn.thyonline.taotao.mapper.TbContentMapper;
import cn.thyonline.taotao.pojo.TbContent;
import cn.thyonline.taotao.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    //注入
    @Autowired
    private TbContentMapper mapper;
    @Override
    public EasyUIDataGridResult getContentList(Integer page, Integer rows, Integer categoryId) {
        if (page==null) page=1;
        if (rows==null) rows=20;
        //2 设置分页
        PageHelper.startPage(page,rows);
        //3 执行SQL
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(Long.valueOf(categoryId));
        List<TbContent> contents = mapper.selectByExample(example);
        //4 取得信息，封装进EasyUIDataGridResult并返回
        PageInfo<TbContent> pageInfo = new PageInfo<>(contents);
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setRows(pageInfo.getList());
//        System.out.println("在service查询到的数据数"+pageInfo.getList().size());
        result.setTotal((int)pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult saveContent(TbContent content) {
        //补全时间信息
        Date date=new Date();
        content.setCreated(date);
        content.setUpdated(date);
        //进行添加操作
        mapper.insert(content);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContent(TbContent content) {
        //补全时间信息
        Date date=new Date();
        content.setUpdated(date);
        //进行更新操作
        mapper.updateByPrimaryKeySelective(content);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(Long id) {
        int i = mapper.deleteByPrimaryKey(id);
        if (i==0)return TaotaoResult.build(405,"删除失败");
        return TaotaoResult.ok();
    }
}

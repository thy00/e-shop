package cn.thyonline.taotao.content.service.Impl;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.common.utils.JsonUtils;
import cn.thyonline.taotao.content.jedis.JedisClient;
import cn.thyonline.taotao.content.service.ContentService;
import cn.thyonline.taotao.mapper.TbContentMapper;
import cn.thyonline.taotao.pojo.TbContent;
import cn.thyonline.taotao.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    //注入
    @Autowired
    private TbContentMapper mapper;
    @Resource
    private JedisClient client;

    @Value("CONTENT_KEY")
    private String CONTENT_KEY;
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
        //在插入时取消缓存
        try {
            client.hdel(CONTENT_KEY, String.valueOf(content.getCategoryId()));
            System.out.println("缓存删除了！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContent(TbContent content) {
        //补全时间信息
        Date date=new Date();
        content.setUpdated(date);
        //进行更新操作
        mapper.updateByPrimaryKeySelective(content);
        try {
            client.hdel(CONTENT_KEY, String.valueOf(content.getCategoryId()));
            System.out.println("缓存删除了！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(Long id) {
        try {
            TbContent content = mapper.selectByPrimaryKey(id);
            client.hdel(CONTENT_KEY, String.valueOf(content.getCategoryId()));
            System.out.println("缓存删除了！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = mapper.deleteByPrimaryKey(id);
        if (i==0)return TaotaoResult.build(405,"删除失败");
        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentListById(Long categoryId) {
        //在操作之前需要判断缓存redis是否存在资源
        try {
            String hget = client.hget(CONTENT_KEY, String.valueOf(categoryId));
            if (StringUtils.isNotBlank(hget)){
                System.out.println("这是使用的缓存！！");
                return JsonUtils.jsonToList(hget,TbContent.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据categoryId查询数据
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> contents = mapper.selectByExample(example);
        try {
            System.out.println("这是使用的MySQL数据库！！");
            client.hset(CONTENT_KEY, String.valueOf(categoryId),JsonUtils.objectToJson(contents));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }
}

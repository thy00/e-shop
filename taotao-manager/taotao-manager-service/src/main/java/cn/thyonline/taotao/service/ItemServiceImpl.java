package cn.thyonline.taotao.service;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.mapper.TbItemMapper;
import cn.thyonline.taotao.pojo.TbItem;
import cn.thyonline.taotao.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    //1 注入mapper
    @Autowired
    private TbItemMapper itemMapper;
    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        if (page==null) page=1;
        if (rows==null) rows=30;
        //2 设置分页
        PageHelper.startPage(page,rows);
        //3 执行SQL
        TbItemExample example=new TbItemExample();
        List<TbItem> items = itemMapper.selectByExample(example);
        //4 取得信息，封装进EasyUIDataGridResult并返回
        PageInfo<TbItem> pageInfo = new PageInfo<>(items);
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setRows(pageInfo.getList());
//        System.out.println("在service查询到的数据数"+pageInfo.getList().size());
        result.setTotal((int)pageInfo.getTotal());
        return result;
    }
}

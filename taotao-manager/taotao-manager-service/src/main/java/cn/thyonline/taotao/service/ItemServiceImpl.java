package cn.thyonline.taotao.service;

import cn.thyonline.taotao.common.pojo.EasyUIDataGridResult;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.common.utils.IDUtils;
import cn.thyonline.taotao.mapper.TbItemDescMapper;
import cn.thyonline.taotao.mapper.TbItemMapper;
import cn.thyonline.taotao.mapper.TbItemParamMapper;
import cn.thyonline.taotao.pojo.TbItem;
import cn.thyonline.taotao.pojo.TbItemDesc;
import cn.thyonline.taotao.pojo.TbItemExample;
import cn.thyonline.taotao.pojo.TbItemParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    //注入对象
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamMapper itemParamMapper;
    //保存商品
    @Override
    public TaotaoResult saveItem(TbItem item, String desc) {
        //1、补全TbItem属性，并添加数据
        //生成ID
        long id = IDUtils.genItemId();
        item.setId(id);
        //status date
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        Date date=new Date();
        item.setCreated(date);
        item.setUpdated(date);
        itemMapper.insert(item);
        //2、创建desc表并补全属性，添加数据
        TbItemDesc itemDesc=new TbItemDesc();
        //date id与TbItem的ID相同
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        itemDescMapper.insert(itemDesc);
        //3、如果没报错则在这个事务中都保存成功，直接回复保存成功就可以了
        return TaotaoResult.ok();
    }

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

    @Override
    public TaotaoResult deleteItem(Long id) {
//        TbItemExample itemExample =new TbItemExample();
        System.out.println("商品id为："+id);
        int i = itemMapper.deleteByPrimaryKey(id);
        int i1 = itemDescMapper.deleteByPrimaryKey(id);
        if (i!=0) return TaotaoResult.ok();
        return TaotaoResult.build(100,"删除失败");
    }

    @Override
    public TaotaoResult toEditItemDesc(Long id) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
        return TaotaoResult.ok(itemDesc);
    }

    @Override
    public TaotaoResult toEditItemQuery(Long id) {
        TbItemParam param = itemParamMapper.selectByPrimaryKey(id);
        return TaotaoResult.ok(param);
    }

    @Override
    public TaotaoResult updateItem(TbItem item, String desc) {
        //1、补全TbItem属性，并添加数据
        //status date
        //商品状态，1-正常，2-下架，3-删除
        Date date=new Date();
        item.setUpdated(date);
        itemMapper.updateByPrimaryKeySelective(item);
        //2、创建desc表并补全属性，添加数据
        TbItemDesc itemDesc=null;
        //查询表是否存在，如果存在则更新，如果不存在则添加
        TbItemDesc itemDesc1 = itemDescMapper.selectByPrimaryKey(item.getId());
        if (itemDesc1==null){
            itemDesc=new TbItemDesc();
            itemDesc.setCreated(date);
            //date id与TbItem的ID相同
            itemDesc.setItemId(item.getId());
            itemDesc.setItemDesc(desc);
            itemDesc.setUpdated(date);
            itemDescMapper.insert(itemDesc);
            return TaotaoResult.ok();
        }
        //date id与TbItem的ID相同
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(date);
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
        //3、如果没报错则在这个事务中都保存成功，直接回复保存成功就可以了
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult instockItem(Long id) {
//        System.out.println("商品id为："+id);
        //商品状态，1-正常，2-下架，3-删除,修改商品状态为下架 2
        TbItem item=new TbItem();
        item.setId(id);
        item.setStatus((byte) 2);
        int i = itemMapper.updateByPrimaryKeySelective(item);
        if (i!=0) return TaotaoResult.ok();
        return TaotaoResult.build(405,"上架失败");
    }

    @Override
    public TaotaoResult reshelfItem(Long id) {
        //商品状态，1-正常，2-下架，3-删除,修改商品状态为上架 1
        TbItem item=new TbItem();
        item.setId(id);
        item.setStatus((byte) 1);
        int i = itemMapper.updateByPrimaryKeySelective(item);
        if (i!=0) return TaotaoResult.ok();
        return TaotaoResult.build(405,"下架失败");
    }
}

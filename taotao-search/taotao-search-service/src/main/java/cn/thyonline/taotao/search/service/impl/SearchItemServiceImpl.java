package cn.thyonline.taotao.search.service.impl;

import cn.thyonline.taotao.common.pojo.SearchItem;
import cn.thyonline.taotao.common.pojo.SearchResult;
import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.search.dao.SearchItemDao;
import cn.thyonline.taotao.search.mapper.SearchItemMapper;
import cn.thyonline.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper mapper;
    @Autowired
    private SolrServer server;
    @Resource
    private SearchItemDao itemDao;
    @Override
    public TaotaoResult importAllItems() throws Exception{
        //1、从数据库查询所有商品
        System.out.println("开始查询商品！");
        List<SearchItem> items = mapper.searchItemList();
        //2、创建solrServer对象，将商品信息通过SolrInputDocument存入
        System.out.println("查询到的商品数量："+items.size());
        for (SearchItem item:items){
            SolrInputDocument document=new SolrInputDocument();
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            document.addField("item_desc", item.getItem_desc());
            server.add(document);
        }
        //3、提交并返回数据
        server.commit();
        return TaotaoResult.ok();
    }

    @Override
    public SearchResult searchItem(String queryStr, Integer page, Integer rows) throws Exception {
        //1、创建SolrQuery对象，并设置条件
        SolrQuery query=new SolrQuery();
        query.setQuery(queryStr);
        query.setStart((page-1)*rows);//设置查询的起始
        query.setRows(rows);
        query.set("df","item_title");//默认搜索域
        query.setHighlight(true);//开启高亮
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //2、调用dao得到SearchResult
        SearchResult result = itemDao.searchItem(query);
        //3、补充dao返回条件得到的总页数
        long count = result.getRecordCount();
        long pages = count / rows;
        if (count%rows>0){
            pages++;
        }
        result.setPageCount(pages);
        return result;
    }
}

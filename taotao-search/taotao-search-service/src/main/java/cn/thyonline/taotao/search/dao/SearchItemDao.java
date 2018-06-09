package cn.thyonline.taotao.search.dao;

import cn.thyonline.taotao.common.pojo.SearchItem;
import cn.thyonline.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 搜索商品
 */
@Repository
public class SearchItemDao {
    @Autowired
    private SolrServer server;
    /**
     * 参数：service层返回的包装好的SolrQuery
     * 返回：SerchResult
     */
    public SearchResult searchItem(SolrQuery query) throws SolrServerException {
        //1、使用query对象查询solr库
        QueryResponse response = server.query(query);
        //2、从结果中得到商品的数据
        SolrDocumentList documents = response.getResults();
        //3、将商品数据转换成SearchItem的集合
        Map<String,Map<String,List<String>>> highLighting=response.getHighlighting();
        List<SearchItem> searchItems=new ArrayList<>();
        for (SolrDocument document:documents){
            SearchItem item=new SearchItem();
//            System.out.println((String) document.get("item_category_name"));
            item.setCategory_name((String) document.get("item_category_name"));
            item.setId(Long.valueOf(document.get("id").toString()));
            item.setImage(String.valueOf(document.get("item_image")));
            item.setPrice(Long.valueOf(document.get("item_price").toString()));
            item.setSell_point((String) document.get("item_sell_point"));
            //设置高亮显示
            List<String> list = highLighting.get(document.get("id")).get("item_title");
            String itemTitle="";
            if (list!=null&&list.size()>0){
                itemTitle=list.get(0);
            }else {
                itemTitle= (String) document.get("item_title");
            }
            item.setTitle(itemTitle);
            searchItems.add(item);
        }
        //4、将list集合封装进SearchResult返回
        SearchResult result=new SearchResult();
        result.setRecordCount(documents.getNumFound());
        result.setItemList(searchItems);
        return result;
    }
}

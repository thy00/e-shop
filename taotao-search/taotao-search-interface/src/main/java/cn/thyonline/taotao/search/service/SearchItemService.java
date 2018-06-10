package cn.thyonline.taotao.search.service;

import cn.thyonline.taotao.common.pojo.SearchResult;
import cn.thyonline.taotao.common.pojo.TaotaoResult;

public interface SearchItemService {

    /**
     * 导入所有商品到索引库
     * @return
     */
    TaotaoResult importAllItems() throws Exception;

    /**
     * 门户搜索时根据搜索条件查询
     * @param queryStr
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    SearchResult searchItem(String queryStr,Integer page,Integer rows) throws Exception;


    /**
     * 通过id更新索引库
     * @param id
     * @return
     */
    TaotaoResult updateSearchItemById(Long id) throws Exception;
}

package cn.thyonline.taotao.search.mapper;

import cn.thyonline.taotao.common.pojo.SearchItem;

import java.util.List;

public interface SearchItemMapper {
    //查询索引库所需要的数据封装到searchItem的集合中
    List<SearchItem> searchItemList();
}

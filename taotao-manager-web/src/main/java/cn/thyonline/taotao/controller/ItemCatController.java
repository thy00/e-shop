package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.pojo.EasyUITreeNode;
import cn.thyonline.taotao.service.ItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ItemCatController {
    /**
     * 返回EasyUITreeNode的list集合
     * 得到参数 id
     * 请求的连接/item/cat/list
     */
    @Resource
    private ItemCatService itemCatService;
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> easyUITreeNodes = itemCatService.getItemCatList(parentId);
        return easyUITreeNodes;
    }
}

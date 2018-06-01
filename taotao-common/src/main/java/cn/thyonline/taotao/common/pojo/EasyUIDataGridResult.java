package cn.thyonline.taotao.common.pojo;

import java.util.List;

/**
 * 查询商品的数据返回类型
 */
public class EasyUIDataGridResult {
    private Integer total;
    private List row;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRow() {
        return row;
    }

    public void setRow(List row) {
        this.row = row;
    }
}

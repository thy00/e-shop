package cn.thyonline.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 查询商品的数据返回类型
 */

public class EasyUIDataGridResult implements Serializable {

    private Integer total;

    private List rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

}

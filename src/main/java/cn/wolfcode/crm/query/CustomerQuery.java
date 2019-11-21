package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerQuery extends QueryObject{
    private Integer status;//状态
    private Long sellerId = -1l;//市场专员
}

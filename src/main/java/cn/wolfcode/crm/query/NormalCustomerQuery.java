package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NormalCustomerQuery extends QueryObject{

    private Long sellerId = -1l;//销售员id
    private Long courseId = -1l;//学科id
}

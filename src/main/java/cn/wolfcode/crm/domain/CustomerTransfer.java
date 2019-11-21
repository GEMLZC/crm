package cn.wolfcode.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTransfer extends BaseDomain{
    //客户
    private Customer customer;
    //操作人
    private Employee operator;
    //操作时间
    private Date operateTime;
    //旧销售员
    private Employee oldSeller;
    //新销售员
    private Employee newSeller;
    //移交原因
    private String reason;
}
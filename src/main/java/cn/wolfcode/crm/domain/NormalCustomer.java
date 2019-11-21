package cn.wolfcode.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NormalCustomer extends BaseDomain{

    private Date inputTime;

    private Customer customer;

    private Systemdictionaryitem course;

    private BigDecimal money;
}
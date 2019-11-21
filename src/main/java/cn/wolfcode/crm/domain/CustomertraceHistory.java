package cn.wolfcode.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomertraceHistory extends BaseDomain{
    private static final int TYPE_COMMO = 0;//潜在客户跟进
    private static final int TYPE_NOMAL = 1;//正式客户跟进

    //跟进时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date traceTime;
    //跟进记录
    private String traceDetails;
    //交流方式
    private Systemdictionaryitem traceType;
    //跟进结果
    private Integer traceResult;
    //备注
    private String remark;
    //跟进客户
    private Customer customer;
    //录入员
    private Employee inputUser;
    //录入时间
    private Date inputTime;
    //跟进类型
    private Integer type;
    
}
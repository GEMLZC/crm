package cn.wolfcode.crm.domain;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseDomain{
    //给状态字段设置5种状态
    public static final int STATUS_COMMO = 0;//潜在客户
    public static final int STATUS_POOL = 1;//客户池客户
    public static final int STATUS_NORMAL = 2;//正式客户
    public static final int STATUS_FAIL = 3;//开发失败
    public static final int STATUS_LAST = 4;//流失客户

    //姓名
    private String name;
    //年龄
    private Integer age;
    //性别
    private Integer gender;
    //电话
    private String tel;
    //qq
    private String qq;
    //职业
    private Systemdictionaryitem job;
    //来源
    private Systemdictionaryitem source;
    //销售员
    private Employee seller;
    //录入员
    private Employee inputuser;
    //录入时间
    private Date inputTime;
    //状态
    private Integer status = STATUS_COMMO;

    public String getStatusName(){
        String name = "潜在客户";
        switch (status){
            case STATUS_POOL:
                name = "客户池";
                break;
            case STATUS_NORMAL:
                name = "正式客户";
                break;
            case STATUS_FAIL:
                name = "开发失败";
                break;
            case STATUS_LAST:
                name = "流失客户";
                break;
        }
        return name;
    }


    //把属性放入map中，并转成json数据返回给页面
    public String getJson(){
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("age",age);
        map.put("id",id);
        map.put("tel",tel);
        map.put("qq",qq);
        map.put("gender",gender);
        if (job != null){
            map.put("jobId",job.id);
        }
        if (source != null){
            map.put("sourceId",source.id);
        }
        if (seller != null){
            map.put("sellerName",seller.getName());
            map.put("sellerId",seller.id);
        }

        return JSON.toJSONString(map);
    }
}
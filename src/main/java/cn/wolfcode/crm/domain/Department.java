package cn.wolfcode.crm.domain;

import com.alibaba.fastjson.JSON;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Department extends BaseDomain{

    private String name;

    private String sn;

    //把属性放入map中，并转成json数据返回给页面
    public String getJson(){
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("sn",sn);
        map.put("id",id);
        return JSON.toJSONString(map);
    }

}
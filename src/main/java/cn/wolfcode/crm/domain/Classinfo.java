package cn.wolfcode.crm.domain;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Classinfo extends BaseDomain{

    private String name;

    private Integer number;

    private Employee emp;

    public String getJson(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("number",number);
        map.put("empId",emp.getId());
        return  JSON.toJSONString(map);
    }


}
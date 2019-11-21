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
@NoArgsConstructor
@AllArgsConstructor
public class Systemdictionaryitem extends BaseDomain{

    private Long parentId;

    private String title;

    private Integer sequence;

    //把属性放入map中，并转成json数据返回给页面
    public String getJson(){
        Map<String, Object> map = new HashMap<>();
        map.put("title",title);
        map.put("parentId",parentId);
        map.put("id",id);
        map.put("sequence",sequence);
        return JSON.toJSONString(map);
    }

}
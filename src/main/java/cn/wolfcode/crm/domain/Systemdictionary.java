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
public class Systemdictionary extends BaseDomain{

    private String sn;

    private String title;

    private String intro;

    //把属性放入map中，并转成json数据返回给页面
    public String getJson(){
        Map<String, Object> map = new HashMap<>();
        map.put("title",title);
        map.put("sn",sn);
        map.put("id",id);
        map.put("intro",intro);
        return JSON.toJSONString(map);
    }


}
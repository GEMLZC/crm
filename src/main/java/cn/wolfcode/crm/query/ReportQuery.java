package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class ReportQuery extends QueryObject{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;//开始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;//结束时间

    private String groupType = "e.name";//分组条件以及查询信息

    public String getGroupTypeName() {
        String name = "员工";
        switch (groupType){
            case "DATE_FORMAT(c.input_time,'%Y')":
                name = "年";
                break;
            case "DATE_FORMAT(c.input_time,'%Y-%m')":
                name = "月";
                break;
            case "DATE_FORMAT(c.input_time,'%Y-%m-%d')":
                name = "日";
                break;
        }
        return name;
    }
}

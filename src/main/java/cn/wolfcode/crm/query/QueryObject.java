package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

//用来封装用户传递过来的参数
//通用的查询对象
@Getter@Setter
public class QueryObject {

	private int currentPage = 1;
	private int pageSize = 3;
    private String keyword;

    public String getKeyword() {
        return StringUtils.hasLength(keyword) ? keyword : null;
    }

}

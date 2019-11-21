package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.CustomertraceHistory;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomertraceHistoryService {
	void remove(Long id);

	void save(CustomertraceHistory record) throws Exception;

    CustomertraceHistory get(Long id);

	PageInfo<CustomertraceHistory> allList(QueryObject qo);

    //List<CustomertraceHistory> queryDeptName();
}

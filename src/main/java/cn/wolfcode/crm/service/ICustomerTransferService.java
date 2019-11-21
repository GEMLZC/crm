package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.CustomerTransfer;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomerTransferService {
	//void remove(Long id);

	void save(CustomerTransfer record) throws Exception;

    CustomerTransfer get(Long id);

	PageInfo<CustomerTransfer> allList(QueryObject qo);

    //List<CustomerTransfer> queryDeptName();
}

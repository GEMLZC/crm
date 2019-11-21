package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.NormalCustomer;
import cn.wolfcode.crm.query.NormalCustomerQuery;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface INormalCustomerService {

	void save(NormalCustomer record);

    NormalCustomer get(Long id);

	PageInfo<NormalCustomer> allList(NormalCustomerQuery qo);

}

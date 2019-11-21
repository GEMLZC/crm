package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomerService {

	void save(Customer record) throws Exception;

    Customer get(Long id);

	PageInfo<Customer> allList(QueryObject qo);


    void updateStatus(Customer customer) throws Exception;

    List<Customer> getCustomerByStatus(Integer statusCommo);
}

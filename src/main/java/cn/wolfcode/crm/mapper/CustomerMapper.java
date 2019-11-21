package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    Customer selectByPrimaryKey(Long id);

    List<Customer> selectAll(QueryObject qo);

    int updateByPrimaryKey(Customer record);

    void updateSellerIdById(@Param("newsellerID") Long newsellerID, @Param("customerId") Long customerId);

    void updateStatus(Customer customer);

    List<Customer> selectCustomerByStatus(Integer status);

    void updateStatusWithNornal(Long id);
}
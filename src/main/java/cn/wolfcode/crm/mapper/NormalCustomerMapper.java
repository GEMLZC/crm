package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.NormalCustomer;
import cn.wolfcode.crm.query.NormalCustomerQuery;

import java.util.List;

public interface NormalCustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NormalCustomer record);

    NormalCustomer selectByPrimaryKey(Long id);

    List<NormalCustomer> selectAll(NormalCustomerQuery qo);

    int updateByPrimaryKey(NormalCustomer record);
}
package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.CustomertraceHistory;

import java.util.List;

public interface CustomertraceHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomertraceHistory record);

    CustomertraceHistory selectByPrimaryKey(Long id);

    List<CustomertraceHistory> selectAll();

    int updateByPrimaryKey(CustomertraceHistory record);
}
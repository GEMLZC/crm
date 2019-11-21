package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.mapper.CustomerReportMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.query.ReportQuery;
import cn.wolfcode.crm.service.ICustomerReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomerReportServiceImpl implements ICustomerReportService{
    @Autowired
    private CustomerReportMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public List<Map<String, Object>> allList(ReportQuery qo) {
        return mapper.potentiCount(qo);
    }
}

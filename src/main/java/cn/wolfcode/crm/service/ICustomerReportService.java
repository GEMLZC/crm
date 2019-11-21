package cn.wolfcode.crm.service;

import cn.wolfcode.crm.query.ReportQuery;

import java.util.List;
import java.util.Map;

public interface ICustomerReportService {

    List<Map<String,Object>> allList(ReportQuery qo);
}

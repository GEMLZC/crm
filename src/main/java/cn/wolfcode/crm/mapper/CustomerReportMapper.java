package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Systemdictionary;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.query.ReportQuery;

import java.util.List;
import java.util.Map;

public interface CustomerReportMapper {
    List<Map<String,Object>> potentiCount(ReportQuery qo);
}
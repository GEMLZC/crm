package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Classinfo;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface ClassinfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Classinfo record);

    Classinfo selectByPrimaryKey(Long id);

    List<Classinfo> selectAll(QueryObject qo);

    int updateByPrimaryKey(Classinfo record);
}
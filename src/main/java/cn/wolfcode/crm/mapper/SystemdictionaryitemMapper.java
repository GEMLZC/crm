package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Systemdictionaryitem;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface SystemdictionaryitemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Systemdictionaryitem record);

    Systemdictionaryitem selectByPrimaryKey(Long id);

    List<Systemdictionaryitem> selectAll();

    int updateByPrimaryKey(Systemdictionaryitem record);

    List<Systemdictionaryitem> selectAllList(QueryObject qo);

    int selectMaxSequenceByParentId(Long parentId);

    List<Systemdictionaryitem> selectDirBySn(String sn);

}
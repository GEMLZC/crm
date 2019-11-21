package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.Systemdictionary;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISystemdictionaryService {

	void save(Systemdictionary record);

    Systemdictionary get(Long id);

	PageInfo<Systemdictionary> allList(QueryObject qo);

    List<Systemdictionary> allList();
}

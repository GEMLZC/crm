package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.Systemdictionaryitem;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISystemdictionaryitemService {

	void save(Systemdictionaryitem record);

    Systemdictionaryitem get(Long id);

	PageInfo<Systemdictionaryitem> allList(QueryObject qo);

    List<Systemdictionaryitem> getDicBySn(String sn);
}

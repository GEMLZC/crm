package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.Classinfo;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IClassinfoService {
	void remove(Long id);

	void save(Classinfo record);

    Classinfo get(Long id);

	PageInfo<Classinfo> allList(QueryObject qo);

}

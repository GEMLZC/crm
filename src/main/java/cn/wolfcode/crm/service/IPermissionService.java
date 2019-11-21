package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IPermissionService {
	void remove(Long id);

    Permission get(Long id);

	PageInfo<Permission> allListAndPagination(QueryObject qo);

	void load();

    List<Permission> allList();
}

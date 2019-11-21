package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IRoleService {
	void remove(Long id);

	void save(Role record, Long[] ids);

    Role get(Long id);

	List<Role> allList();

    PageInfo<Role> allListAndPagination(QueryObject qo);

    Role getRoleName(String name);
}

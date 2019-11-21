package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IDepartmentService {
	void remove(Long id);

	void save(Department record);

    Department get(Long id);

	PageInfo<Department> allList(QueryObject qo);

    List<Department> queryDeptName();
}

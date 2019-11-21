package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.mapper.DepartmentMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements IDepartmentService {
	@Autowired
	private DepartmentMapper mapper;

	@Override
	public void remove(Long id) {
	    //打破员工与部门的关系
        mapper.updateDeptIdByPrimaryKey(id);
		mapper.deleteByPrimaryKey(id);
		//System.out.println(1/0);
	}

	@Override
	public void save(Department record) {
		if (record.getId() != null) {
			mapper.updateByPrimaryKey(record);
		}else {
			mapper.insert(record);
		}

	}

	@Transactional(readOnly=true)
	@Override
	public Department get(Long id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public PageInfo<Department> allList(QueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<Department> list = mapper.selectAll(qo);
		PageInfo<Department> page = new PageInfo<>(list);
		if (page.getPrePage() == 0) {
			page.setPrePage(1);
		}
		if (page.getNextPage() == 0) {
			page.setNextPage(page.getPages());
		}
		return page;
	}

    @Override
    public List<Department> queryDeptName() {
        return mapper.selectAll(null);
    }

}

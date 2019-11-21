package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Classinfo;
import cn.wolfcode.crm.mapper.ClassinfoMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IClassinfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassinfoServiceImpl implements IClassinfoService {
	@Autowired
	private ClassinfoMapper mapper;

	@Override
	public void remove(Long id) {

		mapper.deleteByPrimaryKey(id);
		//System.out.println(1/0);
	}

	@Override
	public void save(Classinfo record) {
		if (record.getId() != null) {
			mapper.updateByPrimaryKey(record);
		}else {
			mapper.insert(record);
		}

	}

	@Transactional(readOnly=true)
	@Override
	public Classinfo get(Long id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public PageInfo<Classinfo> allList(QueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<Classinfo> list = mapper.selectAll(qo);
		PageInfo<Classinfo> page = new PageInfo<>(list);
		if (page.getPrePage() == 0) {
			page.setPrePage(1);
		}
		if (page.getNextPage() == 0) {
			page.setNextPage(page.getPages());
		}
		return page;
	}
}

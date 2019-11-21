package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Systemdictionary;
import cn.wolfcode.crm.mapper.SystemdictionaryMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemdictionaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SystemdictionaryImpl implements ISystemdictionaryService {
	@Autowired
	private SystemdictionaryMapper mapper;


	@Override
	public void save(Systemdictionary record) {
		if (record.getId() != null) {
			mapper.updateByPrimaryKey(record);
		}else {
			mapper.insert(record);
		}

	}

	@Transactional(readOnly=true)
	@Override
	public Systemdictionary get(Long id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public PageInfo<Systemdictionary> allList(QueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<Systemdictionary> list = mapper.selectAllList(qo);
		PageInfo<Systemdictionary> page = new PageInfo<>(list);
		if (page.getPrePage() == 0) {
			page.setPrePage(1);
		}
		if (page.getNextPage() == 0) {
			page.setNextPage(page.getPages());
		}
		return page;
	}

    @Override
    public List<Systemdictionary> allList() {
        return mapper.selectAll();
    }
}

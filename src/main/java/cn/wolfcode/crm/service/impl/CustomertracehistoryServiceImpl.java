package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.CustomertraceHistory;
import cn.wolfcode.crm.mapper.CustomertraceHistoryMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ICustomertraceHistoryService;
import cn.wolfcode.crm.utils.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomertracehistoryServiceImpl implements ICustomertraceHistoryService {
	@Autowired
	private CustomertraceHistoryMapper mapper;

	@Override
	public void remove(Long id) {
		mapper.deleteByPrimaryKey(id);
		//System.out.println(1/0);
	}

	@Override
	public void save(CustomertraceHistory record) throws Exception{
		if (record.getId() != null) {
			mapper.updateByPrimaryKey(record);
		}else {
            record.setInputUser(UserContext.getCurrentUser());
			mapper.insert(record);
		}

	}

	@Transactional(readOnly=true)
	@Override
	public CustomertraceHistory get(Long id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public PageInfo<CustomertraceHistory> allList(QueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<CustomertraceHistory> list = mapper.selectAll();
		PageInfo<CustomertraceHistory> page = new PageInfo<>(list);
		if (page.getPrePage() == 0) {
			page.setPrePage(1);
		}
		if (page.getNextPage() == 0) {
			page.setNextPage(page.getPages());
		}
		return page;
	}

}

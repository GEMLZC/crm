package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.NormalCustomer;
import cn.wolfcode.crm.mapper.CustomerMapper;
import cn.wolfcode.crm.mapper.NormalCustomerMapper;
import cn.wolfcode.crm.query.NormalCustomerQuery;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.INormalCustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NormalCustomerImpl implements INormalCustomerService {
	@Autowired
	private NormalCustomerMapper mapper;
	@Autowired
    private CustomerMapper customerMapper;


	@Override
	public void save(NormalCustomer record) {
		if (record.getId() != null) {
			mapper.updateByPrimaryKey(record);
		}else {
            customerMapper.updateStatusWithNornal(record.getCustomer().getId());
			mapper.insert(record);
		}

	}

	@Transactional(readOnly=true)
	@Override
	public NormalCustomer get(Long id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public PageInfo<NormalCustomer> allList(NormalCustomerQuery qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<NormalCustomer> list = mapper.selectAll(qo);
		PageInfo<NormalCustomer> page = new PageInfo<>(list);
		return page;
	}
}

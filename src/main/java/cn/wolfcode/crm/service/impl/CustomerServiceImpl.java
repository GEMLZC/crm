package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.mapper.CustomerMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ICustomerService;
import cn.wolfcode.crm.utils.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {
	@Autowired
	private CustomerMapper mapper;

	@Override
	public void save(Customer record) throws Exception{
		if (record.getId() != null) {
			mapper.updateByPrimaryKey(record);
		}else {
            Employee employee = UserContext.getCurrentUser();
            record.setSeller(employee);//设置销售人员
            record.setInputuser(employee);//设置录入人员
			mapper.insert(record);
		}

	}

	@Transactional(readOnly=true)
	@Override
	public Customer get(Long id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public PageInfo<Customer> allList(QueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<Customer> list = mapper.selectAll(qo);
		PageInfo<Customer> page = new PageInfo<>(list);
		if (page.getPrePage() == 0) {
			page.setPrePage(1);
		}
		if (page.getNextPage() == 0) {
			page.setNextPage(page.getPages());
		}
		return page;
	}

    @Override
    public void updateStatus(Customer customer) throws Exception{
        mapper.updateStatus(customer);
    }

    @Override
    public List<Customer> getCustomerByStatus(Integer status) {
        return mapper.selectCustomerByStatus(status);
    }


}

package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.CustomerTransfer;
import cn.wolfcode.crm.mapper.CustomerMapper;
import cn.wolfcode.crm.mapper.CustomerTransferMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ICustomerTransferService;
import cn.wolfcode.crm.utils.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerTransferImpl implements ICustomerTransferService {
	@Autowired
	private CustomerTransferMapper mapper;
	@Autowired
    private CustomerMapper customerMapper;


	@Override
	public void save(CustomerTransfer record) throws Exception{
		if (record.getId() != null) {
			mapper.updateByPrimaryKey(record);
		}else {
            record.setOperator(UserContext.getCurrentUser());//设置录入员
            //修改客户的销售人员
            customerMapper.updateSellerIdById(record.getNewSeller().getId(),record.getCustomer().getId());
			mapper.insert(record);
		}

	}

	@Transactional(readOnly=true)
	@Override
	public CustomerTransfer get(Long id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public PageInfo<CustomerTransfer> allList(QueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<CustomerTransfer> list = mapper.selectAll();
		PageInfo<CustomerTransfer> page = new PageInfo<>(list);
		if (page.getPrePage() == 0) {
			page.setPrePage(1);
		}
		if (page.getNextPage() == 0) {
			page.setNextPage(page.getPages());
		}
		return page;
	}



}

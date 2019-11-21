package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Systemdictionaryitem;
import cn.wolfcode.crm.mapper.SystemdictionaryitemMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemdictionaryitemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SystemdictionaryitemImpl implements ISystemdictionaryitemService {
	@Autowired
	private SystemdictionaryitemMapper mapper;


	@Override
	public void save(Systemdictionaryitem record) {
		if (record.getId() != null) {

			mapper.updateByPrimaryKey(record);
		}else {
            //如果没有设置序号，默认设置当前目录最大序号加一
            if(record.getSequence() == null){
                int maxSequence  = mapper.selectMaxSequenceByParentId(record.getParentId());
                record.setSequence(++maxSequence);
            }
			mapper.insert(record);
		}
	}

	@Transactional(readOnly=true)
	@Override
	public Systemdictionaryitem get(Long id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public PageInfo<Systemdictionaryitem> allList(QueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<Systemdictionaryitem> list = mapper.selectAllList(qo);
		PageInfo<Systemdictionaryitem> page = new PageInfo<>(list);
		if (page.getPrePage() == 0) {
			page.setPrePage(1);
		}
		if (page.getNextPage() == 0) {
			page.setNextPage(page.getPages());
		}
		return page;
	}

    @Override
    public List<Systemdictionaryitem> getDicBySn(String sn) {
        return mapper.selectDirBySn(sn);
    }

}

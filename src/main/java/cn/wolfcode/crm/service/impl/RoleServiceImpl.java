package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.RoleMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper mapper;

    @Override
    public void remove(Long id) {
        //先打破与员工和权限表的关系，在执行删除操作
        mapper.deletePermissionRoleByRoleId(id);
        mapper.deleteEmployeeRoleByRoleId(id);
        mapper.deleteByPrimaryKey(id);

    }

    @Override
    public void save(Role record, Long[] ids) {
        if (record.getId() != null) {
            //先删除中间表的数据
            mapper.deletePermissionRoleByRoleId(record.getId());
            mapper.updateByPrimaryKey(record);
        } else {
            mapper.insert(record);
        }

        if (ids != null && ids.length > 0) {
            for (Long permissionId : ids) {
		        mapper.insertRolePermission(record.getId(),permissionId);
            }
        }

    }

    @Transactional(readOnly = true)
    @Override
    public Role get(Long id) {

        return mapper.selectByPrimaryKey(id);
    }


    @Transactional(readOnly = true)
    @Override
    public List<Role> allList() {

        return mapper.selectAll();
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<Role> allListAndPagination(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Role> list = mapper.selectAllAndPagination(qo);
        PageInfo<Role> page = new PageInfo<>(list);
        if (page.getPrePage() == 0) {
            page.setPrePage(1);
        }
        if (page.getNextPage() == 0) {
            page.setNextPage(page.getPages());
        }
        return page;
    }

    @Override
    public Role getRoleName(String name) {
        return mapper.selectByRoleName(name);
    }

}

package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    Permission selectByRoleId(Long id);

    List<Permission> selectAllAndPagination(QueryObject qo);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    /**
     * 查询全部权限
     * @return
     */
    List<String> selectAllExpression();

    void deletePermissionRoleByPermissionId(Long permissionId);

    /**
     * 查询单个员工的权限
     * @param empId
     * @return
     */
    List<String> selectEmpExpression(Long empId);
}
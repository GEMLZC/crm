package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<Role> selectByEmployeeKey(Long id);

    List<Role> selectAllAndPagination(QueryObject qo);

    void deletePermissionRoleByRoleId(Long id);

    void deleteEmployeeRoleByRoleId(Long id);

    void insertRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    Role selectByRoleName(String name);

    List<String> selectSnByEmpId(Long id);
}
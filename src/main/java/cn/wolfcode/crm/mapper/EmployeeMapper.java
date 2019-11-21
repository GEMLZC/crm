package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll(QueryObject qo);

    List<Employee> selectAllName();

    int updateByPrimaryKey(Employee record);

    void insertEmpAndRole(@Param("id") Long id, @Param("roleId") Long roleId);

    void deleteEmployeeRoleById(Long id);

    Employee seleteByUserAndPassword(@Param("username") String username, @Param("password") String password);

    Employee selectByName(String name);

    void batchDeleteById(Long[] ids);

    List<Employee> selectAllForXsl();

    List<Employee> selectByRoleSn(String... sn);
}
package cn.wolfcode.crm.shiro;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.mapper.EmployeeMapper;
import cn.wolfcode.crm.mapper.PermissionMapper;
import cn.wolfcode.crm.mapper.RoleMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义的数据源
 */
@Component("crmRealm")
public class CRMRealm extends AuthorizingRealm {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 将使用的密码匹配器设置到当前使用的Realm中,注解使用此方式注入，
     * 通过注入自定义数据源就可以注入密码匹配器
     */
    @Autowired
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }


    /**
     * 提供用户认证数据(登录)
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = authenticationToken.getPrincipal().toString();
        Employee employee = employeeMapper.selectByName(username);
        if (employee != null){
            return new SimpleAuthenticationInfo(employee,employee.getPassword(),
                    //用户名作为盐
                    ByteSource.Util.bytes(username)
                    ,"crmRealm");
        }
        return null;
    }


    /**
     *
     * 提供用户权限数据（权限认证）
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        //System.out.println("+++++++++++++++++");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到当前用户信息
        Employee emp = (Employee) principal.getPrimaryPrincipal();
        if (emp.isAdmin()){
            //给超管所有的角色和权限
            info.addStringPermission("*:*");
            info.addRole("ADMIN");
        }else {
            //查询当前用户的角色
            List<String> roles = roleMapper.selectSnByEmpId(emp.getId());
            //查询当前用户的权限
            List<String> permissions = permissionMapper.selectEmpExpression(emp.getId());
            //交给shiro管理
            info.addStringPermissions(permissions);
            info.addRoles(roles);
        }
        return info;
    }


}

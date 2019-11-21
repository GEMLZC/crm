package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.mapper.PermissionMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private PermissionMapper mapper;
    @Autowired
    private ApplicationContext ctx;

    @Override
    public void remove(Long permissionId) {
        //首先必须要打破关系
        mapper.deletePermissionRoleByPermissionId(permissionId);
        //再执行删除操作
        mapper.deleteByPrimaryKey(permissionId);

    }


    @Transactional(readOnly = true)
    @Override
    public Permission get(Long id) {

        return mapper.selectByPrimaryKey(id);
    }


    @Transactional(readOnly = true)
    @Override
    public PageInfo<Permission> allListAndPagination(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Permission> list = mapper.selectAllAndPagination(qo);
        PageInfo<Permission> page = new PageInfo<>(list);
        if (page.getPrePage() == 0) {
            page.setPrePage(1);
        }
        if (page.getNextPage() == 0) {
            page.setNextPage(page.getPages());
        }
        return page;
    }

    @Override
    public List<Permission> allList() {
        return mapper.selectAll();
    }

    @Override
    public void load() {
        //获取所有的Controller
        Map<String, Object> map = ctx.getBeansWithAnnotation(Controller.class);
        //查询表中的含有的权限
        List<String> allExpression = mapper.selectAllExpression();

        //System.out.println(map);
        //获取所有map的value
        Collection<Object> values = map.values();
        //System.out.println(values);

        for (Object controller : values) {
            //获取所有Controller类
            Class<?> clazz = controller.getClass();
            //获取所有Controller的所有方法
            Method[] methods = clazz.getSuperclass().getDeclaredMethods();
            //遍历拿到有注解RequirePermission的方法
            for (Method m : methods) {
                RequiresPermissions requirePermissionAnnotation = m.getAnnotation(RequiresPermissions.class);
                //当返回为空时说明方法上没写RequiresPermissions注解，不做处理
                if (requirePermissionAnnotation != null) {
                    String expression = "";//表达式
                    String name = "";//权限名称
                    Permission permission = new Permission();

                    //直接获取表达式和权限名称，因为shrio不支持反射获取表达式
                    expression = requirePermissionAnnotation.value()[0];
                    name = requirePermissionAnnotation.value()[1];

                    //如果集合包含了该权限直接进行下一次循环
                    if (allExpression.contains(expression)) {
                        continue;
                    }
                    //对表达式处理封装参数
                    permission.setExpression(expression);
                    permission.setName(name);
                    mapper.insert(permission);
                }
            }
        }

    }


}

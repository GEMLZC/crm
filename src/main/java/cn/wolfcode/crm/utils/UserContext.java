package cn.wolfcode.crm.utils;

import cn.wolfcode.crm.domain.Employee;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


public abstract class UserContext {
    /**
     * 获取当前用户
     * @return
     */
    public static Employee getCurrentUser(){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        return employee;
    }

}

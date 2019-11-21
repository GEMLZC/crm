package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.query.EmpQueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EmployeeServiceImplTest {
    @Autowired
    private IEmployeeService service;

    @Test
    public void allList() {
        EmpQueryObject emp = new EmpQueryObject();
        emp.setKeyword("总");
        service.allList(emp).getList().forEach(System.out::println);
    }
}
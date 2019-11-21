package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DepartmentServiceImplTest {
    @Autowired
    private IDepartmentService service;

    @Test
    public void remove() {
    }

    @Test
    public void save() {
    }

    @Test
    public void get() {
    }

    @Test
    public void allList() {
        PageInfo<Department> page = service.allList(new QueryObject());
        page.getList().forEach(System.out::println);
    }
}
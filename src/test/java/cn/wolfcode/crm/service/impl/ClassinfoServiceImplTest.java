package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.query.ClassQueryObject;
import cn.wolfcode.crm.query.EmpQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IClassinfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ClassinfoServiceImplTest {
    @Autowired
    private IClassinfoService service;

    @Test
    public void allList() {
        service.allList(new ClassQueryObject()).getList().forEach(System.out::println);
    }
}
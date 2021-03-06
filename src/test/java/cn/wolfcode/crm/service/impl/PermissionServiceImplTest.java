package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.service.IPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PermissionServiceImplTest {
    @Autowired
    private IPermissionService service;
    @Test
    public void allList() {
        service.allList().forEach(System.out::println);
    }
}
package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CustomerMapperTest {
    @Autowired
    private CustomerMapper mapper;

    @Test
    public void selectAll() {
        /*List<Customer> list = mapper.selectAll(qo);
        list.forEach(System.out::println);*/
    }
}
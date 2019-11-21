package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.mapper.EmployeeMapper;
import cn.wolfcode.crm.mapper.PermissionMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper mapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void remove(Long id) {
        //首先必须要打破关系
        mapper.deleteEmployeeRoleById(id);
        //再执行删除操作
        mapper.deleteByPrimaryKey(id);

    }

    @Override
    public void save(Employee record, Long[] ids) {
        if (record.getId() != null) {
            //首先必须要打破关系
            mapper.deleteEmployeeRoleById(record.getId());
            //再执行修改操作
            mapper.updateByPrimaryKey(record);
        } else {
            //密码加密处理
            Md5Hash md5Hash = new Md5Hash(record.getPassword(),record.getName());
            //把加密后的密码重写赋值给Employee中
            record.setPassword(md5Hash.toString());
            //先要保证用户表的插入数据
            mapper.insert(record);
        }
        if (ids != null && ids.length > 0) {
            //再保证中间表的数据
            for (Long roleId : ids) {
                mapper.insertEmpAndRole(record.getId(), roleId);
            }
        }


    }

    @Transactional(readOnly = true)
    @Override
    public Employee get(Long id) {

        return mapper.selectByPrimaryKey(id);
    }


    @Transactional(readOnly = true)
    @Override
    public PageInfo<Employee> allList(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Employee> list = mapper.selectAll(qo);
        PageInfo<Employee> page = new PageInfo<>(list);
        if (page.getPrePage() == 0) {
            page.setPrePage(1);
        }
        if (page.getNextPage() == 0) {
            page.setNextPage(page.getPages());
        }
        return page;
    }

    @Override
    public List<Employee> getAllName() {
        return mapper.selectAllName();
    }


    @Override
    public void getEmpByUserAndPassword(String username, String password) {
        Employee emp = mapper.seleteByUserAndPassword(username, password);
        if (emp != null) {
        } else {
            throw new RuntimeException("账户和用户名不匹配");
        }
    }

    @Override
    public Employee getName(String name) {
        return mapper.selectByName(name);
    }

    @Override
    public void batchRemove(Long[] ids) {
        //打破与角色的关系

        //在执行批量删除
        mapper.batchDeleteById(ids);
    }

    @Override
    public Workbook export(HttpServletResponse response) {
        //设置下载文件的名称
        response.setHeader("Content-Disposition","attachment;filename=employee.xls");
        //创建excel文档对象
        Workbook wb = new HSSFWorkbook();
        //创建excel文档
        Sheet sheet = wb.createSheet();

        //给excel文档写入数据
        Row row = sheet.createRow(0);//行
        //设置表头
        row.createCell(0).setCellValue("名字");//单元格（列）
        row.createCell(1).setCellValue("邮箱");
        row.createCell(2).setCellValue("年龄");


        List<Employee> list = mapper.selectAllForXsl();
        for (int i = 0; i < list.size(); i++) {

            Employee employee = list.get(i);
            //创建行
            row = sheet.createRow(i+1);
            //写入值到单元格
            row.createCell(0).setCellValue(employee.getName());
            row.createCell(1).setCellValue(employee.getEmail());
            row.createCell(2).setCellValue(employee.getAge());
        }

        return wb;
    }

    @Override
    public void importXls(InputStream in) throws Exception{

        //创建excel文档对象
        Workbook wb = new HSSFWorkbook(in);
        //获取excel文档第一页
        Sheet sheet = wb.getSheetAt(0);
        //得到总行数
        int rowNum = sheet.getLastRowNum();

        for (int i = 1; i <= rowNum; i++) {
            //创建对象，循环一次创建一次，否则只会插入一条数据，后续操作只能修改该条数据
            Employee employee = new Employee();
            Row row = sheet.getRow(i);
            //做空值校验
            if (row.getCell(0).getStringCellValue() != null){
                employee.setName(row.getCell(0).getStringCellValue());
            }
            if (row.getCell(1).getStringCellValue() != null) {
                employee.setEmail(row.getCell(1).getStringCellValue());
            }
            Cell cell = row.getCell(2);

            Integer age = null;
            //判断单元格
            switch (cell.getCellTypeEnum()){
                case STRING:
                    age = Integer.valueOf(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    age = (int) cell.getNumericCellValue();
                    break;
            }
            employee.setAge(age);
            employee.setPassword("1");
            //保存数据
            save(employee,null);
        }


    }
    @Override
    public List<Employee> getRoleSn(String... sn) {

        return mapper.selectByRoleSn(sn);
    }

}

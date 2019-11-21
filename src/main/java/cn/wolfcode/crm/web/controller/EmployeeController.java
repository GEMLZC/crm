package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.JsonResult;
import cn.wolfcode.crm.query.EmpQueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.service.IRoleService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/emp")
public class EmployeeController {
    @Autowired
    private IEmployeeService service;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IRoleService roleService;

    @RequiresPermissions(value = {"employee:list", "员工列表"}, logical = Logical.OR)
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") EmpQueryObject qo, Model model) {
        model.addAttribute("result", service.allList(qo));
        model.addAttribute("depts", departmentService.queryDeptName());
        //System.out.println(departmentService.queryDeptName());
        return "employee/list";
    }

    @RequiresPermissions(value = {"employee:remove", "删除员工"}, logical = Logical.OR)
    @RequestMapping("/remove")
    @ResponseBody
    public JsonResult remove(Employee emp) {
        JsonResult json = new JsonResult();
        try {
            service.remove(emp.getId());
            return json;
        } catch (Exception e) {
            json.mark("删除失败");
            return json;
        }

    }

    @RequiresPermissions(value = {"employee:input", "员工新增/编辑"}, logical = Logical.OR)
    @RequestMapping("/input")
    public String input(Employee emp, Model model) {
        //model.addAttribute("depts", departmentService.queryDeptName());//查询部门信息
        model.addAttribute("role", roleService.allList());//查询全部权限
        if (emp.getId() != null) {
            model.addAttribute("entity", service.get(emp.getId()));//包括当前用户权限
        }
        return "employee/input";
    }

    @RequestMapping("/getDeptMessage")
    @ResponseBody
    public List<Department> getDeptMessage() {
        return departmentService.queryDeptName();//查询部门信息,并封装成json数据
    }


    @RequiresPermissions(value = {"employee:save", "员工保存"}, logical = Logical.OR)
    @RequestMapping("/save")
    public String save(Employee emp, Long[] ids) {
        service.save(emp, ids);
        return "redirect:/emp/list.do";
    }

    @RequestMapping("/checkName")
    @ResponseBody
    public boolean checkName(String name) {
        Employee employee = service.getName(name);
        return employee == null;
    }

    @RequestMapping("/batchRemove")
    @ResponseBody
    public JsonResult batchRemove(Long[] ids) {
        JsonResult json = new JsonResult();
        try {
            service.batchRemove(ids);
            return json;
        } catch (Exception e) {
            json.mark("删除失败");
            return json;
        }
    }

    /**
     * 下载excel文档
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/exportXls")
    @ResponseBody
    public void exportXls(HttpServletResponse response) throws IOException {
        Workbook wb = service.export(response);
        wb.write(response.getOutputStream());
    }

    /**
     * 导入数据
     *
     * @param file
     * @return
     */
    @RequestMapping("/importXls")
    @ResponseBody
    public JsonResult importXls(MultipartFile file) {
        JsonResult json = new JsonResult();
        try {
            service.importXls(file.getInputStream());
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            json.mark("失败");
            return json;
        }
    }

}

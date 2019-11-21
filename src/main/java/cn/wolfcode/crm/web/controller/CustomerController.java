package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.JsonResult;
import cn.wolfcode.crm.query.CustomerQuery;
import cn.wolfcode.crm.query.EmpQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ICustomerService;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.service.ISystemdictionaryService;
import cn.wolfcode.crm.service.ISystemdictionaryitemService;
import cn.wolfcode.crm.utils.UserContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService service;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ISystemdictionaryitemService systemdictionaryitemService;

    //@RequiresPermissions(value = {"customer:list", "部门列表"},logical = Logical.OR)
    @RequestMapping("/potentialList")
    public String list(@ModelAttribute("qo") CustomerQuery qo, Model model) {
        //设置只查询潜在客户的信息
        qo.setStatus(Customer.STATUS_COMMO);
        Subject subject = SecurityUtils.getSubject();
        //如果不是超管或者经理就无法查看所有的潜在客户,只能看的当前登录用户的信息
        if (!(subject.hasRole("Market_Manager") || subject.hasRole("ADMIN"))){
            qo.setSellerId(UserContext.getCurrentUser().getId());
        }
        //潜在客户列表
        model.addAttribute("result", service.allList(qo));
        //下拉框销售人员列表
        model.addAttribute("sellers", employeeService.getRoleSn("Market_Manager","Market"));
        //查询模态框客户职业
        model.addAttribute("jobs", systemdictionaryitemService.getDicBySn("job"));
        //查询模态框客户来源
        model.addAttribute("sources", systemdictionaryitemService.getDicBySn("source"));
        //交流方式
        model.addAttribute("ccts", systemdictionaryitemService.getDicBySn("communicationMethod"));


        return "customer/list";
    }


    @RequestMapping("/list")
    public String custlist(@ModelAttribute("qo") CustomerQuery qo, Model model) {
        //设置只查询潜在客户的信息
        qo.setStatus(Customer.STATUS_COMMO);
        Subject subject = SecurityUtils.getSubject();
        //潜在客户列表
        model.addAttribute("result", service.allList(qo));
        //下拉框销售人员列表
        model.addAttribute("sellers", employeeService.getRoleSn("Market_Manager","Market"));
        return "customer/customer_list";
    }



    //@RequiresPermissions(value = {"customer:save", "保存部门"},logical = Logical.OR)
    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(Customer customer) {
        JsonResult json = new JsonResult();
        try {
            service.save(customer);
            return json;
        } catch (Exception e) {
            json.setMessage("操作失败");
            return json;
        }
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public JsonResult updateStatus(Customer customer) {
        JsonResult json = new JsonResult();
        try {
            service.updateStatus(customer);
            return json;
        } catch (Exception e) {
            json.setMessage("操作失败");
            return json;
        }
    }
}

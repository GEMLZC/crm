package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.JsonResult;
import cn.wolfcode.crm.domain.NormalCustomer;
import cn.wolfcode.crm.query.NormalCustomerQuery;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ICustomerService;
import cn.wolfcode.crm.service.INormalCustomerService;
import cn.wolfcode.crm.service.ISystemdictionaryitemService;
import cn.wolfcode.crm.utils.UserContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/normalCustomer")
public class NormalCustomerController {
    @Autowired
    private INormalCustomerService service;
    @Autowired
    private ISystemdictionaryitemService systemdictionaryitemService;
    @Autowired
    private ICustomerService customerService;

    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") NormalCustomerQuery qo, Model model) {
        Subject subject = SecurityUtils.getSubject();
        //如果不是超管或者经理就无法查看所有的客户,只能看的当前登录用户的信息
        if (!(subject.hasRole("Market_Manager") || subject.hasRole("ADMIN"))){
            qo.setSellerId(UserContext.getCurrentUser().getId());
        }
        model.addAttribute("result", service.allList(qo));
        model.addAttribute("courses", systemdictionaryitemService.getDicBySn("subject"));
        model.addAttribute("customers", customerService.getCustomerByStatus(Customer.STATUS_COMMO));

        return "normalcustomer/list";
    }

    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(NormalCustomer normalCustomer) {
        JsonResult json = new JsonResult();
        try {
            service.save(normalCustomer);
            return json;
        } catch (Exception e) {
            json.setMessage("操作失败");
            return json;
        }
    }
}

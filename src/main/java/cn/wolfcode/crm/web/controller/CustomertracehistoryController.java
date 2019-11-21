package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.CustomertraceHistory;
import cn.wolfcode.crm.domain.JsonResult;
import cn.wolfcode.crm.query.EmpQueryObject;
import cn.wolfcode.crm.service.ICustomertraceHistoryService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customerTraceHistory")
public class CustomertracehistoryController {
    @Autowired
    private ICustomertraceHistoryService service;

    //@RequiresPermissions(value = {"customertraceHistory:list", "部门列表"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") EmpQueryObject qo, Model model) {
        model.addAttribute("result", service.allList(qo));
        return "customertraceHistory/list";
    }

    //@RequiresPermissions(value = {"customertraceHistory:save", "保存部门"},logical = Logical.OR)
    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(CustomertraceHistory customertraceHistory) {
        JsonResult json = new JsonResult();
        try {
            service.save(customertraceHistory);
            return json;
        } catch (Exception e) {
            json.setMessage("操作失败");
            return json;
        }
    }
}

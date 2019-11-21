package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Classinfo;
import cn.wolfcode.crm.domain.JsonResult;
import cn.wolfcode.crm.query.ClassQueryObject;
import cn.wolfcode.crm.query.EmpQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IClassinfoService;
import cn.wolfcode.crm.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/class")
public class ClassInfoController {
    @Autowired
    private IClassinfoService service;
    @Autowired
    private IEmployeeService employeeService;


    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") ClassQueryObject qo, Model model) {
        model.addAttribute("result", service.allList(qo));
        model.addAttribute("emps", employeeService.getAllName());
        return "classinfo/list";
    }


    @RequestMapping("/remove")
    @ResponseBody
    public JsonResult remove(Classinfo info) {
        JsonResult json = new JsonResult();
        try {
            service.remove(info.getId());
            return json;
        } catch (Exception e) {
            json.setMessage("操作失败");
            return json;
        }
    }


    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(Classinfo info) {
        JsonResult json = new JsonResult();
        try {
            service.save(info);
            return json;
        } catch (Exception e) {
            json.setMessage("操作失败");
            return json;
        }
    }
}

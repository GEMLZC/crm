package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.JsonResult;
import cn.wolfcode.crm.query.EmpQueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dept")
public class DepartmentController {
    @Autowired
    private IDepartmentService service;

    @RequiresPermissions(value = {"department:list", "部门列表"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") EmpQueryObject qo, Model model) {
        model.addAttribute("result", service.allList(qo));
        return "department/list";
    }

    @RequiresPermissions(value = {"department:remove", "删除部门"},logical = Logical.OR)
    @RequestMapping("/remove")
    @ResponseBody
    public JsonResult remove(Department dpet) {
        JsonResult json = new JsonResult();
        try {
            service.remove(dpet.getId());
            return json;
        } catch (Exception e) {
            json.setMessage("操作失败");
            return json;
        }
    }

    @RequiresPermissions(value = {"department:save", "保存部门"},logical = Logical.OR)
    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(Department dept) {
        JsonResult json = new JsonResult();
        try {
            service.save(dept);
            return json;
        } catch (Exception e) {
            json.setMessage("操作失败");
            return json;
        }
    }
}

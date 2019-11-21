package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Systemdictionary;
import cn.wolfcode.crm.domain.JsonResult;
import cn.wolfcode.crm.query.EmpQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemdictionaryService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemDictionary")
public class SystemdictionaryController {
    @Autowired
    private ISystemdictionaryService service;

    //@RequiresPermissions(value = {"systemdictionary:list", "字典目录列表"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) {
        model.addAttribute("result", service.allList(qo));
        return "systemdictionary/list";
    }

    //@RequiresPermissions(value = {"systemdictionary:save", "新增字典"},logical = Logical.OR)
    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(Systemdictionary systemdictionary) {
        JsonResult json = new JsonResult();
        try {
            service.save(systemdictionary);
            return json;
        } catch (Exception e) {
            json.setMessage("操作失败");
            return json;
        }
    }
}

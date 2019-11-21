package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.JsonResult;
import cn.wolfcode.crm.domain.Systemdictionaryitem;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.query.SystemdictionaryitemQuery;
import cn.wolfcode.crm.service.ISystemdictionaryService;
import cn.wolfcode.crm.service.ISystemdictionaryitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemDictionaryItem")
public class SystemdictionaryitemController {
    @Autowired
    private ISystemdictionaryitemService service;
    @Autowired
    private ISystemdictionaryService systemdictionaryService;

    //@RequiresPermissions(value = {"systemdictionaryitem:list", "字典目录列表"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") SystemdictionaryitemQuery qo, Model model) {
        model.addAttribute("result", service.allList(qo));
        model.addAttribute("dics", systemdictionaryService.allList());
        return "systemdictionaryitem/list";
    }

    //@RequiresPermissions(value = {"systemdictionaryitem:save", "新增字典"},logical = Logical.OR)
    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(Systemdictionaryitem systemdictionaryitem) {
        JsonResult json = new JsonResult();
        try {
            service.save(systemdictionaryitem);
            return json;
        } catch (Exception e) {
            json.setMessage("操作失败");
            return json;
        }
    }
}

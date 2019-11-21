package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.EmpQueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.service.IRoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private IRoleService service;
	@Autowired
    private IPermissionService permissionService;

    @RequiresPermissions(value = {"role:list", "角色列表"},logical = Logical.OR)
    @RequestMapping("/list")
	public String list(@ModelAttribute("qo") EmpQueryObject qo , Model model) {
		model.addAttribute("result", service.allListAndPagination(qo));
		return "role/list";
	}

    @RequiresPermissions(value = {"role:remove", "删除角色"},logical = Logical.OR)
    @RequestMapping("/remove")
	public String remove(Role emp) {
		service.remove(emp.getId());
		return "redirect:/role/list.do";
	}

    @RequiresPermissions(value = {"role:input", "角色编辑/新增"},logical = Logical.OR)
    @RequestMapping("/input")
	public String input(Role role,Model model) {
		if (role.getId() != null) {
			model.addAttribute("entity", service.get(role.getId()));

		}
		return "role/input";
	}


    @RequestMapping("/getPermission")
    @ResponseBody
    public List<Permission> getPermission() {
        return permissionService.allList();
    }

    @RequiresPermissions(value = {"role:save", "保存角色"},logical = Logical.OR)
    @RequestMapping("/save")
	public String save(Role role , Long[] ids) {
		service.save(role,ids);
		return "redirect:/role/list.do";
	}

    @RequestMapping("/checkRole")
    @ResponseBody
    private boolean checkRole(String name){
        Role role = service.getRoleName(name);
        return role == null;
    }
}

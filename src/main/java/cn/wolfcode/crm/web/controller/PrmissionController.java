package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.JsonResult;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.QueryObject;
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

@Controller
@RequestMapping("/permission")
public class PrmissionController {
	@Autowired
	private IPermissionService service;

	@Autowired
    private IRoleService roleService;


	@RequestMapping("/list")
    @RequiresPermissions(value = {"permission:list", "权限列表"},logical = Logical.OR)
	public String list(@ModelAttribute("qo") QueryObject qo , Model model) {
		model.addAttribute("result", service.allListAndPagination(qo));

		return "permission/list";
	}

	@RequestMapping("/remove")
    @RequiresPermissions(value = {"permission:remove", "权限删除"},logical = Logical.OR)
	public String remove(Permission per) {
		service.remove(per.getId());
		return "redirect:/permission/list.do";
	}

	@RequestMapping("/load")
    @ResponseBody
	public JsonResult load() {
        JsonResult json = new JsonResult();
        try {
            service.load();
            return json;
        }catch (Exception e){
            json.mark("加载失败");
            return json;
        }
	}

	@RequestMapping("/noprmission")
	public String noPrmission(){
		return "common/nopermission";
	}
}

package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.query.ReportQuery;
import cn.wolfcode.crm.service.ICustomerReportService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customerReport")
public class ReportController {
    @Autowired
    private ICustomerReportService service;

    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") ReportQuery qo, Model model) {
        model.addAttribute("result", service.allList(qo));
        return "report/list";
    }

    @RequestMapping("/chartByBar")
    public String chartByBar(@ModelAttribute("qo") ReportQuery qo, Model model) {
        List<Map<String, Object>> list = service.allList(qo);
        List<Object> xAxis = new ArrayList<>();
        List<Object> yAxis = new ArrayList<>();
        for (Map<String, Object> map:list){
            xAxis.add(map.get("groupType"));
            yAxis.add(map.get("customerNum"));
        }
        model.addAttribute("xAxis", JSON.toJSONString(xAxis));//x轴数据
        model.addAttribute("yAxis", JSON.toJSONString(yAxis));//y轴数据
        return "report/customerReport_bar";
    }


    @RequestMapping("/chartByPie")
    public String chartByPie(@ModelAttribute("qo") ReportQuery qo, Model model) {
        List<Map<String, Object>> list = service.allList(qo);
        List<Object> legend = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        for (Map<String, Object> map:list){
            //左上方数据
            legend.add(map.get("groupType"));
            //饼图数据
            HashMap<Object, Object> m = new HashMap<>();
            m.put("value",map.get("customerNum"));
            m.put("name",map.get("groupType"));
            data.add(m);
        }
        model.addAttribute("legend", JSON.toJSONString(legend));//左上方数据
        model.addAttribute("data", JSON.toJSONString(data));//饼图数据
        return "report/customerReport_pie";
    }



}

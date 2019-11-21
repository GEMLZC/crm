package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

public interface IEmployeeService {
	void remove(Long id);

	void save(Employee record , Long[] ids);

    Employee get(Long id);

	PageInfo<Employee> allList(QueryObject qo);

	List<Employee> getAllName();

    void getEmpByUserAndPassword(String username, String password);

    Employee getName(String name);

    void batchRemove(Long[] ids);

    /**
     * 导出excle
     * @return
     * @param response
     */
    Workbook export(HttpServletResponse response);

    void importXls(InputStream file) throws Exception;

    /**
     * 下拉框显示的潜在客户的销售人员
     * @return
     */
    List<Employee> getRoleSn(String... sn);
}

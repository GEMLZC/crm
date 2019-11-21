package cn.wolfcode.crm.shiro;

import cn.wolfcode.crm.domain.JsonResult;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功或失败的处理方式
 */
@Component("crmFormAuthenticationFilter")
public class CRMFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 登录成功时的响应
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //设置响应数据格式
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new JsonResult()));
        return false;

    }

    /**
     * 登录失败时的响应
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        //登录失败shiro会抛出账户或密码不匹配的异常，只需判断异常类型即可知道如何响应数据
        JsonResult json = new JsonResult();
        if (e instanceof UnknownAccountException){//帐号不正确异常
            json.mark("帐号不正确!");
        }else if (e instanceof IncorrectCredentialsException){//密码不正确异常
            json.mark("密码不正确!");
        }else {
            json.mark("未知错误!");
        }
        e.printStackTrace();

        try {
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(json));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }
}

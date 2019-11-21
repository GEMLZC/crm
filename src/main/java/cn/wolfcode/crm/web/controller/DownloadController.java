package cn.wolfcode.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class DownloadController {

    @RequestMapping("/download")
    public ModelAndView download(String fileName , HttpServletResponse response , HttpServletRequest request) throws IOException {
        //设置下载文件的位置
        String realPath = request.getServletContext().getRealPath("/css");
        //设置响应头信息
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        ServletOutputStream out = response.getOutputStream();
        Files.copy(Paths.get(realPath,fileName),out);
        return null;
    }
}

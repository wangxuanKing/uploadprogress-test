package com.pawx.uploadprogress.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pawx.uploadprogress.modle.User;
import com.pawx.uploadprogress.service.ProgressEntity;

/**
 * 上传文件
 * @author wangxuan
 *
 */
@Controller
@RequestMapping("/user")
public class UploadController {
    private final static Map<String, User> users = new HashMap<String, User>();

    //模拟数据源,构造初始数据
    public UploadController() {
	users.put("张起灵", new User("张起灵", "闷油瓶", "02200059", "menyouping@yeah.net"));
	users.put("李寻欢", new User("李寻欢", "李探花", "08866659", "lixunhuan@gulong.cn"));
	users.put("拓拔野", new User("拓拔野", "搜神记", "05577759", "tuobaye@manhuang.cc"));
	users.put("孙悟空", new User("孙悟空", "美猴王", "03311159", "sunhouzi@xiyouji.zh"));
    }

    @RequestMapping("/list")
    public String list(Model model) {
	model.addAttribute("users", users);
	return "user/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser() {
	return "user/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(User user, @RequestParam MultipartFile[] myfiles, HttpServletRequest request) throws IOException {
	//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
	//如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
	//并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件
	for (MultipartFile myfile : myfiles) {
	    if (myfile.isEmpty()) {
		System.out.println("文件未上传");
	    } else {
		System.out.println("文件长度: " + myfile.getSize());
		System.out.println("文件类型: " + myfile.getContentType());
		System.out.println("文件名称: " + myfile.getName());
		System.out.println("文件原名: " + myfile.getOriginalFilename());
		System.out.println("========================================");
		//如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
		//这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
		FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
	    }
	}
	users.put(user.getUsername(), user);
	return "redirect:/user/list";
    }
    
    @RequestMapping(value = "/getProgress")  
    @ResponseBody  
    public String getProgress(Map<String, Object> model) {  
        ProgressEntity status = (ProgressEntity) model.get("status");  
        if(status==null){  
            return "{}";  
        }  
        return status.toString();  
    }  
}
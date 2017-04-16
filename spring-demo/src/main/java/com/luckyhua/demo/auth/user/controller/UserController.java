package com.luckyhua.demo.auth.user.controller;

import com.luckyhua.demo.auth.user.service.UserService;
import com.luckyhua.webmvc.result.ResultUtils;
import com.luckyhua.webmvc.result.json.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller("userController")
@RequestMapping("/auth/user")
public class UserController {

	@Resource(name="userService")
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/login")
	public JsonResult login(String phone, String vcode){
		JsonResult resJson = ResultUtils.getSuccessResult();
		resJson.putData("user", phone + ":" + vcode);
		return resJson;
	}

	@RequestMapping("/test")
	public String test(){
		return "test";
	}

}

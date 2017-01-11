package com.lanxi.WechatIntegralService.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.service.IntegralManagerServiceImpl;

public class IntegralController {
	@Resource
	private IntegralManagerServiceImpl integralService;
	
 	@RequestMapping("/intoJf")
    @ResponseBody
	public String intoJf(HttpServletRequest req, HttpServletResponse rep){
		 return JSONObject.toJSONString(integralService.intoJf(req, rep));
	}
 	 @RequestMapping("/userInfo")
     @ResponseBody
     public String getUserInfo(HttpServletResponse rep, HttpServletRequest req){
 		 return JSONObject.toJSONString(integralService.getUserInfo(rep, req));
 	 }
 	 
     @RequestMapping("/phone")
     @ResponseBody
     public String phone(HttpServletResponse rep, HttpServletRequest req){
    	 return JSONObject.toJSONString(integralService.phone(rep, req));
     }
     @RequestMapping("/updatePhone")
     @ResponseBody
     public String updatephone(HttpServletResponse rep, HttpServletRequest req){
    	 return JSONObject.toJSONString(integralService.updatephone(rep, req));
     }
     @RequestMapping("/updatePhoneOper")
     @ResponseBody
     public String updatePhoneOper(HttpServletResponse rep, HttpServletRequest req){
    	 return JSONObject.toJSONString(integralService.updatePhoneOper(rep, req));
     }
     @RequestMapping("/bindings")
     @ResponseBody
     public String bindings(HttpServletRequest req, HttpServletResponse rep){
    	 return JSONObject.toJSONString(integralService.bindings(req, rep));
     }
     
     @RequestMapping("/bindingsJf")
     @ResponseBody
     public String bindingsJf(HttpServletRequest req, HttpServletResponse rep) {
    	 return JSONObject.toJSONString(integralService.bindingsJf(req, rep));
     }
     
     @RequestMapping("/bindingsJfOper")
     @ResponseBody
     public String bindingsJfOper(HttpServletRequest req, HttpServletResponse rep){
    	 return JSONObject.toJSONString(integralService.bindingsJfOper(req, rep));
     }
     
     @RequestMapping("/integralQuery")
     @ResponseBody
     public String integralQuery(HttpServletResponse rep,HttpServletRequest req){
    	 return JSONObject.toJSONString(integralService.integralQuery(rep,req));
     }
     
     @RequestMapping("/deliveryIntegral")
     @ResponseBody
     public String deliveryIntegral(HttpServletResponse rep, HttpServletRequest req) {
    	 return JSONObject.toJSONString(integralService.deliveryIntegral(rep,req));
     }
     
     @RequestMapping("/deliveryIntegralOper")
     @ResponseBody
     public String deliveryIntegralOper(HttpServletResponse rep, HttpServletRequest req){
    	 return JSONObject.toJSONString(integralService.deliveryIntegralOper(rep,req));
     }
     
     @RequestMapping("/sendMessage")
     @ResponseBody
     public String sendMessage(HttpServletResponse rep, HttpServletRequest req){
    	 return JSONObject.toJSONString(integralService.sendMessage(rep,req));
     }
}

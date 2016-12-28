package com.lanxi.WechatIntegralService.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanxi.WechatIntegralService.test.ServiceTest;

@Controller
public class TestController {
	@Resource
	private ServiceTest test;
	@RequestMapping("/validService")
	@ResponseBody
	public String validService(HttpServletRequest req,HttpServletResponse res){
		System.out.println("request in");
		return test.validService(req, res);
	}
	@RequestMapping("/receiveMessage")
	@ResponseBody
	public String receiveMessage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("receiveMessage");
		req.setCharacterEncoding("utf-8");
		InputStream in=req.getInputStream();
		InputStreamReader reader=new InputStreamReader(in, "utf-8");
		BufferedReader buffer=new BufferedReader(reader);
		String temp=null;
		StringBuffer strBuffer=new StringBuffer();
		while((temp=buffer.readLine())!=null)
			strBuffer.append(temp);
		System.out.println(strBuffer.toString());
		if(strBuffer.toString().trim().equals(""))
			return test.validService(req, res);
		
		in.close();
		res.getOutputStream().close();
		return "finish";
	}
	@RequestMapping("/codeIn")
	@ResponseBody
	public String  getCode(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException{
		System.out.println("code info ");
		req.setCharacterEncoding("utf-8");
		String code=req.getParameter("code");
		System.out.println("code:"+code);
		return "code info : "+code;
	}
}

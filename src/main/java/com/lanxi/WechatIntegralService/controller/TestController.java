package com.lanxi.WechatIntegralService.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lanxi.wechat.entity.token.WebAccessToken;
import com.lanxi.wechat.manageer.TokenManager;
import com.lanxi.wechat.manageer.UserManager;
import com.lanxi.wechat.service.ValidServerService;
/**
 * 测试用-控制器类
 * @author 1
 *
 */
@Controller
public class TestController {
	@Resource
	private ValidServerService test;
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
	public void getCode(HttpServletRequest req, HttpServletResponse res) throws IOException{
		System.out.println("code info ");
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		String code=req.getParameter("code");
		System.out.println("code:"+code);
		WebAccessToken token=TokenManager.generatorWebAccessTokenMetadata(code);
		StringBuffer strBuffer=new StringBuffer();
		strBuffer.append("code:"+code+"\n");
		strBuffer.append("tokens:"+"\n");
		strBuffer.append("openId:"+token.getOpenId()+"\n");
		strBuffer.append("webToken:"+token.getAccessToken()+"\n");
		strBuffer.append("refreshToken:"+token.getRefreshToken().getRefreshToken()+"\n");
		strBuffer.append("userInfo:"+UserManager.getWebUserInfo(token.getOpenId()));
		System.out.println(strBuffer.toString());
		System.out.println(TokenManager.getWebAccessTokens());
		res.getOutputStream().println("<!DOCTYPE html>");
		res.getOutputStream().println("<html>");
		res.getOutputStream().println("<head>");
		res.getOutputStream().println("<meta charset=\"utf-8\">");
		res.getOutputStream().println("</head>");
		res.getOutputStream().println("<body>");
		res.getOutputStream().println("<p>");
		res.getOutputStream().println(new String(strBuffer.toString().getBytes("utf-8"),"iso8859-1"));
		res.getOutputStream().println("</p>");
		res.getOutputStream().println("</body>");
		res.getOutputStream().println("</html>");
	}
}

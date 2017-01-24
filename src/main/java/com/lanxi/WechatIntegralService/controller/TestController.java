package com.lanxi.WechatIntegralService.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanxi.WechatIntegralService.service.QuartzService;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.integral.report.ReturnMessage;
import com.lanxi.wechat.entity.automessage.NewsAutoMessage;
import com.lanxi.wechat.entity.message.TextMessage;
import com.lanxi.wechat.entity.token.JSSign;
import com.lanxi.wechat.entity.token.WebAccessToken;
import com.lanxi.wechat.manageer.TokenManager;
import com.lanxi.wechat.manageer.UserManager;
import com.lanxi.wechat.service.ValidServerService;
import com.mysql.jdbc.log.Log;
/**
 * 测试用-控制器类
 * @author 1
 *
 */
@Controller
public class TestController {
	private static Logger logger=Logger.getLogger(TestController.class);
	
	static{
		try {
			Class.forName("com.lanxi.wechat.manageer.TokenManager");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Resource
	private ValidServerService test;
	
	
	@Resource
	private QuartzService quartz;
	
	public void redpacketOvertime(){
		System.out.println("红包过期任务");
		quartz.redPacketOverTime();
	}
	public void couponOverTime(){
		System.out.println("卡券过期任务");
		quartz.couponOverTime();
	}
	
	@ResponseBody
	public String validService(HttpServletRequest req,HttpServletResponse res){
		System.out.println("request in");
		return test.validService(req, res);
	}
	@RequestMapping("/receiveMessage.do")
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

		
		if(strBuffer.toString().trim().contains("百度")){
			TextMessage textMessage=new TextMessage();
			textMessage.fromString(strBuffer.toString().trim());
			System.out.println(textMessage.toElement().asXML());
			
//			LinkMessage linkMessage=new LinkMessage();
//			linkMessage.setCreateTime(textMessage.getCreateTime());
//			linkMessage.setDescription("百度一下,你就知道!");
//			linkMessage.setFromUserName(textMessage.getToUserName());
//			linkMessage.setToUserName(textMessage.getFromUserName());
//			linkMessage.setMsgId(textMessage.getMsgId());
//			linkMessage.setUrl("www.baidu.com");
//			linkMessage.setTitle("百度");
//			System.out.println(linkMessage.toElement().asXML());
//			
//			System.out.println(HttpUtil.postXml(linkMessage.toElement().asXML(), res, "utf-8"));;
			NewsAutoMessage back=new NewsAutoMessage();
			back.setArticleCount(1+"");
			NewsAutoMessage.Item item=new NewsAutoMessage.Item();
			item.setDescription("百度一下,你就知道");
			item.setPicUrl("http://imgs.technews.cn/wp-content/uploads/2014/10/Baidu.jpg");
			item.setTitle("百度");
			item.setUrl("www.baidu.com");
			back.setCreateTime(textMessage.getCreateTime());
			back.setFromUserName(textMessage.getToUserName());
			back.setToUserName(textMessage.getFromUserName());
			back.addArticle(item);
			System.out.println(back.toElement().asXML());
			System.out.println(HttpUtil.postXml(back.toElement().asXML(), res,"utf-8"));;
		}
		
		res.getOutputStream().close();
		return "finish";
	}
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
	@RequestMapping("/getJsSign.do")
	@ResponseBody
	public String getJsSign(HttpServletRequest req,HttpServletResponse res) throws Exception{
		req.setCharacterEncoding("utf-8");
		logger.info("前端请求jsskd签名!");
		String nonce=req.getParameter("nonce");
		String timestamp=req.getParameter("timestamp");
		String url=req.getParameter("url");
		logger.info("签名参数传入:nonce="+nonce+",timestamp="+timestamp+",url="+url);
		JSSign sign=TokenManager.getJsSign(nonce, timestamp, url);
		logger.info("获取的签名:"+sign);
		ReturnMessage message=new ReturnMessage();
		message.setRetCode("0000");
		message.setRetMsg("6666");
		message.setObj(sign);
		logger.info("返回信息:"+message.toJson());
		return message.toJson();
	}
}

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
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.service.QuartzService;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.integral.report.ReturnMessage;
import com.lanxi.wechat.entity.automessage.NewsAutoMessage;
import com.lanxi.wechat.entity.automessage.TextAutoMessage;
import com.lanxi.wechat.entity.menuevent.ClickMenuEvent;
import com.lanxi.wechat.entity.menuevent.LocationSelectMenuEvent;
import com.lanxi.wechat.entity.menuevent.ScanPushMenuEvent;
import com.lanxi.wechat.entity.menuevent.ScanWaitMenuEvent;
import com.lanxi.wechat.entity.menuevent.SystemCameraAndPhotosMenuEvent;
import com.lanxi.wechat.entity.menuevent.SystemCameraMenuEvent;
import com.lanxi.wechat.entity.menuevent.ViewMenuEvent;
import com.lanxi.wechat.entity.menuevent.WechatCameraMenuEvent;
import com.lanxi.wechat.entity.menuevent.WechatMenuEvent;
import com.lanxi.wechat.entity.message.TextMessage;
import com.lanxi.wechat.entity.token.JSSign;
import com.lanxi.wechat.entity.token.WebAccessToken;
import com.lanxi.wechat.entity.token.WebAccessTokenRequst;
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
		logger.info("receiveMessage");
		req.setCharacterEncoding("utf-8");
		InputStream in=req.getInputStream();
		InputStreamReader reader=new InputStreamReader(in, "utf-8");
		BufferedReader buffer=new BufferedReader(reader);
		String temp=null;
		StringBuffer strBuffer=new StringBuffer();
		while((temp=buffer.readLine())!=null)
			strBuffer.append(temp);
		logger.info("收到的消息:"+strBuffer.toString());
		if(strBuffer.toString().trim().equals("")){
			logger.info("收到服务器认证消息!");
			return test.validService(req, res);
		}
		in.close();
		try{
		logger.info("尝试处理消息");
		Element ele=DocumentHelper.parseText(strBuffer.toString().trim()).getRootElement();
		if(ele.selectSingleNode("//Event")!=null&&ele.selectSingleNode("//EventKey")!=null){
			logger.info("收到菜单事件消息!");
			String type=ele.selectSingleNode("//Event").getText();
			String key =ele.selectSingleNode("//EventKey").getText();
			switch (type) {
			case WechatMenuEvent.MENU_EVENT_CLICK:
				logger.info("收到点击下了消息!");
				ClickMenuEvent clickEvent=new ClickMenuEvent();
				clickEvent.fromString(strBuffer.toString().trim());
				switch (key) {
				case "product":
					NewsAutoMessage back1=new NewsAutoMessage();
					back1.setArticleCount(1+"");
					NewsAutoMessage.Item item=new NewsAutoMessage.Item();
					item.setTitle("产品中心");
					item.setPicUrl("http://www.188lanxi.com/weixinlanxi/img/cultureShow6.png");
					item.setDescription("杭州蓝喜信息技术有限公司是一家专业从事移动互联网技术开发、便民金融服务、通讯运营商与银行业务外包的移动互联网服务运营商。");
					item.setUrl("http://mp.weixin.qq.com/s/uwKg2uAqhZdNr0DtKBYTDg");
					back1.setCreateTime(clickEvent.getCreateTime());
					back1.setFromUserName(clickEvent.getToUserName());
					back1.setToUserName(clickEvent.getFromUserName());
					back1.addArticle(item);
					HttpUtil.postXml(back1.toElement().asXML(), res,"utf-8");
					break;
				case "server":
					logger.info("用户获取客服联系方式!");
					TextAutoMessage back2=new TextAutoMessage();
					back2.setFromUserName(clickEvent.getToUserName());
					back2.setToUserName(clickEvent.getFromUserName());
					back2.setContent("【客户服务热线：4000552797】");
					HttpUtil.postXml(back2.toElement().asXML(), res,"utf-8");
					break;
				default:return null;
				}
				break;
			case WechatMenuEvent.MENU_EVENT_VIEW:
				ViewMenuEvent viewEvent=new ViewMenuEvent();
				viewEvent.fromString(strBuffer.toString().trim());
				break;	
			case WechatMenuEvent.MENU_EVENT_SCANCODE_PUSH:
				ScanPushMenuEvent scanPushEvent=new ScanPushMenuEvent();
				scanPushEvent.fromString(strBuffer.toString().trim());
				break;
			case WechatMenuEvent.MENU_EVENT_SCANCODE_WAITMSG:
				ScanWaitMenuEvent scanWaitEvent=new ScanWaitMenuEvent();
				scanWaitEvent.fromString(strBuffer.toString().trim());
				break;
			case WechatMenuEvent.MENU_EVENT_PIC_SYSPHOTO:
				SystemCameraMenuEvent systemCameraEvent=new SystemCameraMenuEvent();
				systemCameraEvent.fromString(strBuffer.toString().trim());
				break;
			case WechatMenuEvent.MENU_EVENT_PIC_PHOTO_OR_ALBUM:
				SystemCameraAndPhotosMenuEvent systemCameraAndPhotosMenuEvent=new SystemCameraAndPhotosMenuEvent();
				systemCameraAndPhotosMenuEvent.fromString(strBuffer.toString().trim());
				break;
			case WechatMenuEvent.MENU_EVENT_PIC_WEIXIN:
				WechatCameraMenuEvent wechatCameraEvent=new WechatCameraMenuEvent();
				wechatCameraEvent.fromString(strBuffer.toString().trim());
				break;
			case WechatMenuEvent.MENU_EVENT_LOCATION_SELECT:
				LocationSelectMenuEvent locationSelectEvent=new LocationSelectMenuEvent();
				locationSelectEvent.fromString(strBuffer.toString().trim());
				break;
			default:return null;
			}
		}
		
		}catch (Exception e) {
			throw new AppException("消息转dom元素异常",e);
		}
		
		if(strBuffer.toString().trim().contains("百度")){
			TextMessage textMessage=new TextMessage();
			textMessage.fromString(strBuffer.toString().trim());
			System.out.println(textMessage.toElement().asXML());
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
	@RequestMapping("/getAccessToken.do")
	@ResponseBody
	public String getAccessToken(HttpServletRequest req,HttpServletResponse res){
		try {
			logger.info("外部组件请求微信授权!");
			req.setCharacterEncoding("utf-8");
			return TokenManager.getAccessToken();
		} catch (Exception e) {
			throw new AppException("外部组件请求微信授权异常",e);
		}
	}
	@RequestMapping("/getWebAccessToken.do")
	@ResponseBody
	public String getWebAccessToken(HttpServletRequest req,HttpServletResponse res){
		try {
			logger.info("外部组件请求微信网页授权!");
			req.setCharacterEncoding("utf-8");
			String openId=req.getParameter("openId");
			String code  =req.getParameter("code");
			Object obj=TokenManager.generatorWebAccessTokenMetadata(code);
			return JSONObject.toJSONString(obj);
		} catch (Exception e) {
			throw new AppException("外部组件请求微信网页授权异常",e);
		}
	}
	
	
	@RequestMapping("/toPlane.do")
	public void toPlaneGameInde(HttpServletRequest req,HttpServletResponse res) throws IOException{
		logger.info("玩游戏咯!");
		try {
			req.setCharacterEncoding("utf-8");
			String code =req.getParameter("code");
			res.sendRedirect("http://162749ty99.iask.in/weixinlanxi/oauth/authorization.do?code="+code);
		} catch (Exception e) {
			throw new AppException("跳转游戏页面异常",e);
		}
	}
	@RequestMapping("/generatorRedPacketUrl.do")
	@ResponseBody
	public String generatorRedPacketUrl(HttpServletRequest req,HttpServletResponse res){
		ReturnMessage returnMessage=new ReturnMessage();
		try {
			req.setCharacterEncoding("utf-8");
			String redPacketId=req.getParameter("redPacketId");
			logger.info("请求生成红包分享链接:redpacketId="+redPacketId);
			String result= TokenManager.generatorWebTokenCodeUrl(ConfigUtil.get("unpackRedPacketUrl")+"redPacketId="+redPacketId,WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_DETAIL);
			returnMessage.setRetCode("0000");
			returnMessage.setRetMsg("生成红包url成功");
			returnMessage.setToken(null);
			returnMessage.setObj(result);
		} catch (Exception e) {
			returnMessage.setRetCode("9999");
			returnMessage.setToken(null);
			returnMessage.setObj(null);
			returnMessage.setRetMsg("生成红包url失败");
			throw new AppException("生成红包分享链接异常",e);
		}
		return returnMessage.toJson();
	}
}

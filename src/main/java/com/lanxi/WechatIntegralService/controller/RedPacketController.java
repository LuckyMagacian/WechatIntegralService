package com.lanxi.WechatIntegralService.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanxi.WechatIntegralService.service.IntegralRedPacketService;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.integral.report.ReturnMessage;

@Controller
public class RedPacketController {
	@Resource
	IntegralRedPacketService redPacketService;
	
	@RequestMapping(value = "/toUnpackRedPacket.do")
	public void toUnpackRedPacket(HttpServletRequest req,HttpServletResponse res){
		ReturnMessage message=redPacketService.toUnpackRedPacket(req, res);
		try{
		if(message.getRetCode().equals("0000"))
			res.sendRedirect(ConfigUtil.get("unpackRedPacketPageUrl")+"token="+message.getToken()+"&redPacketId="+message.getObj());
		else
			res.sendRedirect("points_weixin/404.html");
		}catch (Exception e) {
			throw new AppException("跳转拆红包页面异常!",e);
		}
	}
	@RequestMapping(value = "/grantRedPacket.do" , produces = {"application/json;charset=UTF-8"})
//	@RequestMapping("/grantRedPacket")
	@ResponseBody
	public String grantRedPacket(HttpServletRequest req,HttpServletResponse res){
		return redPacketService.grantRedPacket(req,res);
	}
	@ResponseBody
//	@RequestMapping("/unpackRedPacket")
	@RequestMapping(value = "/unpackRedPacket.do" , produces = {"application/json;charset=UTF-8"})
	public String unpackRedPacket(HttpServletRequest req,HttpServletResponse res){
		return redPacketService.unpackRedPacket(req,res);
	}
	@ResponseBody
	@RequestMapping(value = "/redPacketDetail.do" , produces = {"application/json;charset=UTF-8"})
	public String redPacketDetail(HttpServletRequest req,HttpServletResponse res){
		return redPacketService.redPacketDetail(req,res);
	}
	@ResponseBody
	@RequestMapping(value = "/redPacketInfo.do" , produces = {"application/json;charset=UTF-8"})
	public String redPacketInfo(HttpServletRequest req,HttpServletResponse res){
		return redPacketService.redPacketInfo(req, res);
	}
	
}

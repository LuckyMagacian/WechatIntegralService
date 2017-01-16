package com.lanxi.WechatIntegralService.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanxi.WechatIntegralService.service.IntegralRedPacketService;

@Controller
public class RedPacketController {
	@Resource
	IntegralRedPacketService redPacketService;
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

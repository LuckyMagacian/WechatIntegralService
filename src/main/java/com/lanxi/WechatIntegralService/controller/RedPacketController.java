package com.lanxi.WechatIntegralService.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanxi.WechatIntegralService.service.IntegralRedPacketService;

@Controller
public class RedPacketController {
	@Resource
	IntegralRedPacketService redPacketService;
	
	@RequestMapping("/grantRedPacket")
	@ResponseBody
	public String grantRedPacket(HttpServletRequest req){
		return redPacketService.grantRedPacket(req);
	}
	@ResponseBody
	@RequestMapping("/unpackRedPacket")
	public String unpackRedPacket(HttpServletRequest req){
		return redPacketService.unpackRedPacket(req);
	}
}

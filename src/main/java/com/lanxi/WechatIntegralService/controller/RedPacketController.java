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
	@RequestMapping(value = "/grantRedPacket.do" , produces = {"application/json;charset=UTF-8"})
//	@RequestMapping("/grantRedPacket")
	@ResponseBody
	public String grantRedPacket(HttpServletRequest req){
		return redPacketService.grantRedPacket(req);
	}
	@ResponseBody
//	@RequestMapping("/unpackRedPacket")
	@RequestMapping(value = "/unpackRedPacket.do" , produces = {"application/json;charset=UTF-8"})
	public String unpackRedPacket(HttpServletRequest req){
		return redPacketService.unpackRedPacket(req);
	}
}

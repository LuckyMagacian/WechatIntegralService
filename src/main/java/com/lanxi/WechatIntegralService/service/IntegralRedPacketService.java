package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lanxi.integral.report.ReturnMessage;

public interface IntegralRedPacketService {
	public String grantRedPacket(HttpServletRequest req,HttpServletResponse res);
	public String unpackRedPacket(HttpServletRequest req,HttpServletResponse res);
	public String redPacketDetail(HttpServletRequest req,HttpServletResponse res);
	public String redPacketInfo(HttpServletRequest req,HttpServletResponse res);
	public ReturnMessage toUnpackRedPacket(HttpServletRequest req,HttpServletResponse res);
}

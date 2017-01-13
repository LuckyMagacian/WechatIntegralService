package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IntegralRedPacketService {
	public String grantRedPacket(HttpServletRequest req,HttpServletResponse res);
	public String unpackRedPacket(HttpServletRequest req,HttpServletResponse res);
	public String redPacketDetail(HttpServletRequest req,HttpServletResponse res);
}

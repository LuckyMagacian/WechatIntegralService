package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;

public interface IntegralRedPacketService {
	public String grantRedPacket(HttpServletRequest req);
	public String unpackRedPacket(HttpServletRequest req);
	public String redPacketDetail(HttpServletRequest req);
}

package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;

public interface IntegralRedPackteService {
	public void grantRedPacket(HttpServletRequest req);
	public void unpackRedPacket(HttpServletRequest req);
	public void redPacketDetail(HttpServletRequest req);
}

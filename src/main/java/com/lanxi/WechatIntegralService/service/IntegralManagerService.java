package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;

import com.lanxi.WechatIntegralService.entity.IntegralTransfer;

public interface IntegralManagerService {
	public void getUserInfo(HttpServletRequest req);
	public String queryIntegralByWechat(HttpServletRequest req);
	public String queryIntegralByIdCard(HttpServletRequest req);
	public String queryIntegral(HttpServletRequest req);
	public String queryHistory(HttpServletRequest req);
	public String transferIntegral(HttpServletRequest req);
	public String modiferPhone(HttpServletRequest req);
}

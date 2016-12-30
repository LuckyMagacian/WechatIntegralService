package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;

public interface ValidService {
	public boolean validCode(HttpServletRequest req);
	public boolean validUserID(HttpServletRequest req);
	public boolean validIntegral(HttpServletRequest req);
}

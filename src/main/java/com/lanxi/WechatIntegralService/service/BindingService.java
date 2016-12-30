package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;

public interface BindingService {
	public void binding(HttpServletRequest req);
	public void getUserInfo(HttpServletRequest req);
	public void bindingAccount(HttpServletRequest req);
}

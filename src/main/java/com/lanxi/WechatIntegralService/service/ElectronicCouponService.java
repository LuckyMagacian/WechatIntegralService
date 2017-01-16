package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ElectronicCouponService {
	public String getElectronicCouponInfo(HttpServletRequest req,HttpServletResponse res);
	public String getElectronicCouponInfos(HttpServletRequest req,HttpServletResponse res);
}

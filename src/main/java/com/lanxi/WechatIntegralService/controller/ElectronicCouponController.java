package com.lanxi.WechatIntegralService.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanxi.WechatIntegralService.service.ElectronicCouponService;

@Controller
public class ElectronicCouponController {
	@Resource 
	private ElectronicCouponService service;
	@RequestMapping(value = "/couponInfo.do", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getElectronicCouponInfo(HttpServletResponse res, HttpServletRequest req){
		return service.getElectronicCouponInfo(req, res);
	}
	@RequestMapping(value = "/coupons.do", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getElectronicCoupons(HttpServletResponse res, HttpServletRequest req){
		return service.getElectronicCouponInfos(req, res);
	}
}

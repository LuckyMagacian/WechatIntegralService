package com.lanxi.WechatIntegralService.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lanxi.WechatIntegralService.entity.ElectronicCoupon;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.integral.report.ReturnMessage;
import com.lanxi.token.EasyToken;
@Service("electronicCouponService")
public class ElectronicCouponServiceImpl implements ElectronicCouponService {
	private static Logger logger=Logger.getLogger(ElectronicCouponServiceImpl.class);
	@Resource
	private DaoService dao;
	@SuppressWarnings("finally")
	@Override
	public String getElectronicCouponInfo(HttpServletRequest req, HttpServletResponse res) {
		ReturnMessage message=new ReturnMessage();
		try {
			req.setCharacterEncoding("utf-8");
			String tokenStr =req.getParameter("token");
			EasyToken token=EasyToken.verifyTokenRenew(tokenStr); 
			if(token==null){
				message.setRetCode("9998");
				message.setRetMsg("token过期!");
				message.setObj("token过期!");
				logger.info("token过期!");
				return message.toJson();
			}
			String couponId=req.getParameter("couponId");
			logger.info("查询电子券信息:couponId="+couponId+",token="+tokenStr);
			ElectronicCoupon coupon=dao.getElectronicCoupon(couponId);
			if(coupon==null){
				message.setRetCode("9998");
				message.setRetMsg("电子券不存在!");
			}else{
				message.setRetCode("0000");
				message.setRetMsg("查询成功!");
				message.setObj(coupon);
				logger.info("电子券信息:"+coupon);
			}
		} catch (Exception e) {
			message.setRetCode("9999");
			message.setRetMsg("查询异常!");
			throw new AppException("查询电子券详情异常!",e);
		}
		finally {
			return message.toJson();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String getElectronicCouponInfos(HttpServletRequest req, HttpServletResponse res) {
		ReturnMessage message=new ReturnMessage();
		try {
			req.setCharacterEncoding("utf-8");
			String tokenStr =req.getParameter("token");
			EasyToken token=EasyToken.verifyTokenRenew(tokenStr); 
			if(token==null){
				message.setRetCode("9998");
				message.setRetMsg("token过期!");
				message.setObj("token过期!");
				logger.info("token过期!");
				return message.toJson();
			}
			String openId=token.getInfo();
			logger.info("查询用户电子券信息:userId="+openId);
			List<ElectronicCoupon> coupons=dao.getElectronicCoupons(openId);
			if(coupons==null){
				message.setRetCode("9998");
				message.setRetMsg("该用户没有电子券!");
			}else{
				message.setRetCode("0000");
				message.setRetMsg("查询成功!");
				logger.info("用户电子券信息:"+coupons);
				for(ElectronicCoupon each:coupons)
					each.setCode("");
				message.setObj(coupons);
			}
		} catch (Exception e) {
			message.setRetCode("9999");
			message.setRetMsg("查询异常!");
			throw new AppException("查询电子券异常!",e);
		}finally {
			return message.toJson();
		}
	}

}

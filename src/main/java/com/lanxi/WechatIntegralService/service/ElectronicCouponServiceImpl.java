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
		EasyToken token=null;
		try {
			req.setCharacterEncoding("utf-8");
			String tokenStr =req.getParameter("token");
			token=EasyToken.verifyTokenRenew(tokenStr); 
			if(token==null){
				message.setRetCode("0001");
				message.setRetMsg("token过期!");
				message.setObj(null);
				message.setToken(null);
				logger.info("token过期!");
				return message.toJson();
			}
			String couponId=req.getParameter("couponId");
			logger.info("查询电子券信息:couponId="+couponId+",token="+tokenStr);
			ElectronicCoupon coupon=dao.getElectronicCoupon(couponId);
			if(coupon==null){
				message.setRetCode("3101");
				message.setRetMsg("电子券不存在!");
				message.setObj(null);
				message.setToken(token.toToken());
				logger.info("电子券不存在!");
			}else{
				message.setRetCode("0000");
				message.setRetMsg("查询成功!");
				message.setObj(coupon);
				message.setToken(token.toToken());
				logger.info("电子券信息:"+coupon);
			}
		} catch (Exception e) {
			message.setRetCode("9999");
			message.setRetMsg("查询电子券详情异常!");
			message.setObj(null);
			message.setToken(token.toToken());
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
		EasyToken token=null;
		try {
			req.setCharacterEncoding("utf-8");
			String tokenStr =req.getParameter("token");
			token=EasyToken.verifyTokenRenew(tokenStr); 
			if(token==null){
				message.setRetCode("0001");
				message.setRetMsg("token过期!");
				message.setObj(null);
				message.setToken(null);
				logger.info("token过期!");
				return message.toJson();
			}
			String openId=token.getInfo();
			logger.info("查询用户电子券信息:userId="+openId);
			List<ElectronicCoupon> coupons=dao.getElectronicCoupons(openId);
			if(coupons==null){
				message.setRetCode("3102");
				message.setRetMsg("该用户没有卡券!");
				message.setObj(null);
				message.setToken(token.toToken());
				logger.info("用户没有卡券");
			}else{
				message.setRetCode("0000");
				message.setRetMsg("查询成功!");
				message.setToken(token.toToken());
				logger.info("用户电子券信息:"+coupons);
				for(ElectronicCoupon each:coupons)
					each.setCode(null);
				message.setObj(coupons);
			}
		} catch (Exception e) {
			message.setRetCode("9999");
			message.setRetMsg("获取用户卡券异常!");
			message.setObj(null);
			message.setToken(token.toToken());
			throw new AppException("查询卡券异常!",e);
		}finally {
			return message.toJson();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String deleteElectronicCoupon(HttpServletRequest req, HttpServletResponse res) {
		ReturnMessage message=new ReturnMessage();
		EasyToken token=null;
		try {
			req.setCharacterEncoding("utf-8");
			String tokenStr =req.getParameter("token");
			token=EasyToken.verifyTokenRenew(tokenStr); 
			if(token==null){
				message.setRetCode("0001");
				message.setRetMsg("token过期!");
				message.setObj(null);
				message.setToken(null);
				logger.info("token过期!");
				return message.toJson();
			}
			String couponId=req.getParameter("couponId");
			logger.info("用户尝试删除卡券:couponId="+couponId+",token="+tokenStr+",userId="+token.getInfo());
			ElectronicCoupon coupon=dao.getElectronicCoupon(couponId);
			if(coupon==null){
				message.setRetCode("3101");
				message.setRetMsg("卡券不存在!");
				message.setObj(null);
				message.setToken(token.toToken());
			}else{
				dao.deleteElectronicCoupon(coupon);
				message.setRetCode("0000");
				message.setRetMsg("删除成功!");
				message.setObj(coupon);
				message.setToken(token.toToken());
				logger.info("卡券信息删除成功:"+coupon);
			}
		} catch (Exception e) {
			message.setRetCode("9999");
			message.setRetMsg("删除卡券异常!");
			message.setObj(null);
			message.setToken(token.toToken());
			throw new AppException("删除电子券详情异常!",e);
		}
		finally {
			return message.toJson();
		}
	}

}

package com.lanxi.WechatIntegralService.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.IntegralRedPacket;
import com.lanxi.WechatIntegralService.entity.RedPacketReceive;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.RandomUtil;
import com.lanxi.WechatIntegralService.util.TimeUtil;
import com.lanxi.integral.report.AddResBody;
import com.lanxi.integral.report.QueryResBody;
import com.lanxi.integral.report.ReduceResBody;
import com.lanxi.integral.report.ReturnMessage;
import com.lanxi.integral.service.IntegralService;
import com.lanxi.token.EasyToken;
import com.lanxi.wechat.entity.token.WebAccessToken;
import com.lanxi.wechat.entity.token.WebAccessTokenRequst;
import com.lanxi.wechat.manageer.TokenManager;
@Service("integralRedPacketService")
public class IntegralRedPacketServiceImpl implements IntegralRedPacketService {
	@Resource
	private DaoService dao;
	private static Logger logger=Logger.getLogger(IntegralRedPacketServiceImpl.class);
	
	public ReturnMessage toUnpackRedPacket(HttpServletRequest req,HttpServletResponse res){
			logger.info("用户尝试进入拆红包页面!");
			ReturnMessage message=null;
			EasyToken token=null;
		try {
			message=new ReturnMessage();
			req.setCharacterEncoding("utf-8");
			String redPacketId=req.getParameter("redPacketId");
			String code=req.getParameter("code");
			logger.info("请求信息code="+code+",redPacketId="+redPacketId);
			if(code==null||code.isEmpty()){
				message.setRetCode("3311");
				message.setRetMsg("未收到code,无法获取网页授权!");
				message.setObj(null);
				logger.info("未收到code,无法获取网页授权!");
				return message;
			}
			WebAccessToken webAccessToken=TokenManager.generatorWebAccessTokenMetadata(code);
			if(webAccessToken==null){
				message.setRetCode("3312");
				message.setRetMsg("授权失败!");
				message.setObj(null);
				logger.info("授权失败!");
				return message;
			}
			logger.info("请求授权成功:"+webAccessToken);
			String openId=webAccessToken.getOpenId();
			logger.info("用户开放微信号:"+openId);
			token=new EasyToken();
			token.setInfo(openId);
			token.setValidTo(System.currentTimeMillis() + Long.parseLong(ConfigUtil.get("easyTokenExpiryTime")) * 1000);
			message.setRetCode("0000");
			message.setToken(token.toToken());
			message.setRetMsg("获取微信号成功!");
			logger.info("获取微信号成功!");
			message.setObj(redPacketId);
		} catch (Exception e) {
			message.setRetCode("9999");
			message.setRetMsg("进入拆红包页面异常!");
			message.setObj(null);
			message.setToken(token==null?null:token.toToken());
			throw new AppException("进入拆红包页面异常!",e);
		}
		return message;
	}
	
	
	@SuppressWarnings("finally")
	@Override
	public String grantRedPacket(HttpServletRequest req,HttpServletResponse res) {
		ReturnMessage returnMessage=new ReturnMessage();
		EasyToken token=null;
		try {
			req.setCharacterEncoding("utf-8");
			String count=req.getParameter("redPacketCount");
			String integral=req.getParameter("redPacketIntegral");
			String name=req.getParameter("redPacketName");
			String tokenStr =req.getParameter("token");
			token=EasyToken.verifyTokenRenew(tokenStr); 
			if(token==null){
				returnMessage.setRetCode("0001");
				returnMessage.setRetMsg("token过期!");
				returnMessage.setObj(null);
				returnMessage.setToken(null);
				//TODO 删除此处token生成
				token=new EasyToken();
				token.setInfo("o5uSlw3veETF28qOR7bqqzJpHa44");
				token.setValidTo(token.getValidFrom() + Long.parseLong(ConfigUtil.get("easyTokenExpiryTime"))*1000);
				returnMessage.setToken(token.toToken());
				logger.info("token过期!");
				return returnMessage.toJson();
			}
			String userId=token.getInfo();
			logger.info("用户尝试发红包,wechatId="+userId+",name="+name+",count="+count+",integral="+integral+",token="+tokenStr);
			
			AccountBinding account=dao.getAccount(userId);
			logger.info("用户信息:"+account);
			if(account==null){
				returnMessage.setRetCode("3301");
				returnMessage.setRetMsg("用户未绑定综合积分帐号!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("用户未绑定综合积分帐号!");
				return returnMessage.toJson();
			}
			returnMessage=IntegralService.queryIntegral(account.getIntegralAccount());
			logger.info("积分查询结果:"+returnMessage);
			
			if(!returnMessage.getRetCode().trim().equals("0000")){
				returnMessage.setRetCode("3302");
				returnMessage.setRetMsg("积分查询失败!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("积分查询异常!");
				return returnMessage.toJson();
			}
			
			Integer point=(int)Double.parseDouble(((QueryResBody)returnMessage.getObj()).getTotalPoints());
			
			if(Integer.parseInt(integral)>point){
				returnMessage.setRetCode("3303");
				returnMessage.setRetMsg("积分不足!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("用户积分不足!");
				return returnMessage.toJson();
			}
			
			returnMessage=IntegralService.reduceIntegral(account.getIntegralAccount(), integral);
			logger.info("积分扣除结果:"+returnMessage);
			
			if(!returnMessage.getRetCode().trim().equals("0000")){
				returnMessage.setRetCode("3304");
				returnMessage.setRetMsg("积分扣除失败!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("积分扣除失败!");
				return returnMessage.toJson();
			}
			IntegralRedPacket redPacket=new IntegralRedPacket();
			redPacket.setPlateformSerialId(((ReduceResBody)returnMessage.getObj()).getSerialNo());
			redPacket.setOpenId(userId);
			redPacket.setNickName(account.getNickName());
			redPacket.setRedPacketName(name);
			redPacket.setRedPacketId(TimeUtil.getDateTime()+RandomUtil.getRandomNumber(4));
			redPacket.setRedPacketCount(Integer.parseInt(count));
			redPacket.setRedPacketLessCount(redPacket.getRedPacketCount());
			redPacket.setTotalIntegral(Integer.parseInt(integral));
			redPacket.setLessIntegral(redPacket.getTotalIntegral());
			redPacket.setRedPacketUrl(TokenManager.generatorWebTokenCodeUrl(ConfigUtil.get("unpackRedPacketUrl")+"redPacketId="+redPacket.getRedPacketId(),WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_DETAIL));
			redPacket.setRedPacketStatus(IntegralRedPacket.RED_PACKET_STATUS_NORML);
			Long startTime=System.currentTimeMillis();
			Long overTime=startTime+Long.parseLong(ConfigUtil.get("redPacketExpiryTime"))*1000;
			redPacket.setStartTime(TimeUtil.formatDateTime(new Date(startTime)));
			redPacket.setOverTime(TimeUtil.formatDateTime(new Date(overTime)));
			redPacket.setReceivers("");
			redPacket.setBeiy(account.getHeadimgUrl());
			logger.info("生成红包:"+redPacket);
			dao.addIntegralRedPacket(redPacket);
			returnMessage.setRetCode("0000");
			returnMessage.setRetMsg("红包创建完成!");
			returnMessage.setObj(redPacket);
			returnMessage.setToken(token.toToken());
			return JSONObject.toJSONString(returnMessage);
		} catch (Exception e) {
			returnMessage.setRetCode("9999");
			returnMessage.setRetMsg("红包创建异常!");
			returnMessage.setObj(null);
			returnMessage.setToken(token.toToken());
			throw new AppException("红包功能异常!",e);
		}finally {
			return returnMessage.toJson();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String unpackRedPacket(HttpServletRequest req,HttpServletResponse res) {
		ReturnMessage returnMessage=new ReturnMessage();
		EasyToken token=null;
		try {
			req.setCharacterEncoding("utf-8");
			String redPacketId=req.getParameter("redPacketId");
			String tokenStr=req.getParameter("token");
			token=EasyToken.verifyTokenRenew(tokenStr);
			if(token==null){
				returnMessage.setRetCode("0001");
				returnMessage.setRetMsg("token过期!");
				returnMessage.setObj(null);
				returnMessage.setToken(null);
				logger.info("token过期!");
				return returnMessage.toJson();
			}
			String userId=token.getInfo();
			logger.info("用户尝试拆红包:userId="+userId+",redPacketId="+redPacketId+",token="+tokenStr);
//			String userId=req.getParameter("userId").trim();
			
			AccountBinding account=dao.getAccount(userId);
			if(account==null){
				returnMessage.setRetCode("3301");
				returnMessage.setRetMsg("用户未绑定综合积分帐号!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("用户未绑定综合积分帐号!");
				return returnMessage.toJson();
			}
			
			IntegralRedPacket redPacket=dao.getRedPacket(redPacketId);
			if(redPacket==null){
				returnMessage.setRetCode("3305");
				returnMessage.setRetMsg("红包不存在!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("红包不存在!");
				return returnMessage.toJson();
			}
			// TODO
			if(redPacket.getReceivers().contains(userId)){
				//3306
				returnMessage.setRetCode("0000");
				returnMessage.setRetMsg("红包已领过!");
				returnMessage.setObj(redPacketId);
				returnMessage.setToken(token.toToken());
				logger.info("红包已领过!");
				return returnMessage.toJson();
			}
			if(IntegralRedPacket.RED_PACKET_STATUS_OVERTIME.equals(redPacket.getRedPacketStatus())){
				returnMessage.setRetCode("3307");
				returnMessage.setRetMsg("红包已过期!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("红包已过期!");
				return returnMessage.toJson();
			}
			if(IntegralRedPacket.RED_PACKET_STATUS_NONE.equals(redPacket.getRedPacketStatus())){
				returnMessage.setRetCode("3308");
				returnMessage.setRetMsg("红包已领完!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("红包已领完!");
				return returnMessage.toJson();
			}
			RedPacketReceive receive=new RedPacketReceive();
			receive.setOpenId(userId);
			receive.setReceiveTime(TimeUtil.getDateTime());
			receive.setRedPacketId(redPacket.getRedPacketId());
			receive.setNickName(account.getNickName());
			receive.setBeiy(account.getHeadimgUrl());
			if(redPacket.getRedPacketLessCount()==redPacket.getRedPacketCount())
				redPacket.setReceivers(account.getOpenId());
			else
				redPacket.setReceivers(redPacket.getReceivers()+","+account.getOpenId());
			
			if(redPacket.getRedPacketLessCount()==1){
				logger.info("最后一个红包!");
				receive.setIntegral(redPacket.getLessIntegral());
				redPacket.setLessIntegral(0);
				redPacket.setRedPacketLessCount(0);
				redPacket.setRedPacketStatus(IntegralRedPacket.RED_PACKET_STATUS_NONE);
				logger.info("拆得积分:"+receive.getIntegral());
			}else{
				Random random=new Random();
				int min=1;
				int max=redPacket.getLessIntegral()*5/redPacket.getRedPacketLessCount();
				Double percent=random.nextInt(100)/100D;
				receive.setIntegral((int)(max*percent));	
				receive.setIntegral(receive.getIntegral()<min?min:receive.getIntegral());
				redPacket.setLessIntegral(redPacket.getLessIntegral()-receive.getIntegral());
				
				redPacket.setRedPacketLessCount(redPacket.getRedPacketLessCount()-1);
				logger.info("拆得积分:"+receive.getIntegral());
			}
			returnMessage=IntegralService.addIntegral(account.getIntegralAccount(), receive.getIntegral()+"");
			logger.info("积分增加结果:"+returnMessage);
			if(!returnMessage.getRetCode().trim().equals("0000")){
				returnMessage.setRetCode("3309");
				returnMessage.setRetMsg("积分增加失败!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("积分增加失败!");
				return returnMessage.toJson();
			}
			receive.setPlateformSerialId(((AddResBody)returnMessage.getObj()).getSerialNo());
			dao.addRedPacketReceive(receive);
			logger.info("增加拆红包记录:"+receive);
			dao.updateIntegralRedPacket(redPacket);
			logger.info("更新红包状态!");
			
			returnMessage.setRetCode("0000");
			returnMessage.setRetMsg("获得积分"+receive.getIntegral());
			returnMessage.setObj("获得积分"+receive.getIntegral());
			returnMessage.setToken(token.toToken());
			return returnMessage.toJson();
		} catch (Exception e) {
			returnMessage.setRetCode("9999");
			returnMessage.setRetMsg("拆红包异常");
			returnMessage.setObj(null);
			returnMessage.setToken(token.toToken());
			throw new AppException("拆红包异常!",e);
		}finally {
			return returnMessage.toJson();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String redPacketDetail(HttpServletRequest req,HttpServletResponse res) {
		ReturnMessage returnMessage=new ReturnMessage();
		EasyToken token=null;
		try{
			req.setCharacterEncoding("utf-8");
			String redPacketId=req.getParameter("redPacketId");
			String tokenStr=req.getParameter("token");
			token=EasyToken.verifyTokenRenew(tokenStr);
			if(token==null){
				returnMessage.setRetCode("0001");
				returnMessage.setRetMsg("token过期!");
				returnMessage.setObj(null);
				returnMessage.setToken(null);
				logger.info("token过期!");
				return returnMessage.toJson();
			}
			String userId=token.getInfo();
			logger.info("用户查询红包领取详情!"+"userId="+userId+",token="+tokenStr+",redPacketId="+redPacketId);
			List<RedPacketReceive> receives=dao.getReceives(redPacketId);
			logger.info("红包领取详情"+receives);
			if(receives==null){
				returnMessage.setRetCode("3305");
				returnMessage.setRetMsg("红包不存在!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("红包不存在!");
				return returnMessage.toJson();
			}
			RedPacketReceive receive=dao.getReceiveRecord(redPacketId, userId);
			if(receive==null){
				returnMessage.setRetCode("3310");
				returnMessage.setRetMsg("用户未领取过该红包不允许查询!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				logger.info("用户未领取过该红包不允许查询!");
				return returnMessage.toJson();
			}
			logger.info("发起查询用户记录"+receive);
			returnMessage.setRetCode("0000");
			returnMessage.setRetMsg(receive.getIntegral()+"");
			returnMessage.setObj(receives);
			returnMessage.setToken(token.toToken());
		}catch (Exception e) {
			returnMessage.setRetCode("9999");
			returnMessage.setRetMsg("查看红包领取详情异常");
			returnMessage.setObj(null);
			returnMessage.setToken(token.toToken());
			throw new AppException("查看红包领取详情异常",e);
		}finally {
			return returnMessage.toJson();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String redPacketInfo(HttpServletRequest req, HttpServletResponse res) {
		ReturnMessage returnMessage=new ReturnMessage();
		EasyToken token=null;
		try{
			req.setCharacterEncoding("utf-8");
			String redPacketId=req.getParameter("redPacketId");
			String tokenStr=req.getParameter("token");
			token=EasyToken.verifyTokenRenew(tokenStr);
			if(token==null){
				returnMessage.setRetCode("0001");
				returnMessage.setRetMsg("token过期!");
				returnMessage.setObj(null);
				returnMessage.setToken(null);
				logger.info("token过期!");
				return returnMessage.toJson();
			}
			String userId=token.getInfo();
			logger.info("查询红包详情!"+"userId="+userId+",token="+tokenStr+",redPacketId="+redPacketId);
			IntegralRedPacket redPacket=dao.getRedPacket(redPacketId);
			if(redPacket==null){
				returnMessage.setRetCode("3305");
				returnMessage.setRetMsg("红包不存在!");
				returnMessage.setObj(null);
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
			returnMessage.setRetCode("0000");
			returnMessage.setRetMsg("红包详情!");
			returnMessage.setObj(redPacket);
		}catch (Exception e) {
			returnMessage.setRetCode("9999");
			returnMessage.setRetMsg("查看红包详情异常");
			returnMessage.setObj(null);
			returnMessage.setToken(token.toToken());
			throw new AppException("查看红包详情异常",e);
		}finally {
			return returnMessage.toJson();
		}
	}

	
}

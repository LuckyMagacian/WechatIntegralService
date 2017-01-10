package com.lanxi.WechatIntegralService.service;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

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

public class IntegralRedPacketServiceImpl implements IntegralRedPacketService {
	@Resource
	private DaoService dao;
	private static Logger logger=Logger.getLogger(IntegralRedPacketServiceImpl.class);
	@Override
	public String grantRedPacket(HttpServletRequest req) {
		try {
			ReturnMessage returnMessage=new ReturnMessage();
			req.setCharacterEncoding("utf-8");
			String count=req.getParameter("redPacketCount").trim();
			String integral=req.getParameter("redPacketIntegral").trim();
			String name=req.getParameter("redPacketName").trim();
			String tokenStr =req.getParameter("token").trim();
			EasyToken token=EasyToken.verifyTokenRenew(tokenStr);
			if(token==null){
				returnMessage.setRetCode("9998");
				returnMessage.setRetMsg("token过期!");
				returnMessage.setObj("token过期!");
				logger.info("token过期!");
				return returnMessage.toJson();
			}
			String userId=req.getParameter("userId").trim();
			logger.info("用户尝试发红包,wechatId="+userId+",name="+name+",count="+count+",integral="+integral+",token="+tokenStr);
			
			AccountBinding account=dao.getAccount(userId);
			logger.info("用户信息:"+account);
			
			returnMessage=IntegralService.queryIntegral(account.getIntegralAccount());
			logger.info("积分查询结果:"+returnMessage);
			
			if(!returnMessage.getRetCode().trim().equals("0000")){
				returnMessage.setRetMsg("积分查询失败!");
				returnMessage.setObj("积分查询失败!");
				logger.info("积分查询异常!");
				return returnMessage.toJson();
			}
			
			Integer point=Integer.parseInt(((QueryResBody)returnMessage.getObj()).getTotalPoints());
			
			if(Integer.parseInt(integral)<point){
				returnMessage.setRetMsg("积分不足!");
				returnMessage.setObj("积分不足!");
				logger.info("用户积分不足!");
				return returnMessage.toJson();
			}
			
			returnMessage=IntegralService.reduceIntegral(account.getIntegralAccount(), integral);
			logger.info("积分扣除结果:"+returnMessage);
			
			if(!returnMessage.getRetCode().trim().equals("0000")){
				returnMessage.setRetMsg("积分扣除失败!");
				returnMessage.setObj("积分扣除失败!");
				logger.info("积分扣除失败!");
				return returnMessage.toJson();
			}
			IntegralRedPacket redPacket=new IntegralRedPacket();
			redPacket.setPlateformSerialId(((ReduceResBody)returnMessage.getObj()).getSerialNo());
			redPacket.setOpenId(userId);
			redPacket.setRedPacketName(name);
			redPacket.setRedPacketId(TimeUtil.getDateTime()+RandomUtil.getRandomNumber(4));
			redPacket.setRedPacketCount(Integer.parseInt(count));
			redPacket.setRedPacketLessCount(redPacket.getRedPacketCount());
			redPacket.setTotalIntegral(Integer.parseInt(integral));
			redPacket.setLessIntegeral(redPacket.getTotalIntegral());
			redPacket.setRedPacketUrl(ConfigUtil.get("unpackRedPacketUrl?redPacketId=")+redPacket.getRedPacketId());
			redPacket.setRedPacketStatus(IntegralRedPacket.RED_PACKET_STATUS_NORML);
			Long startTime=System.currentTimeMillis();
			Long overTime=startTime+Long.parseLong(ConfigUtil.get("redPacketExpiryTime"))*1000;
			redPacket.setStartTime(TimeUtil.formatDate(new Date(startTime)));
			redPacket.setOverTime(TimeUtil.formatDate(new Date(overTime)));
			redPacket.setReceivers("");
			logger.info("生成红包:"+redPacket);
			dao.addIntegralRedPacket(redPacket);
			returnMessage.setRetCode("0000");
			returnMessage.setRetMsg("红包创建完成!");
			returnMessage.setObj(redPacket.getRedPacketUrl());
			return JSONObject.toJSONString(returnMessage);
		} catch (Exception e) {
			throw new AppException("红包功能异常!",e);
		}
	}

	@Override
	public String unpackRedPacket(HttpServletRequest req) {
		try {
			ReturnMessage returnMessage=new ReturnMessage();
			req.setCharacterEncoding("utf-8");
			String redPacketId=req.getParameter("redPacketId");
			String tokenStr=req.getParameter("token");

			EasyToken token=EasyToken.verifyTokenRenew(tokenStr);
			if(token==null){
				returnMessage.setRetCode("9998");
				returnMessage.setRetMsg("token过期!");
				returnMessage.setObj("token过期!");
				logger.info("token过期!");
				return returnMessage.toJson();
			}
//			String userId=token.getInfo();
			String userId=req.getParameter("userId").trim();
			
			AccountBinding account=dao.getAccount(userId);
			if(account==null){
				returnMessage.setRetCode("9989");
				returnMessage.setRetMsg("用户未绑定综合积分帐号!");
				returnMessage.setObj("用户未绑定综合积分帐号!");
				logger.info("用户未绑定综合积分帐号!");
				return returnMessage.toJson();
			}
			
			IntegralRedPacket redPacket=dao.getRedPacket(redPacketId);
			if(redPacket==null){
				returnMessage.setRetCode("9997");
				returnMessage.setRetMsg("红包不存在!");
				returnMessage.setObj("红包不存在!");
				logger.info("红包不存在!");
				return returnMessage.toJson();
			}
			// TODO
			if(redPacket.getReceivers().contains(userId)){
				returnMessage.setRetCode("9996");
				returnMessage.setRetMsg("红包已领过!");
				returnMessage.setObj("红包已领过!");
				logger.info("红包已领过!");
				return returnMessage.toJson();
			}
			if(IntegralRedPacket.RED_PACKET_STATUS_OVERTIME.equals(redPacket.getRedPacketStatus())){
				returnMessage.setRetCode("9995");
				returnMessage.setRetMsg("红包已过期!");
				returnMessage.setObj("红包已过期!");
				logger.info("红包已过期!");
				return returnMessage.toJson();
			}
			if(IntegralRedPacket.RED_PACKET_STATUS_NONE.equals(redPacket.getRedPacketStatus())){
				returnMessage.setRetCode("9994");
				returnMessage.setRetMsg("红包已领完!");
				returnMessage.setObj("红包已领完!");
				logger.info("红包已领完!");
				return returnMessage.toJson();
			}
			RedPacketReceive receive=new RedPacketReceive();
			receive.setOpenId(userId);
			receive.setReceiveTime(TimeUtil.getDateTime());
			receive.setRedPacketId(redPacket.getRedPacketId());
			
			if(redPacket.getRedPacketLessCount()==redPacket.getRedPacketCount())
				redPacket.setReceivers(account.getOpenId());
			else
				redPacket.setReceivers(redPacket.getReceivers()+","+account.getOpenId());
			
			if(redPacket.getRedPacketLessCount()==1){
				logger.info("最后一个红包!");
				receive.setIntegral(redPacket.getLessIntegeral());
				redPacket.setLessIntegeral(0);
				redPacket.setRedPacketLessCount(0);
				redPacket.setRedPacketStatus(IntegralRedPacket.RED_PACKET_STATUS_NONE);
				logger.info("拆得积分:"+receive.getIntegral());
			}else{
				Random random=new Random();
				Double percent=random.nextInt(100)/100D;
				receive.setIntegral((int)(redPacket.getLessIntegeral()*percent));	
				redPacket.setLessIntegeral(redPacket.getLessIntegeral()-receive.getIntegral());
				redPacket.setRedPacketLessCount(redPacket.getRedPacketLessCount()-1);
			}
			returnMessage=IntegralService.addIntegral(account.getIntegralAccount(), receive.getIntegral()+"");
			logger.info("积分增加结果:"+returnMessage);
			if(!returnMessage.getRetCode().trim().equals("0000")){
				returnMessage.setRetMsg("积分增加失败!");
				returnMessage.setObj("积分增加失败!");
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
			return returnMessage.toJson();
		} catch (Exception e) {
			throw new AppException("拆红包异常!",e);
		}
	}

	@Override
	public String redPacketDetail(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

}

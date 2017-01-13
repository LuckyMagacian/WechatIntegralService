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
@Service("integralRedPacketService")
public class IntegralRedPacketServiceImpl implements IntegralRedPacketService {
	@Resource
	private DaoService dao;
	private static Logger logger=Logger.getLogger(IntegralRedPacketServiceImpl.class);
	@SuppressWarnings("finally")
	@Override
	public String grantRedPacket(HttpServletRequest req,HttpServletResponse res) {
		ReturnMessage returnMessage=new ReturnMessage();
		try {
			req.setCharacterEncoding("utf-8");
			String count=req.getParameter("redPacketCount");
			String integral=req.getParameter("redPacketIntegral");
			String name=req.getParameter("redPacketName");
			String tokenStr =req.getParameter("token");
			EasyToken token=EasyToken.verifyTokenRenew(tokenStr); 
			if(token==null){
				returnMessage.setRetCode("9998");
				returnMessage.setRetMsg("token过期!");
				returnMessage.setObj("token过期!");
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
			
			returnMessage=IntegralService.queryIntegral(account.getIntegralAccount());
			logger.info("积分查询结果:"+returnMessage);
			
			if(!returnMessage.getRetCode().trim().equals("0000")){
				returnMessage.setRetMsg("积分查询失败!");
				returnMessage.setObj("积分查询失败!");
				logger.info("积分查询异常!");
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
			
			Integer point=(int)Double.parseDouble(((QueryResBody)returnMessage.getObj()).getTotalPoints());
			
			if(Integer.parseInt(integral)>point){
				returnMessage.setRetMsg("积分不足!");
				returnMessage.setObj("积分不足!");
				logger.info("用户积分不足!");
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
			
			returnMessage=IntegralService.reduceIntegral(account.getIntegralAccount(), integral);
			logger.info("积分扣除结果:"+returnMessage);
			
			if(!returnMessage.getRetCode().trim().equals("0000")){
				returnMessage.setRetMsg("积分扣除失败!");
				returnMessage.setObj("积分扣除失败!");
				logger.info("积分扣除失败!");
				returnMessage.setToken(token.toToken());
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
			redPacket.setLessIntegral(redPacket.getTotalIntegral());
			redPacket.setRedPacketUrl(ConfigUtil.get("unpackRedPacketUrl")+"redPacketId="+redPacket.getRedPacketId());
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
			returnMessage.setObj(redPacket);
			returnMessage.setToken(token.toToken());
			return JSONObject.toJSONString(returnMessage);
		} catch (Exception e) {
			returnMessage.setRetCode("9999");
			returnMessage.setRetMsg("红包创建异常!");
			returnMessage.setObj(null);
			throw new AppException("红包功能异常!",e);
		}finally {
			return returnMessage.toJson();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String unpackRedPacket(HttpServletRequest req,HttpServletResponse res) {
		ReturnMessage returnMessage=new ReturnMessage();
		try {
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
			String userId=token.getInfo();
			logger.info("用户尝试拆红包:userId="+userId+",redPacketId="+redPacketId+",token="+tokenStr);
//			String userId=req.getParameter("userId").trim();
			
			AccountBinding account=dao.getAccount(userId);
			if(account==null){
				returnMessage.setRetCode("9989");
				returnMessage.setRetMsg("用户未绑定综合积分帐号!");
				returnMessage.setObj("用户未绑定综合积分帐号!");
				logger.info("用户未绑定综合积分帐号!");
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
			
			IntegralRedPacket redPacket=dao.getRedPacket(redPacketId);
			if(redPacket==null){
				returnMessage.setRetCode("9997");
				returnMessage.setRetMsg("红包不存在!");
				returnMessage.setObj("红包不存在!");
				logger.info("红包不存在!");
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
			// TODO
			if(redPacket.getReceivers().contains(userId)){
				returnMessage.setRetCode("9996");
				returnMessage.setRetMsg("红包已领过!");
				returnMessage.setObj("红包已领过!");
				logger.info("红包已领过!");
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
			if(IntegralRedPacket.RED_PACKET_STATUS_OVERTIME.equals(redPacket.getRedPacketStatus())){
				returnMessage.setRetCode("9995");
				returnMessage.setRetMsg("红包已过期!");
				returnMessage.setObj("红包已过期!");
				logger.info("红包已过期!");
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
			if(IntegralRedPacket.RED_PACKET_STATUS_NONE.equals(redPacket.getRedPacketStatus())){
				returnMessage.setRetCode("9994");
				returnMessage.setRetMsg("红包已领完!");
				returnMessage.setObj("红包已领完!");
				logger.info("红包已领完!");
				returnMessage.setToken(token.toToken());
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
				receive.setIntegral(redPacket.getLessIntegral());
				redPacket.setLessIntegral(0);
				redPacket.setRedPacketLessCount(0);
				redPacket.setRedPacketStatus(IntegralRedPacket.RED_PACKET_STATUS_NONE);
				logger.info("拆得积分:"+receive.getIntegral());
			}else{
				Random random=new Random();
				Double percent=random.nextInt(100)/100D;
				receive.setIntegral((int)(redPacket.getLessIntegral()*percent));	
				redPacket.setLessIntegral(redPacket.getLessIntegral()-receive.getIntegral());
				redPacket.setRedPacketLessCount(redPacket.getRedPacketLessCount()-1);
			}
			returnMessage=IntegralService.addIntegral(account.getIntegralAccount(), receive.getIntegral()+"");
			logger.info("积分增加结果:"+returnMessage);
			if(!returnMessage.getRetCode().trim().equals("0000")){
				returnMessage.setRetMsg("积分增加失败!");
				returnMessage.setObj("积分增加失败!");
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
			throw new AppException("拆红包异常!",e);
		}finally {
			return returnMessage.toJson();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String redPacketDetail(HttpServletRequest req,HttpServletResponse res) {
		ReturnMessage returnMessage=new ReturnMessage();
		try{
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
			String userId=token.getInfo();
			logger.info("用户查询红包领取详情!"+"userId="+userId+",token="+tokenStr+",redPacketId="+redPacketId);
			List<RedPacketReceive> receives=dao.getReceives(redPacketId);
			logger.info("红包领取详情"+receives);
			if(receives==null){
				returnMessage.setRetCode("9998");
				returnMessage.setRetMsg("红包不存在!");
				returnMessage.setObj("红包不存在!");
				logger.info("不存在!");
				return returnMessage.toJson();
			}
			RedPacketReceive receive=dao.getReceiveRecord(redPacketId, userId);
			logger.info("发起查询用户记录"+receive);
			returnMessage.setRetCode("0000");
			returnMessage.setRetMsg(receive.getIntegral()+"");
			returnMessage.setObj(receives);
		}catch (Exception e) {
			returnMessage.setRetCode("9999");
			returnMessage.setRetMsg("查看红包领取详情异常");
			returnMessage.setObj(null);
			throw new AppException("查看红包领取详情异常",e);
		}finally {
			return returnMessage.toJson();
		}
	}

}

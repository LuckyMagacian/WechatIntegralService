package com.lanxi.WechatIntegralService.service;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.ElectronicCoupon;
import com.lanxi.WechatIntegralService.entity.Game;
import com.lanxi.WechatIntegralService.entity.Gift;
import com.lanxi.WechatIntegralService.entity.GiftOrder;
import com.lanxi.WechatIntegralService.entity.IntegralGame;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.WechatIntegralService.util.RandomUtil;
import com.lanxi.WechatIntegralService.util.TimeUtil;
import com.lanxi.gift.report.BaoWen;
import com.lanxi.gift.report.ReqHead;
import com.lanxi.gift.report.ReqMsg;
import com.lanxi.integral.report.AddResBody;
import com.lanxi.integral.report.QueryResBody;
import com.lanxi.integral.report.ReduceResBody;
import com.lanxi.integral.report.ReturnMessage;
import com.lanxi.integral.service.IntegralService;
import com.lanxi.token.EasyToken;
@Service("integralGameService")
public class IntegralGameServiceImpl implements IntegralGameService {
	private static Logger logger=Logger.getLogger(IntegralGameServiceImpl.class);
	@Resource
	private DaoService dao;
	
	private static Random random=new Random();
	@SuppressWarnings("finally")
	@Override
	public String startGame(HttpServletRequest req) {
		ReturnMessage returnMessage=new ReturnMessage();
		try{
			req.setCharacterEncoding("utf-8");
			String gameId=req.getParameter("gameId");
			String tokenStr =req.getParameter("token");
			EasyToken token=EasyToken.verifyTokenRenew(tokenStr);
			if(token==null){
				returnMessage.setRetCode("9998");
				returnMessage.setRetMsg("token过期!");
				returnMessage.setObj("token过期!");
				logger.info("token过期!");
				return returnMessage.toJson();
			}
			String userId=token.getInfo();
//			String userId=req.getParameter("userId");
			logger.info("用户参与游戏,gameId="+gameId+",token="+token+",userId="+userId);
			
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
			Game   game =dao.getGame(gameId);
			if(game==null){
				returnMessage.setRetCode("9997");
				returnMessage.setRetMsg("游戏不存在!");
				returnMessage.setObj("游戏不存在!");
				logger.info("游戏不存在!");
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
			logger.info("游戏信息:"+game);
			
			if(game.getIntegral()>point){
				returnMessage.setRetCode("9997");
				returnMessage.setRetMsg("积分不足!");
				returnMessage.setObj("积分不足!");
				logger.info("积分不足!");
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
			logger.info("请求扣除积分-"+game.getIntegral());
			returnMessage=IntegralService.reduceIntegral(account.getIntegralAccount(), game.getIntegral()+"");
			logger.info("积分扣除结果:"+returnMessage);
			
			if(!returnMessage.getRetCode().trim().equals("0000")){
				returnMessage.setRetMsg("积分扣除失败!");
				returnMessage.setObj("积分扣除失败!");
				logger.info("积分扣除失败!");
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
				
			double prize=random.nextInt(10000)/10000D;
			logger.info("摇奖结果:"+prize);
			
			Integer prizeLevel=game.getPrize(prize);
			logger.info("奖励等级(null为未中奖):"+prizeLevel);
			
			IntegralGame integralGame=new IntegralGame();
			integralGame.setSerialId(TimeUtil.getDateTime()+RandomUtil.getRandomNumber(6));
			integralGame.setGame(game);
			integralGame.setOpenId(userId);
			integralGame.setPlateformSerialId(((ReduceResBody)returnMessage.getObj()).getSerialNo());
			
			
			if(prizeLevel==null){
				returnMessage.setRetMsg("未中奖!");
				returnMessage.setObj("未中奖!");
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
			}
			
			Gift gift=dao.getGift(game.getId(), prizeLevel);
			logger.info("奖品:"+gift);
			integralGame.setResult(gift.getName());
			
			if(gift.getMerchantId()==null||gift.getMerchantId().isEmpty()){
				Integer pointPrize=gift.getIntegralValue();
				returnMessage=IntegralService.addIntegral(account.getIntegralAccount(), pointPrize+"");
				integralGame.setBeiy(((AddResBody)returnMessage.getObj()).getSerialNo());
				logger.info("积分增加结果"+returnMessage);
				if(returnMessage.getRetCode().equals("0000")){
					returnMessage.setRetCode("0000");
					returnMessage.setRetMsg("获得了"+gift.getIntegralValue()+"积分!");
					returnMessage.setObj(gift.getPrizeLevel());
				}else{
					returnMessage.setRetCode("0000");
					returnMessage.setRetMsg("未中奖");
					returnMessage.setObj(null);
					integralGame.setRemark("加分失败,修正中奖结果为未中奖!");
				}
				returnMessage.setToken(token.toToken());
//				return returnMessage.toJson();
			}else{
				returnMessage=dealEleGift(gift,account);
				if(!returnMessage.getRetCode().equals("0000")){
					returnMessage.setRetMsg("获得了"+gift.getName());
					returnMessage.setObj(gift.getPrizeLevel());
				}else{
					returnMessage.setRetMsg("未中奖!");
					returnMessage.setObj(null);
					integralGame.setRemark("电子券下单失败,修正中奖结果为未中奖!");
				}
			}
			dao.addIntegralGame(integralGame);
			logger.info("增加游戏记录:"+integralGame);
		}catch (Exception e) {
			returnMessage.setRetCode("9999");
			returnMessage.setRetMsg("游戏异常!");
			returnMessage.setObj(null);
			throw new AppException("游戏开始异常",e);
		}finally {
			return returnMessage.toJson();
		}		
	}

	@Override
	public void getGameResult(HttpServletRequest req) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getGameReward(HttpServletRequest req) {
		// TODO Auto-generated method stub
		
	}

	private ReturnMessage dealEleGift(Gift eleGift,AccountBinding account){
		ReturnMessage message=new ReturnMessage();
		try {
			logger.info("开始处理电子券!");
			ReqHead head=new ReqHead();
			head.setMsgId(TimeUtil.getDateTime()+RandomUtil.getRandomNumber(6));
			head.setMsgNo(head.getMsgId().substring(0,14));
			head.setReserve("");
			ReqMsg  body=new ReqMsg();
			body.setPhone(account.getBindingPhone());
			body.setSkuCode(eleGift.getMerchantId());
			body.setCount(eleGift.getCount()+"");
			BaoWen baoWen=new BaoWen();
			baoWen.setHead(head);
			baoWen.setMsg(body);
			baoWen.sign();
			logger.info("电子礼品平台请求报文:"+baoWen.toDocument().asXML());
			GiftOrder order=new GiftOrder();
			order.setOrderId(head.getMsgId());
			order.setOpenId(account.getOpenId());
			order.setGiftId(eleGift.getId());
			order.setGiftCount(eleGift.getCount());
			order.setWorkTime(head.getWorkTime());
			order.setStatus(GiftOrder.ORDER_STATUS_WAIT);
			dao.addGiftOrder(order);
			logger.info("增加订单记录:"+order);
			String rs=HttpUtil.postXml(baoWen.toDocument().asXML(), ConfigUtil.get("giftUrl"), "GBK");
			logger.info("电子礼品平台响应信息:"+rs);
			Document doc=DocumentHelper.parseText(rs);
			if(!doc.selectSingleNode("//ResCode").getText().trim().equals("0000")){
				logger.info("下单失败,订单异常!原因"+doc.selectSingleNode("//ResMsg").getText());
				order.setStatus(GiftOrder.ORDER_STATUS_FAIL);
				dao.updateGiftOrder(order);
				logger.info("更新电子券订单信息:"+order);
				logger.info("电子券购买失败!");
				message.setRetCode("9998");
				message.setRetMsg("电子券奖品处理失败!");
			}else{
				StringBuffer codes=new StringBuffer("");
				@SuppressWarnings("unchecked")
				List<Node> nodes=doc.selectNodes("//Code");
				if(nodes!=null&&!nodes.isEmpty())
				for(Node each:nodes){
					ElectronicCoupon coupon=new ElectronicCoupon();
					coupon.setId(TimeUtil.getDateTime()+RandomUtil.getRandomNumber(6));
					coupon.setCode(each.getText());
					coupon.setImageCode(eleGift.getImageCode());
					coupon.setOpenId(account.getOpenId());
					coupon.setStartTime(TimeUtil.getDateTime());
					coupon.setOvetTime(2017+TimeUtil.getDateTime().substring(4));
					coupon.setStatus(ElectronicCoupon.ELECTRONIC_COUPON_STATUS_NORMAL);
					dao.addElectronicCoupon(coupon);
					logger.info("电子券信息入库:"+coupon);
					codes.append(codes.length()==0?each.getText():","+each.getText());
				}
				order.setMoreInfo(codes.toString());
				order.setStatus(GiftOrder.ORDER_STATUS_SUCCESS);
				dao.updateGiftOrder(order);
				logger.info("更新电子券订单信息:"+order);
				logger.info("电子券购买成功!"+codes.toString());
				message.setRetCode("0000");
				message.setRetMsg("电子券奖品处理完成!");
			}
		} catch (Exception e) {
			message.setRetCode("9999");
			message.setRetMsg("电子券奖品处理异常!");
			throw new AppException("电子券奖品处理异常!",e);
		}
		return message;
	}

	@SuppressWarnings("finally")
	@Override
	public String getGifts(HttpServletRequest req) {
		ReturnMessage returnMessage=new ReturnMessage();
		try{
			req.setCharacterEncoding("utf-8");
			String gameId=req.getParameter("gameId");
			String tokenStr =req.getParameter("token");
			logger.info("查询游戏奖励:gameId="+gameId+",token="+tokenStr);
			EasyToken token=EasyToken.verifyTokenRenew(tokenStr);
			if(token==null){
				returnMessage.setRetCode("9998");
				returnMessage.setRetMsg("token过期!");
				returnMessage.setObj("token过期!");
				logger.info("token过期!");
				return returnMessage.toJson();
			}
			List<Gift> gifts=dao.getGifts(gameId);
			returnMessage.setRetCode("0000");
			returnMessage.setRetMsg("获取游戏奖品成功!");
			returnMessage.setObj(gifts);
			logger.info("游戏奖品:"+gifts);
			return returnMessage.toJson();
		}catch (Exception e) {
			returnMessage.setRetCode("9999");
			returnMessage.setRetMsg("获取游戏奖品异常!");
			throw new AppException("获取游戏奖品异常!",e);
		}finally{
			return returnMessage.toJson();
		}
	}
	
	
}

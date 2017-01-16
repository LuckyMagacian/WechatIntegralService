package com.lanxi.WechatIntegralService.service;

import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;

import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.Game;
import com.lanxi.WechatIntegralService.entity.Gift;
import com.lanxi.WechatIntegralService.entity.IntegralGame;
import com.lanxi.WechatIntegralService.util.AppException;
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
			logger.info("游戏信息:"+game);
			
			if(game.getIntegral()>point){
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
				returnMessage.setRetCode("0000");
				returnMessage.setRetMsg("获得了"+gift.getIntegralValue()+"积分!");
				returnMessage.setObj(gift.getPrizeLevel());
				returnMessage.setToken(token.toToken());
				return returnMessage.toJson();
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

	private ReturnMessage dealEleGift(){
		ReturnMessage message=new ReturnMessage();
		try {
			
		} catch (Exception e) {
			message.setRetCode("9999");
			message.setRetMsg("电子券奖品处理异常!");
			throw new AppException("电子券奖品处理异常!",e);
		}
		return message;
	}
}

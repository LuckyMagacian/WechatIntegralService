package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;

import com.lanxi.WechatIntegralService.util.AppException;

public class IntegralGameServiceImpl implements IntegralGameService {

	@Override
	public void startGame(HttpServletRequest req) {
		try{
			req.setCharacterEncoding("utf-8");
			String gameId=req.getParameter("gameId");
			String token =req.getParameter("token");
		}catch (Exception e) {
			throw new AppException("游戏开始异常",e);
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

}

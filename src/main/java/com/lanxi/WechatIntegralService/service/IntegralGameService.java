package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;

public interface IntegralGameService {
	public String getGifts(HttpServletRequest req);
	public String startGame(HttpServletRequest req);
	public void getGameResult(HttpServletRequest req);
	public void getGameReward(HttpServletRequest req);
}

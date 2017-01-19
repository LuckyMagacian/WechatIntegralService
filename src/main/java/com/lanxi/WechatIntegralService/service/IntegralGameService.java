package com.lanxi.WechatIntegralService.service;

import javax.servlet.http.HttpServletRequest;

import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.Gift;
import com.lanxi.integral.report.ReturnMessage;

public interface IntegralGameService {
	public String getGifts(HttpServletRequest req);
	public String startGame(HttpServletRequest req);
	public String getGiftInfo(HttpServletRequest req);
	public ReturnMessage dealEleGift(Gift eleGift,AccountBinding account);
}

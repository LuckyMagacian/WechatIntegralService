package com.lanxi.WechatIntegralService.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanxi.WechatIntegralService.service.IntegralGameService;

@Controller
public class GameController {
	@Resource
	IntegralGameService gameService;
//	@RequestMapping("/game")
	@RequestMapping(value = "/game.do" , produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String game(HttpServletRequest req){
		return gameService.startGame(req);
	}
	@RequestMapping(value = "/getGifts.do" , produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getGifts(HttpServletRequest req){
		return gameService.getGifts(req);
	}
	@RequestMapping(value = "/giftInfo.do" , produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getGiftInfo(HttpServletRequest req){
		return gameService.getGiftInfo(req);
	}
}

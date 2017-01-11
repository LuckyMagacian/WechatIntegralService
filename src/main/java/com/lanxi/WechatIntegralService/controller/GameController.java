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
//	@RequestMapping(value = "/game" , produces = {"application/json;charset=UTF-8"})
	@RequestMapping("/game")
	@ResponseBody
	public String game(HttpServletRequest req){
		return gameService.startGame(req);
	}
}

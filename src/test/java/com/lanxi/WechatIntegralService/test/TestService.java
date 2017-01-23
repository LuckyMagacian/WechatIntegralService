package com.lanxi.WechatIntegralService.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.Game;
import com.lanxi.WechatIntegralService.entity.Gift;
import com.lanxi.WechatIntegralService.service.DaoService;
import com.lanxi.WechatIntegralService.service.ElectronicCouponService;
import com.lanxi.WechatIntegralService.service.IntegralGameService;
import com.lanxi.WechatIntegralService.service.IntegralManagerService;
import com.lanxi.WechatIntegralService.service.IntegralManagerServiceImpl;
import com.lanxi.WechatIntegralService.service.IntegralRedPacketService;
import com.lanxi.WechatIntegralService.service.QuartzService;
import com.lanxi.WechatIntegralService.servlet.Log4jInitServlet;

public class TestService {
	private ApplicationContext ac;
	private DaoService      dao;
	private IntegralGameService game;
	private ElectronicCouponService coupon;
	private IntegralManagerServiceImpl integral;
	private IntegralRedPacketService redpacket;
	private QuartzService quartz;
	@Before
	public void init(){
		ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
		game=ac.getBean(IntegralGameService.class);
		coupon=ac.getBean(ElectronicCouponService.class);
		integral=ac.getBean(IntegralManagerServiceImpl.class);
		redpacket=ac.getBean(IntegralRedPacketService.class);
		quartz=ac.getBean(QuartzService.class);
		dao=ac.getBean(DaoService.class);
		Log4jInitServlet.Log4jInit();
	}
	
	@Test
	public void testGift(){
		Gift gift=dao.getGift("2023");
		AccountBinding account=dao.getAccount("o5uSlw3veETF28qOR7bqqzJpHa44");
		System.out.println(gift);
		System.out.println(account);
		System.out.println(game.dealEleGift(gift, account));
	}
	@Test 
	public void testGame(){
		Game game=dao.getGame("1002");
		assert game==null:"游戏为空";
		System.out.println(game.getPrize(0.0001));
		System.out.println(game.getPrize(0.1));
		System.out.println(game.getPrize(0.2));
		System.out.println(game.getPrize(0.3));
		System.out.println(game.getPrize(0.4));
		System.out.println(game.getPrize(0.5));
		System.out.println(game.getPrize(0.6));
		System.out.println(game.getPrize(0.7));
		System.out.println(game.getPrize(0.8));
		System.out.println(game.getPrize(0.9));
	}
}

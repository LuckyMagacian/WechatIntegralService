package com.lanxi.WechatIntegralService.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.service.BindingService;
import com.lanxi.WechatIntegralService.service.DaoService;
import com.lanxi.WechatIntegralService.service.IntegralGameService;
import com.lanxi.WechatIntegralService.service.IntegralManagerServiceImpl;
import com.lanxi.WechatIntegralService.service.IntegralRedPacketService;
import com.lanxi.WechatIntegralService.util.TimeUtil;

public class TestDao {
	
	private ApplicationContext ac;
	private DaoService dao;
	private IntegralGameService game;
	private IntegralManagerServiceImpl manager;
	private IntegralRedPacketService redpacket;
//	private BindingService binding;
	@Before
	public void init(){
		ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
		dao=ac.getBean(DaoService.class);
		game=ac.getBean(IntegralGameService.class);
		manager=ac.getBean(IntegralManagerServiceImpl.class);
		redpacket=ac.getBean(IntegralRedPacketService.class);
//		binding=ac.getBean(BindingService.class);
	}
	
	@Test
	public void testDaoService(){
		System.out.println();
	}
	@Test
	public void testService(){
		
	}
}

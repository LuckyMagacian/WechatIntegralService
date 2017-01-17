package com.lanxi.WechatIntegralService.test;

import com.lanxi.integral.report.ReturnMessage;
import com.lanxi.integral.service.IntegralService;
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
import com.lanxi.WechatIntegralService.service.QuartzService;
import com.lanxi.WechatIntegralService.util.TimeUtil;

public class TestDao {
	
	private ApplicationContext ac;
	private DaoService dao;
	private IntegralGameService game;
	private IntegralManagerServiceImpl manager;
	private IntegralRedPacketService redpacket;
	private QuartzService quartz;
//	private BindingService binding;
	@Before
	public void init(){
		ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
		dao=ac.getBean(DaoService.class);
		game=ac.getBean(IntegralGameService.class);
		manager=ac.getBean(IntegralManagerServiceImpl.class);
		redpacket=ac.getBean(IntegralRedPacketService.class);
		quartz=ac.getBean(QuartzService.class);
//		binding=ac.getBean(BindingService.class);
	}
	
	@Test
	public void testDaoService(){
		String a="12345";
		String b=a.substring(0,1);
		System.out.println(b);
	}
	@Test
	public void testService(){
		ReturnMessage returnMessage= null;
		try {
			returnMessage = IntegralService.queryIntegral("101330326199412256115");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("数据"+returnMessage.getObj());
		System.out.println(returnMessage.getRetCode());
	}
}

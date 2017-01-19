package com.lanxi.WechatIntegralService.test;

import com.lanxi.integral.report.HistoryResBody;
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

import java.util.List;

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
//		for(int i=1;i<=10;i++){
//			System.out.println(i);
//		}
		int end=2;
		int size=4;
		int endLine=end>size?size:end;
//		System.out.println(endLine);
		int a=size/end;
		System.out.println(a);
		int b=size%end;
		if(b>0){
			a++;
		}
		System.out.println(a);
		System.out.println(b);
		String c="abc";
		String d=c.substring(0,1);
		System.out.println(d);

	}
	@Test
	public void testService(){
		ReturnMessage returnMessage= null;
		try {
			returnMessage = IntegralService.historyIntegral("101331082199311236217","20161225");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("数据"+returnMessage.getObj());
		HistoryResBody historyResBody =(HistoryResBody)returnMessage.getObj();
		List<HistoryResBody.Item> list = historyResBody.getSerialList();
		HistoryResBody.Item item=list.get(0);
		HistoryResBody.Item item2=list.get(1);
		System.out.println(returnMessage.getRetCode());
		System.out.println(item.getPointsVal());
		System.out.println(item2.getPointsVal());

	}
}

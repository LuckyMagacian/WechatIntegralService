package com.lanxi.WechatIntegralService.test;

import org.junit.Before;
import org.junit.Test;

import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.wechat.entity.menu.ClickButton;
import com.lanxi.wechat.entity.menu.LocationSelectButton;
import com.lanxi.wechat.entity.menu.SubButton;
import com.lanxi.wechat.entity.menu.ViewButton;
import com.lanxi.wechat.entity.menu.ViewsMessageButton;
import com.lanxi.wechat.entity.menu.WechatMenu;
import com.lanxi.wechat.entity.token.AccessToken;
import com.lanxi.wechat.entity.token.WebAccessTokenRequst;
import com.lanxi.wechat.manageer.MenuManager;
import com.lanxi.wechat.manageer.TokenManager;

public class TestMenu {
	@Before
	public void init(){
		TokenManager.loadAccessToken();
	}
	@Test
	public void testClickMenu(){
		ClickButton click=new ClickButton();
		click.setKey("clickme");
		click.setName("点我点我点我");
		WechatMenu menu=new WechatMenu();
		SubButton subButton=new SubButton();
		subButton.setName("子菜单");
		subButton.addButton(click);
		menu.addButton(subButton);
		
		ClickButton click2=new ClickButton();
		click2.setKey("clickme");
		click2.setName("点我点我点我");
		
		menu.addButton(click2);
//		System.out.println(subButton.toJson());
		System.out.println(menu.toJson());
	}
	@Test
	public void testDeleteMenu() throws Exception{
		System.out.println(MenuManager.clearMenu());
	}
	@Test
	public void testCreateMenu(){
		SubButton  sub1=new SubButton();
		sub1.setName("蓝喜生活");
		ViewButton			button11=new ViewButton();
		button11.setName("蓝喜简介");
		button11.setUrl("http://u.eqxiu.com/s/QBqidnf7");
		ClickButton 	button12=new ClickButton();
		button12.setName("产品介绍");
		button12.setKey("product");
		ViewButton 			button13=new ViewButton();
		button13.setName("诚聘英才");
		button13.setUrl("http://eqxiu.com/s/Y1mgFM2c");
		sub1.addButton(button11);
		sub1.addButton(button12);
		sub1.addButton(button13);
		SubButton  sub2=new SubButton();
		sub2.setName("消费服务");
		ViewButton 			button21=new ViewButton();
		button21.setName("礼品兑换");
		button21.setUrl("http://www.188lanxi.com/weixinlanxi/exchangeCode/exchangeCode.do?");
		ClickButton         button22=new ClickButton();
		button22.setName("客户服务");
		button22.setKey("server");
		sub2.addButton(button21);
		sub2.addButton(button22);
		SubButton  sub3=new SubButton();
		sub3.setName("点我点我");
		ViewButton          button31=new ViewButton();
		button31.setName("积分服务");
//		button31.setUrl(TokenManager.generatorWebTokenCodeUrl("http://www.188lanxi.com/WechatIntegralService/intoJf.do?",WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_DETAIL));
		button31.setUrl(TokenManager.generatorWebTokenCodeUrl("http://yangyuanjian.imwork.net/WechatIntegralService/intoJf.do?",WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_DETAIL));
		ViewButton 			button32=new ViewButton();
		button32.setName("玩个游戏");
//		button32.setUrl(TokenManager.generatorWebTokenCodeUrl("http://www.188lanxi.com/weixinlanxi/oauth/authorization.do?",WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_DETAIL));
		button32.setUrl(TokenManager.generatorWebTokenCodeUrl("http://yangyuanjian.imwork.net/WechatIntegralService/toPlane.do?",WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_DETAIL));
		sub3.addButton(button31);
		sub3.addButton(button32);
		MenuManager.addSubButton(sub1);
		MenuManager.addSubButton(sub2);
		MenuManager.addSubButton(sub3);
		System.out.println(MenuManager.createMenu());
	}
	@Test
	public void testGetMenu(){
		TokenManager.getAccessTokenMetadata().generatorToken();
		TokenManager.saveAccessToken();
//		AccessToken token=new AccessToken();
//		token.setAppid("wxc6e892670e84ab71");
//		token.setSecret("c8ef04146f4b31a2d4012dacff18b06b");
//		token.generatorToken();
//		String urlStr=ConfigUtil.get("menuGetUrl");
//		urlStr=urlStr.replace("ACCESS_TOKEN",token.getAccessToken());
//		System.out.println(HttpUtil.get(urlStr, "utf-8"));
		System.out.println(MenuManager.getMenu());
	}
	@Test
	public void makeToken() throws Exception{
		System.out.println(TokenManager.generatorWebTokenCodeUrl(ConfigUtil.get("unpackRedPacketUrl")+"redPacketId="+110,WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_DETAIL));
	}
	
	@Test
	public void unicodeTest(){
		System.out.println("💐🐾");
	}
}

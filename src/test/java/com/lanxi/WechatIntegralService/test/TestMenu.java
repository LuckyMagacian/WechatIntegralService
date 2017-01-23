package com.lanxi.WechatIntegralService.test;

import org.junit.Before;
import org.junit.Test;

import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.wechat.entity.menu.ClickButton;
import com.lanxi.wechat.entity.menu.LocationSelectButton;
import com.lanxi.wechat.entity.menu.SubButton;
import com.lanxi.wechat.entity.menu.ViewButton;
import com.lanxi.wechat.entity.menu.WechatMenu;
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
		LocationSelectButton locationButton=new LocationSelectButton();
		locationButton.setName("提莫队长前来报道");
		locationButton.setKey("timo");
		MenuManager.addButton(locationButton);
		ViewButton viewButton1=new ViewButton();
		viewButton1.setName("积分服务平台");		
		viewButton1.setUrl(TokenManager.generatorWebTokenCodeUrl("http://yangyuanjian.imwork.net/WechatIntegralService/intoJf.do",WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_DETAIL));
		MenuManager.addButton(viewButton1);
		System.out.println(viewButton1.toJson());
		System.out.println(MenuManager.createMenu());
	}
	@Test
	public void testGetMenu(){
		System.out.println(MenuManager.getMenu());
	}
	@Test
	public void makeToken() throws Exception{
		TokenManager.getAccessToken();
		TokenManager.saveAccessToken();
	}
}

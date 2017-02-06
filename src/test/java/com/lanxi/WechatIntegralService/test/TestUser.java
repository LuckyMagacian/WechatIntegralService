package com.lanxi.WechatIntegralService.test;

import org.junit.Before;
import org.junit.Test;

import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.wechat.manageer.TokenManager;
import com.lanxi.wechat.manageer.UserManager;

public class TestUser {
	
	@Before
	public void init(){
		TokenManager.loadAccessToken();
	}
	@Test
	public void testGet(){
		System.out.println(UserManager.getWebUserInfo("o5uSlw3veETF28qOR7bqqzJpHa44"));
	}
	
	@Test
	public void testGetInfo2(){
		String openId="o5uSlw3veETF28qOR7bqqzJpHa44";
		String urlStr=ConfigUtil.get("webUserInfoGetUrl");
		String webToken=HttpUtil.get("http://yangyuanjian.imwork.net/WechatIntegralService/getWebAccessToken.do?openId="+openId, "utf-8");
		System.out.println(webToken);
		urlStr=urlStr.replace("ACCESS_TOKEN",webToken);
		urlStr=urlStr.replace("OPENID",openId);
		System.out.println(HttpUtil.get(urlStr, "utf-8"));
	}
	@Test
	public void unicodeTest(){
		System.out.println("\ue412");
		System.out.println(UserManager.getUserInfo("o5uSlw3veETF28qOR7bqqzJpHa44"));
	}
}

package com.lanxi.WechatIntegralService.test;

import org.junit.Before;
import org.junit.Test;

import com.lanxi.wechat.manageer.TokenManager;
import com.lanxi.wechat.manageer.UserManager;

public class TestUser {
	
	@Before
	public void init(){
		TokenManager.loadAccessToken();
	}
	@Test
	public void testGetInfo(){
		System.out.println(UserManager.getUserInfo("o5uSlw5jx0yXBgm4guipwhV3BCTM"));
	}
}

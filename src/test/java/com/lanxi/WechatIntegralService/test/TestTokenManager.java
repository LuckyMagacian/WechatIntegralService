package com.lanxi.WechatIntegralService.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;


import org.junit.Before;
import org.junit.Test;

import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.wechat.entity.token.AccessToken;
import com.lanxi.wechat.entity.token.WebAccessTokenRequst;
import com.lanxi.wechat.manageer.TokenManager;

public class TestTokenManager {
	@Before
	public void init(){
		TokenManager.loadAccessToken();
	}
	@Test
	public void testTestClass(){
		TestClass obj=new TestClass();
		obj.fresh();
		while(true);
	}
	@Test
	public void testGetUserInfo() throws Exception{
		AccessToken token=AccessToken.loadToken();
		String openid="o5uSlw5jx0yXBgm4guipwhV3BCTM";
		String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url=url.replace("ACCESS_TOKEN",token.getAccessToken());
		url=url.replace("OPENID",openid);
		System.out.println(HttpUtil.get(url, ConfigUtil.get("charset")));
	}
	@Test
	public void testGetCode(){
		WebAccessTokenRequst req=new WebAccessTokenRequst();
		System.out.println(req.generatorCodeUrl());
	}
}
class TestClass{
	private int life;
	private Timer timer;
	private Long start;
	Random random=new Random();
	public TestClass(){
		life=random.nextInt(5000)+3000;
	}
	public void fresh(){
		if(timer!=null)
			timer.stop();
		life=random.nextInt(5000)+3000;
		timer=new Timer((int) (life*0.9),new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("timer run");
				System.out.println(life*0.9);
				System.out.println((System.currentTimeMillis()-start));
				fresh();
			}
		});
		timer.start();
		start=System.currentTimeMillis();
		System.out.println("life:"+life);
	}
}

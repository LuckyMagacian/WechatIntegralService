package com.lanxi.wechat.manageer;

import com.lanxi.wechat.entity.token.AccessToken;
import com.lanxi.wechat.entity.token.WebAccessToken;
import com.lanxi.wechat.entity.token.WebAccessTokenRequst;
import com.lanxi.wechat.entity.token.WebRefreshToken;

public class TokenManager {
	private static AccessToken accessToken;

	private static WebAccessTokenRequst webAccessTokenRequst;
	
	private static WebAccessToken webAccessToken;
	
	private static WebRefreshToken webRefreshToken;
	
	private TokenManager(){
		
	}
	
	public static String getAccessToken(){
		if(accessToken==null){
			accessToken=new AccessToken();
			accessToken.generatorToken();
		}
		if(accessToken.getOverTime().compareTo(System.currentTimeMillis()+"")<1){
			System.out.println("token over time !");
			accessToken=new AccessToken();
			accessToken.generatorToken();
			// TODO  删除保存token
			saveAccessToken();
		}
		return accessToken.getAccessToken();
	}
	public static void saveAccessToken(){
		if(accessToken==null){
			accessToken=new AccessToken();
			accessToken.generatorToken();
		}
		accessToken.saveToken();
	}
	public static String loadAccessToken(){
		accessToken=AccessToken.loadToken();
		return accessToken.getAccessToken();
	}
}

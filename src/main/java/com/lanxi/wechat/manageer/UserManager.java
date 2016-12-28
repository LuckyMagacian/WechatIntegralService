package com.lanxi.wechat.manageer;

import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;

public class UserManager {
	
	private UserManager(){
		
	}

	public static String getUserInfo(String userId){
		String urlStr=ConfigUtil.get("userBaseInfoGetUrl");
		String accessToken=TokenManager.getAccessToken();
		urlStr=urlStr.replace("ACCESS_TOKEN",accessToken);
		urlStr=urlStr.replace("OPENID",userId);
		return HttpUtil.get(urlStr, "utf-8");
	}
}

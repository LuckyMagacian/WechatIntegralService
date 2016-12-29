package com.lanxi.wechat.manageer;

import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
/**
 * 微信用户管理类
 * @author 1
 *
 */
public class UserManager {
	
	private UserManager(){
		
	}
	/**
	 * 通过用户微信号(对当前公众号)获取用户信息
	 * @param userId
	 * @return
	 */
	public static String getUserInfo(String userId){
		String urlStr=ConfigUtil.get("userBaseInfoGetUrl");
		urlStr=urlStr.replace("ACCESS_TOKEN",TokenManager.getAccessToken());
		urlStr=urlStr.replace("OPENID",userId);
		return HttpUtil.get(urlStr, "utf-8");
	}
	/**
	 * 通过用户微信号(对当前公众号)获取用户网页信息
	 * @param userId
	 * @return
	 */
	public static String getWebUserInfo(String userId){
		String urlStr=ConfigUtil.get("webUserInfoGetUrl");
		urlStr=urlStr.replace("ACCESS_TOKEN",TokenManager.getWebAccessToken(userId));
		urlStr=urlStr.replace("OPENID",userId);
		return HttpUtil.get(urlStr, "utf-8");
	}
}

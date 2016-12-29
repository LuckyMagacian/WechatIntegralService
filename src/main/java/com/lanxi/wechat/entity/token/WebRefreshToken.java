package com.lanxi.wechat.entity.token;

import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
/**
 * 微信网页凭证刷新凭证
 * @author 1
 *
 */
public class WebRefreshToken {
	private String appid;			/**开发者应用编号*/
	private String grantType="refresh_token";
	private String refreshToken;	/**刷新凭证-字符串*/
	private String expiresIn;		/**有效时间*/
	private String generatorTime;	/**生成时间*/
	private String overTime;		/**过期时间*/
	public WebRefreshToken(){
		setAppid(ConfigUtil.get("appID"));
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getGeneratorTime() {
		return generatorTime;
	}
	public void setGeneratorTime(String generatorTime) {
		this.generatorTime = generatorTime;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String freshWebAccessToken(){
		String urlStr=ConfigUtil.get("webTokenRefreshUrl");
		urlStr=urlStr.replace("APPID",getAppid());
		urlStr=urlStr.replace("REFRESH_TOKEN",getRefreshToken());
		return HttpUtil.get(urlStr, "utf-8");
	}
}

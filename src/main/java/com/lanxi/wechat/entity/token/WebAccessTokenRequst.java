package com.lanxi.wechat.entity.token;

import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;

public class WebAccessTokenRequst {
	/**网页授权-基本信息*/
	public static final String WEB_ACCESS_TOOKEN_SCOPE_BASE		="snsapi_base";
	/**网页授权-详细信息*/
	public static final String WEB_ACCESS_TOOKEN_SCOPE_DETAIL	="snsapi_userinfo";
	
	private String appid;
	private String redirectUri;
	private String responseType="code";
	private String scope;
	private String state;
	private String wechatRedirect="#wechat_redirect";
	
	private String code;		//用户同意授权后将作为跳转url的参数返回
	private String grantType;
	
	public WebAccessTokenRequst() {
		setAppid(ConfigUtil.get("appID"));
		setRedirectUri(ConfigUtil.get("getCodeUrl"));
		setScope(WEB_ACCESS_TOOKEN_SCOPE_DETAIL);
		setState("test");
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWechatRedirect() {
		return wechatRedirect;
	}
	public void setWechatRedirect(String wechatRedirect) {
		this.wechatRedirect = wechatRedirect;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String generatorCodeUrl(){
		String urlStr=ConfigUtil.get("webTokenCodeGetUrl");
		urlStr=urlStr.replace("APPID", appid);
		urlStr=urlStr.replace("REDIRECT_URI", redirectUri);
		urlStr=urlStr.replace("STATE", state);
		urlStr=urlStr.replace("SCOPE", scope);
		return urlStr;
	}
	
	public WebAccessToken getWebAccessToken(){
		WebAccessToken token=null;
		//TODO 获取网页授权token
		return token;
	}
}

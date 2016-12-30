package com.lanxi.wechat.entity.token;

import javax.swing.Timer;

import com.alibaba.fastjson.JSONObject;
/**
 * 微信授权凭证-网页授权凭证
 * @author 1
 *
 */
public class WebAccessToken {
	/**网页凭证*/
	private String accessToken;		
	/**有效期*/
	private String expiresIn;		
	/**刷新凭证*/
	private WebRefreshToken refreshToken;
	/**对应用户id*/
	private String openId;			
	/**权限范围*/
	private String scope;			
	/**用户通用id*/
	private String unionid;			
	/**生成时间*/
	private String generatorTime;	
	/**过期时间*/
	private String overTime;		
	/**定时器*/
	private Timer  timer;			
	public WebAccessToken(){
		refreshToken=new WebRefreshToken();
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public WebRefreshToken getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(WebRefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
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
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public String refresh(){
		String rs=getRefreshToken().freshWebAccessToken();
		JSONObject jobj=JSONObject.parseObject(rs);
		if(jobj.getString("errcode")!=null)
			return null;
		setAccessToken(jobj.getString("access_token"));
		setExpiresIn(jobj.getString("expires_in"));
		setOpenId(jobj.getString("openid"));
		getRefreshToken().setRefreshToken(jobj.getString("refresh_token"));
		setScope(jobj.getString("scope"));
		return getAccessToken();
	}
	public WebAccessToken refreshMetadata(){
		String rs=getRefreshToken().freshWebAccessToken();
		JSONObject jobj=JSONObject.parseObject(rs);
		if(jobj.getString("errcode")!=null)
			return null;
		setAccessToken(jobj.getString("access_token"));
		setExpiresIn(jobj.getString("expires_in"));
		setOpenId(jobj.getString("openid"));
		getRefreshToken().setRefreshToken(jobj.getString("refresh_token"));
		setScope(jobj.getString("scope"));
		return this;
	}
}

package com.lanxi.wechat.entity.user;

import com.alibaba.fastjson.JSONObject;

public class BaseUserInfo {
	/**用户关注状态-关注*/
	public static final String USER_SUBSCRIBE_STATUS_SUB="1";
	/**用户关注状态-未关注*/
	public static final String USER_SUBSCRIBE_STATUS_UNSUB="0";
	private String openId;
	private String subscribe;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	public void fromStr(String infoStr){
		JSONObject jobj=JSONObject.parseObject(infoStr);
		setOpenId(jobj.getString("openid"));
		setSubscribe(jobj.getString("subscribe"));
	}
}	

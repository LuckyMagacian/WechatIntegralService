package com.lanxi.wechat.entity.user;

import com.alibaba.fastjson.JSONObject;

public class UserInfo extends BaseUserInfo {
	private String nickName;
	private String sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headImgUrl;
	private String subscribeTime;
	private String unionid;
	private String remark;
	private String groupid;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void fromStr(String infoStr){
		super.fromStr(infoStr);
		if(getSubscribe().equals(USER_SUBSCRIBE_STATUS_UNSUB))
			return;
		JSONObject jobj=JSONObject.parseObject(infoStr);
		setNickName(jobj.getString("nickname"));
		setSex(jobj.getString("sex"));
		setLanguage(jobj.getString("language"));
		setCity(jobj.getString("city"));
		setProvince(jobj.getString("province"));
		setCountry(jobj.getString("country"));
		setHeadImgUrl(jobj.getString("headimgurl"));
		setSubscribeTime(jobj.getString("subscribe_time"));
		setUnionid(jobj.getString("unionid"));
		setRemark(jobj.getString("remark"));
		setGroupid(jobj.getString("groupid"));
	}
}

package com.lanxi.wechat.entity.user;

import com.alibaba.fastjson.JSONObject;
/**
 * 用户详细信息
 * @author 1
 *
 */
public class UserInfo extends BaseUserInfo {
	/**用户信息-性别-未知*/
	public static final String USER_INFO_SEX_UNKNOWN="0";
	/**用户信息-性别-男*/
	public static final String USER_INFO_SEX_MAN	="1";
	/**用户信息-性别-女*/
	public static final String USER_INFO_SEX_WOMAN	="2";
	/**用户昵称*/
	private String nickName;	
	/**用户性别*/
	private String sex;			
	/**用户语言*/	
	private String language;	
	/**用户城市*/
	private String city;		
	/**用户省份*/
	private String province;	
	/**国籍*/
	private String country;		
	/**头像url*/
	private String headImgUrl;	
	/**关注时间*/
	private String subscribeTime;
	/**通用id*/
	private String unionid;		
	/**备注*/
	private String remark;		
	/**分组信息*/
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
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	
}

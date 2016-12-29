package com.lanxi.wechat.entity.user;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
/**
 * 网页获取用户信息
 * @author 1
 *
 */
public class WebUserInfo {
	private String nickName;		/**昵称*/
	private String sex;				/**性别*/
	private String language;		/**语言*/	
	private String city;			/**城市*/
	private String province;		/**省份*/
	private String country;			/**国家*/
	private String headImgUrl;		/**头像url*/
	private List<String> privilege;	/**特权信息*/
	private String unionid;			/**通用id*/
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
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
	public List<String> getPrivilege() {
		return privilege;
	}
	public void setPrivilege(List<String> privilege) {
		this.privilege = privilege;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public void fromStr(String infoStr){
		JSONObject jobj=JSONObject.parseObject(infoStr);
		setNickName(jobj.getString("nickname"));
		setSex(jobj.getString("sex"));
		setLanguage(jobj.getString("language"));
		setCity(jobj.getString("city"));
		setProvince(jobj.getString("province"));
		setCountry(jobj.getString("country"));
		setHeadImgUrl(jobj.getString("headimgurl"));
		setPrivilege(Arrays.asList((String[])jobj.getJSONArray("privilege").toArray()));
		setUnionid(jobj.getString("unionid"));
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

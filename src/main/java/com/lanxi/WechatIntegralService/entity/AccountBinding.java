package com.lanxi.WechatIntegralService.entity;

public class AccountBinding {
	/**绑定的微信号*/
	private String openId;
	/**绑定的微信昵称*/
	private String nickName;
	/**绑定的微信头像*/
	private String headimgUrl;
	/**绑定的积分帐号*/
	private String integralAccount;
	/**绑定的时间*/
	private String bindingTime;
	/**绑定的手机号*/
	private String bindingPhone;
	/**备注*/
	private String remark;
	/**备用*/
	private String beiy;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadimgUrl() {
		return headimgUrl;
	}
	public void setHeadimgUrl(String headimgUrl) {
		this.headimgUrl = headimgUrl;
	}
	public String getIntegralAccount() {
		return integralAccount;
	}
	public void setIntegralAccount(String integralAccount) {
		this.integralAccount = integralAccount;
	}
	public String getBindingTime() {
		return bindingTime;
	}
	public void setBindingTime(String bindingTime) {
		this.bindingTime = bindingTime;
	}
	public String getBindingPhone() {
		return bindingPhone;
	}
	public void setBindingPhone(String bindingPhone) {
		this.bindingPhone = bindingPhone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBeiy() {
		return beiy;
	}
	public void setBeiy(String beiy) {
		this.beiy = beiy;
	}
	
}

package com.lanxi.WechatIntegralService.util;

public class TempSms {
	/**短信通道-移动-免费*/
	public static String SMS_TDID_MOBILE="1";
	/**短信通道-沃动-收费*/
	public static String SMS_TDID_WODONG="2";
	/**商户�?*/
	public static String SMS_MCHTID		="10";
	
	/**商户�? ->10*/
	private String mchtId;		
	/**流水�?->10+年月日时分秒+4位随机数*/
	private String orderId;		
	/**手机�?*/
	private String mobile;		
	/**短信内容*/
	private String content;		
	/**发�?�日�?*/
	private String tradeDate;	
	/**发�?�时�?*/
	private String tradeTime;	
	/**签名*/
	private String sign;		
	/**短信通道�? 1移动 2沃动*/
	private String tdId;		
	public String getMchtId() {
		return mchtId;
	}
	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTdId() {
		return tdId;
	}
	public void setTdId(String tdId) {
		this.tdId = tdId;
	}
	@Override
	public String toString() {
		return "TempSms [mchtId=" + mchtId + ", orderId=" + orderId + ", mobile=" + mobile + ", content=" + content
				+ ", tradeDate=" + tradeDate + ", tradeTime=" + tradeTime + ", sign=" + sign + ", tdId=" + tdId + "]";
	}
	
	
}
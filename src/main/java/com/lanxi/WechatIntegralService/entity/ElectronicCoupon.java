package com.lanxi.WechatIntegralService.entity;

public class ElectronicCoupon {
	/**电子券状态-正常*/
	public static final String ELECTRONIC_COUPON_STATUS_NORMAL="1";
	/**电子券状态-已用*/
	public static final String ELECTRONIC_COUPON_STATUS_USED="2";
	/**电子券状态-过期*/
	public static final String ELECTRONIC_COUPON_STATUS_OVERTIME="3";
	/**电子券编号*/
	private String id;
	/**券码*/	
	private String code;
	/**电子券图片代码*/
	private String imageCode;
	/**拥有者微信号*/
	private String openId;
	/**电子券获取时间*/
	private String startTime;
	/**电子券过期时间*/
	private String overTime;
	/**电子券状态*/
	private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getImageCode() {
		return imageCode;
	}
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOvetTime(String overTime) {
		this.overTime = overTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ElectronicCoupon [id=" + id + ", code=" + code + ", imageCode=" + imageCode + ", openId=" + openId
				+ ", startTime=" + startTime + ", ovetTime=" + overTime + ", status=" + status + "]";
	}
	
}

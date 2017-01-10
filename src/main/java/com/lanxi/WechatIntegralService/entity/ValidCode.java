package com.lanxi.WechatIntegralService.entity;

public class ValidCode {
	/**验证码状态-可以使用*/
	public static final String VALID_CODE_STATUS_NORMAL="1";
	/**验证码状态-已被使用*/
	public static final String VALID_CODE_STATUS_USED="2";
	/**验证码状态-过期*/
	public static final String VALID_CODE_STATUS_OVERTIME="3";
	
	/**手机号码*/
	private String phone;
	/**验证码*/
	private String code;
	/**生成时间*/
	private String startTime;
	/**过期时间*/
	private String overTime;
	/**验证码状态*/
	private String validCodeStatus;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String getValidCodeStatus() {
		return validCodeStatus;
	}
	public void setValidCodeStatus(String validCodeStatus) {
		this.validCodeStatus = validCodeStatus;
	}
	@Override
	public String toString() {
		return "ValidCode [phone=" + phone + ", code=" + code + ", startTime=" + startTime + ", overTime=" + overTime
				+ ", validCodeStatus=" + validCodeStatus + "]";
	}
	
}

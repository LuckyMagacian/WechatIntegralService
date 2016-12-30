package com.lanxi.WechatIntegralService.entity;

public class IntegralRedPacket {
	/**红包编号*/
	private String 	redPacketId;
	/**红包名称*/
	private String 	redPacketName;
	/**红包数量*/
	private Integer redPacketCount;
	/**剩余红包数*/
	private Integer redPacketLessCount;
	/**总积分*/
	private Integer totalIntegral;
	/**剩余积分*/
	private Integer lessIntegeral;
	/**红包url*/
	private String  redPacketUrl;
	/**发放者微信号*/
	private String	openId;
	/**平台流水号*/
	private String 	plateformSerialId;
	/**发放时间*/
	private String  startTime;
	/**过期时间*/
	private String  overTime;
	/**拆红包的微信号*/
	private String 	receivers;
	/**备注*/
	private String 	remark;
	/**备用*/
	private String  beiy;
	public String getRedPacketId() {
		return redPacketId;
	}
	public void setRedPacketId(String redPacketId) {
		this.redPacketId = redPacketId;
	}
	public String getRedPacketName() {
		return redPacketName;
	}
	public void setRedPacketName(String redPacketName) {
		this.redPacketName = redPacketName;
	}
	public Integer getRedPacketCount() {
		return redPacketCount;
	}
	public void setRedPacketCount(Integer redPacketCount) {
		this.redPacketCount = redPacketCount;
	}
	public Integer getRedPacketLessCount() {
		return redPacketLessCount;
	}
	public void setRedPacketLessCount(Integer redPacketLessCount) {
		this.redPacketLessCount = redPacketLessCount;
	}
	public Integer getTotalIntegral() {
		return totalIntegral;
	}
	public void setTotalIntegral(Integer totalIntegral) {
		this.totalIntegral = totalIntegral;
	}
	public Integer getLessIntegeral() {
		return lessIntegeral;
	}
	public void setLessIntegeral(Integer lessIntegeral) {
		this.lessIntegeral = lessIntegeral;
	}
	public String getRedPacketUrl() {
		return redPacketUrl;
	}
	public void setRedPacketUrl(String redPacketUrl) {
		this.redPacketUrl = redPacketUrl;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getPlateformSerialId() {
		return plateformSerialId;
	}
	public void setPlateformSerialId(String plateformSerialId) {
		this.plateformSerialId = plateformSerialId;
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
	public String getReceivers() {
		return receivers;
	}
	public void setReceivers(String receivers) {
		this.receivers = receivers;
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

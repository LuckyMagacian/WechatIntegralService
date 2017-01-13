package com.lanxi.WechatIntegralService.entity;

public class IntegralRedPacket {
	/**红包状态-正常*/
	public static final String RED_PACKET_STATUS_NORML="1";
	/**红包状态-领完*/
	public static final String RED_PACKET_STATUS_NONE="2";
	/**红包状态-过期*/
	public static final String RED_PACKET_STATUS_OVERTIME="3";
	
	
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
	private Integer lessIntegral;
	/**红包url*/
	private String  redPacketUrl;
	/**红包状态*/
	private String  redPacketStatus;
	/**发放者微信号*/
	private String	openId;
	/**发放者微信昵称*/
	private String  nickName;
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
	public Integer getLessIntegral() {
		return lessIntegral;
	}
	public void setLessIntegral(Integer lessIntegral) {
		this.lessIntegral = lessIntegral;
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
	
	public String getRedPacketStatus() {
		return redPacketStatus;
	}
	public void setRedPacketStatus(String redPacketStatus) {
		this.redPacketStatus = redPacketStatus;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Override
	public String toString() {
		return "IntegralRedPacket [redPacketId=" + redPacketId + ", redPacketName=" + redPacketName
				+ ", redPacketCount=" + redPacketCount + ", redPacketLessCount=" + redPacketLessCount
				+ ", totalIntegral=" + totalIntegral + ", lessIntegral=" + lessIntegral + ", redPacketUrl="
				+ redPacketUrl + ", redPacketStatus=" + redPacketStatus + ", openId=" + openId + ", nickName="
				+ nickName + ", plateformSerialId=" + plateformSerialId + ", startTime=" + startTime + ", overTime="
				+ overTime + ", receivers=" + receivers + ", remark=" + remark + ", beiy=" + beiy + "]";
	}
	
}

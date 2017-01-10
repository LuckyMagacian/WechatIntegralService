package com.lanxi.WechatIntegralService.entity;

public class RedPacketReceive {
	/**红包编号*/
	private String 	redPacketId;
	/**微信号*/
	private String 	openId;
	/**所得积分*/
	private Integer integral;
	/**平台流水号*/
	private String 	plateformSerialId;
	/**拆红包时间*/
	private String 	receiveTime;
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getPlateformSerialId() {
		return plateformSerialId;
	}
	public void setPlateformSerialId(String plateformSerialId) {
		this.plateformSerialId = plateformSerialId;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
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
	@Override
	public String toString() {
		return "RedPacketReceive [redPacketId=" + redPacketId + ", openId=" + openId + ", integral=" + integral
				+ ", plateformSerialId=" + plateformSerialId + ", receiveTime=" + receiveTime + ", remark=" + remark
				+ ", beiy=" + beiy + "]";
	}
	
}

package com.lanxi.WechatIntegralService.entity;

public class IntegralTransfer {
	/**流水号*/
	private String  serialId;
	/**积分平台流水号*/
	private String 	plateformSerialId;
	/**发起赠送的微信号*/
	private String 	giverOpenId;
	/**接收方身份证号*/
	private String 	receiverIdCard;
	/**接收方手机号*/
	private String 	receiverPhone;
	/**赠送积分值*/
	private Integer	integral;
	/**赠送时间*/
	private String 	transferTime;
	/**赠送结果*/
	private String 	result;
	/**备注*/
	private String 	remark;
	/**备用*/
	private String 	beiy;
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public String getPlateformSerialId() {
		return plateformSerialId;
	}
	public void setPlateformSerialId(String plateformSerialId) {
		this.plateformSerialId = plateformSerialId;
	}
	public String getGiverOpenId() {
		return giverOpenId;
	}
	public void setGiverOpenId(String giverOpenId) {
		this.giverOpenId = giverOpenId;
	}
	public String getReceiverIdCard() {
		return receiverIdCard;
	}
	public void setReceiverIdCard(String receiverIdCard) {
		this.receiverIdCard = receiverIdCard;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
		return "IntegralTransfer [serialId=" + serialId + ", plateformSerialId=" + plateformSerialId + ", giverOpenId="
				+ giverOpenId + ", receiverIdCard=" + receiverIdCard + ", receiverPhone=" + receiverPhone
				+ ", integral=" + integral + ", transferTime=" + transferTime + ", result=" + result + ", remark="
				+ remark + ", beiy=" + beiy + "]";
	}
	
}

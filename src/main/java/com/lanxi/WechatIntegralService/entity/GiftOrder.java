package com.lanxi.WechatIntegralService.entity;

public class GiftOrder {
	/**订单编号*/
	private String 	orderId;
	/**微信号*/
	private String 	openId;
	/**礼品编号*/
	private String 	giftId;
	/**礼品数量*/
	private Integer giftCount;
	/**下单时间*/
	private String 	workTime;
	/**订单状态*/
	private String 	status;
	/**礼品平台响应码*/
	private String 	resCode;
	/**串码等*/
	private String  moreInfo;
	/**礼品平台响应信息*/
	private String 	resMsg;
	/**备注*/
	private String 	remark;
	/**备用*/
	private String 	beiy;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public Integer getGiftCount() {
		return giftCount;
	}
	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getMoreInfo() {
		return moreInfo;
	}
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
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
		return "GiftOrder [orderId=" + orderId + ", openId=" + openId + ", giftId=" + giftId + ", giftCount="
				+ giftCount + ", workTime=" + workTime + ", status=" + status + ", resCode=" + resCode + ", moreInfo="
				+ moreInfo + ", resMsg=" + resMsg + ", remark=" + remark + ", beiy=" + beiy + "]";
	}
	
}

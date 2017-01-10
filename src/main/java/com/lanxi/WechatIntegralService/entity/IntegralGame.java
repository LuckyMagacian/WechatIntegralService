package com.lanxi.WechatIntegralService.entity;

import com.lanxi.WechatIntegralService.util.TimeUtil;

public class IntegralGame {
	/**流水号*/
	private String serialId;
	/**微信号*/
	private String openId;
	/**积分平台流水号*/
	private String plateformSerialId;
	/**游戏名称*/
	private String gameName;
	/**游戏编号*/
	private String gameId;
	/**游戏时间*/
	private String gameTime;
	/**消费积分*/
	private String integral;
	/**游戏结果*/
	private String result;
	/**订单编号*/
	private String orderId;
	/**备注*/
	private String remark;
	/**备用*/
	private String beiy;
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
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
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getGameTime() {
		return gameTime;
	}
	public void setGameTime(String gameTime) {
		this.gameTime = gameTime;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public void setGame(Game game){
		setGameId(game.getId());
		setGameName(game.getName());
		setGameTime(TimeUtil.getDateTime());
		setIntegral(game.getIntegral()+"");
	}
	
	@Override
	public String toString() {
		return "IntegralGame [serialId=" + serialId + ", openId=" + openId + ", plateformSerialId=" + plateformSerialId
				+ ", gameName=" + gameName + ", gameId=" + gameId + ", gameTime=" + gameTime + ", integral=" + integral
				+ ", result=" + result + ", orderId=" + orderId + ", remark=" + remark + ", beiy=" + beiy + "]";
	}
	
}

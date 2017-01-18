package com.lanxi.WechatIntegralService.entity;

public class Gift {
	/**礼品编号*/
	private String 	id;
	/**供应商商品编号*/
	private String 	merchantId;
	/**商品名称*/
	private String 	name;
	/**游戏编号*/
	private String	gameId;
	/**奖品等级 0-特等奖 1-一等奖 2-二等奖 3-三等奖*/
	private Integer prizeLevel;
	/**积分价值-留作兑换用*/
	private Integer integralValue;
	/**市场价格*/
	private Double 	price;
	/**商品数量*/
	private Integer count;
	/**卡券类商品图片代码*/
	private String  imageCode;
	/**描述*/
	private String  description;
	/**备用*/
	private String  beiy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public Integer getPrizeLevel() {
		return prizeLevel;
	}
	public void setPrizeLevel(Integer prizeLevel) {
		this.prizeLevel = prizeLevel;
	}
	public Integer getIntegralValue() {
		return integralValue;
	}
	public void setIntegralValue(Integer integralValue) {
		this.integralValue = integralValue;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getImageCode() {
		return imageCode;
	}
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBeiy() {
		return beiy;
	}
	public void setBeiy(String beiy) {
		this.beiy = beiy;
	}
	@Override
	public String toString() {
		return "Gift [id=" + id + ", merchantId=" + merchantId + ", name=" + name + ", gameId=" + gameId
				+ ", prizeLevel=" + prizeLevel + ", integralValue=" + integralValue + ", price=" + price + ", count="
				+ count + ", imageCode=" + imageCode + ", description=" + description + ", beiy=" + beiy + "]";
	}
	
}

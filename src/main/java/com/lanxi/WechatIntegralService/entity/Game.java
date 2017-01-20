package com.lanxi.WechatIntegralService.entity;


public class Game {
	/**游戏编号*/
	private String 	id;
	/**游戏名称*/
	private String	name;
	/**特等奖中奖概率*/
	private Double 	specialPrize;
	/**一等奖中奖概率*/
	private Double 	firstPrize;
	/**二等奖中奖概率*/
	private Double 	secondPrize;
	/**三等奖中奖概率*/
	private Double 	thirdPrize;
	/**未中奖概率*/
	private Double	 noPrize;
	/**游戏消费积分*/
	private Integer integral;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSpecialPrize() {
		return specialPrize;
	}
	public void setSpecialPrize(Double specialPrize) {
		this.specialPrize = specialPrize;
	}
	public Double getFirstPrize() {
		return firstPrize;
	}
	public void setFirstPrize(Double firstPrize) {
		this.firstPrize = firstPrize;
	}
	public Double getSecondPrize() {
		return secondPrize;
	}
	public void setSecondPrize(Double secondPrize) {
		this.secondPrize = secondPrize;
	}
	public Double getThirdPrize() {
		return thirdPrize;
	}
	public void setThirdPrize(Double thirdPrize) {
		this.thirdPrize = thirdPrize;
	}
	public Double getNoPrize() {
		return noPrize;
	}
	public void setNoPrize(Double noPrize) {
		this.noPrize = noPrize;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	public Integer getPrize(Double prize){
		Double temp=specialPrize;
		if(prize<temp)
			return 0;
		temp+=firstPrize;
		if(prize<temp)
			return 1;
		temp+=secondPrize;
		if(prize<temp)
			return 2;
		temp+=secondPrize;
		if(prize<temp)
			return 3;
			return null;
	}
	
	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", specialPrize=" + specialPrize + ", firstPrize=" + firstPrize
				+ ", secondPrize=" + secondPrize + ", thirdPrize=" + thirdPrize + ", noPrize=" + noPrize + ", integral="
				+ integral + "]";
	}
	
}

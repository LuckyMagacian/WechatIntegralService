package com.lanxi.gift.report;
/**
 * 券码类
 * @author 1
 *
 */
public class Sku {
	private String amt;			/**单价*/
	private String code;		/**兑换码*/
	private String endTime;		/**截止日期*/
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "Sku [amt=" + amt + ", code=" + code + ", endTime=" + endTime + "]";
	}
	
}

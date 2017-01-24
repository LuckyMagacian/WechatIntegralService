package com.lanxi.wechat.entity.token;

import javax.swing.Timer;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.wechat.manageer.TokenManager;

public class JSTicket {
	public static final String JSTICKET_TYPE="jsapi";
	/**js凭证*/
	private String ticket;
	/**类型*/
	private String type;
	/**有效期*/
	private String expiresIn;
	/**生成时间*/
	private String generatorTime;	
	/**定时器*/
	private Timer  timer;	
	/**过期时间*/
	private String overTime;		
	public JSTicket() {
		setType(JSTICKET_TYPE);
	}
	public String getTicket() {
		if((Long.parseLong(overTime)-System.currentTimeMillis())<140000)
			generatorTicket();
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getGeneratorTime() {
		return generatorTime;
	}
	public void setGeneratorTime(String generatorTime) {
		this.generatorTime = generatorTime;
	}
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	@Override
	public String toString() {
		return "JSTicket [ticket=" + ticket + ", type=" + type + ", expiresIn=" + expiresIn + ", generatorTime="
				+ generatorTime + ", timer=" + timer + ", overTime=" + overTime + "]";
	}
	public void generatorTicket(){
		String url=ConfigUtil.get("jsTicketUrl");
		url=url.replace("ACCESS_TOKEN",TokenManager.getAccessToken());
		String rs=HttpUtil.get(url, "utf-8");
		JSONObject jobj=JSONObject.parseObject(rs);
		if(jobj.getString("errcode")!=null&&jobj.getString("errcode").equals("0")){
			setTicket(jobj.getString("ticket"));
			setExpiresIn(jobj.getString("expires_in"));
			setGeneratorTime(System.currentTimeMillis()+"");
			setOverTime((Long.parseLong(getGeneratorTime())+jobj.getLong("expires_in")*1000)+"");
		}
	}
}

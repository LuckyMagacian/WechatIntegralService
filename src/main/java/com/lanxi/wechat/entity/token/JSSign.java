package com.lanxi.wechat.entity.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.RandomUtil;
import com.lanxi.wechat.manageer.TokenManager;
import com.lanxi.wechat.service.ValidServerService;

public class JSSign {
	/**随机字符串*/
	private String nonce;
	/**JS凭证*/
	private JSTicket ticket;
	/**时间戳*/
	private String timeStamp;
	/**签名授权目标url*/
	private String url;
	/**签名*/
	private String sign;
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public JSTicket getTicket() {
		return ticket;
	}
	public void setTicket(JSTicket ticket) {
		this.ticket = ticket;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "JSSign [nonce=" + nonce + ", ticket=" + ticket + ", timeStamp=" + timeStamp + ", url=" + url + ", sign="
				+ sign + "]";
	}
	public String toJson(){
		return JSONObject.toJSONString(this);
	}
	/**
	 * jsTicket签名
	 * @return
	 */
	public String generatorSign(){
		ticket=ticket==null?TokenManager.getJsTicketMetaData():ticket;
		timeStamp=timeStamp==null?System.currentTimeMillis()+"":timeStamp;
		nonce=nonce==null?RandomUtil.getRandomChar(10):nonce;
		url=url==null?ConfigUtil.get("webTokenTo"):url;
		if(timeStamp.length()>10)
			timeStamp=timeStamp.substring(0, 10);
		List<String> list=new ArrayList<>();
		Map<String , String> map=new HashMap<>();
		list.add("noncestr");
		map.put("noncestr",nonce);
		list.add("timestamp");
		map.put("timestamp",timeStamp);
		list.add("url");
		map.put("url",url);
		list.add("jsapi_ticket");
		map.put("jsapi_ticket",ticket.getTicket());
		Collections.sort(list);
		StringBuffer temp=new StringBuffer();
		boolean flag=true;
		for(String each:list){
			if(!flag)
				temp.append('&');
			flag=false;
			temp.append(each+"="+map.get(each));
		}
		setSign(ValidServerService.sign(temp.toString()));
		return getSign();
	}
}

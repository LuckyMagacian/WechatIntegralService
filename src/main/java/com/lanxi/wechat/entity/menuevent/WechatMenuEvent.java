package com.lanxi.wechat.entity.menuevent;

import org.dom4j.DocumentHelper;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.wechat.entity.event.WechatEventMessage;

public abstract class WechatMenuEvent extends WechatEventMessage {
	private String eventKey;
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public  DOMElement    toElement(){
		DOMElement element=super.toElement();
		element.addElement("EventKey").addCDATA(eventKey);
		return element;
	}
	public void fromString(String str){
		try{
			super.fromString(str);
			setEvent(DocumentHelper.parseText(str).getRootElement().elementText("EventKey"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}

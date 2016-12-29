package com.lanxi.wechat.entity.event;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-事件消息类-自定义菜单事件消息类-点击链接按钮
 * @author 1
 *
 */
public class ViewEvent extends WechatEventMessage {
	private String eventKey;
	public ViewEvent() {
		setEvent(EVENT_VIEW);
	}
	
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("EventKey").addCDATA(eventKey);
		return element;
	}
	@Override
	public void  fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setEventKey(root.elementText("EventKey"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}

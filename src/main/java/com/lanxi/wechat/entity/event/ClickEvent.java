package com.lanxi.wechat.entity.event;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-事件消息类-自定义菜单事件-点击下拉消息事件
 * 建议使用menuevnet包中的clickmenuevent
 * @author 1
 *
 */
public class ClickEvent extends WechatEventMessage {
	/**事件key值*/
	private String eventKey;	
	public ClickEvent() {
		setEvent(EVENT_CLICK);
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

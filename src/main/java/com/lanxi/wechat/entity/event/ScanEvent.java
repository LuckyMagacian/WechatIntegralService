package com.lanxi.wechat.entity.event;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-事件消息类-扫码消息类
 * @author 1
 *
 */
public class ScanEvent extends WechatEventMessage {
	/**事件KEY值，qrscene_为前缀，后面为二维码的参数值*/
	private String eventKey;	
	/**二维码的ticket，可用来换取二维码图片*/
	private String ticket;		
	public ScanEvent() {
		setEvent(EVENT_SCAN);
	}
	
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("EventKey").addCDATA(eventKey);
		element.addElement("Ticket").addCDATA(ticket);
		return element;
	}

	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setEventKey(root.elementText("EventKey"));
			setTicket(root.elementText("Ticket"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}

}

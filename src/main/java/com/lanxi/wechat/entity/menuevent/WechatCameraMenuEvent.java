package com.lanxi.wechat.entity.menuevent;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.wechat.entity.menuevent.SystemCameraMenuEvent.SendPicsInfo;

public class WechatCameraMenuEvent extends WechatMenuEvent {
	private SystemCameraMenuEvent.SendPicsInfo sendPicsInfo;
	public WechatCameraMenuEvent() {
		setMsgType(MENU_EVENT_PIC_WEIXIN);
		sendPicsInfo=new SendPicsInfo();
	}
	
	public SendPicsInfo getSendPicsInfo() {
		return sendPicsInfo;
	}
	public void setSendPicsInfo(SendPicsInfo sendPicsInfo) {
		this.sendPicsInfo = sendPicsInfo;
	}
	

	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.add(sendPicsInfo.toElement());
		return element;
	}
	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			sendPicsInfo.fromElement(root.element("SendPicsInfo"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
}

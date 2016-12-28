package com.lanxi.wechat.entity.automessage;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;

public class TextAutoMessage extends WechatAutoMessage {
	private String content;

	public TextAutoMessage() {
		setMsgType(AUTO_MESSAGE_TYPE_TEXT);
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("Content").addCDATA(content);
		return element;
	}

	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setContent(root.elementText("Content"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}

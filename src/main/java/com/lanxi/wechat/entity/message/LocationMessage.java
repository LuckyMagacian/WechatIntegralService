package com.lanxi.wechat.entity.message;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;

public class LocationMessage extends WechatMessage{
	private String locationX;
	private String locationY;
	private String scale;
	private String label;
	
	public String getLocationX() {
		return locationX;
	}
	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}
	public String getLocationY() {
		return locationY;
	}
	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public LocationMessage() {
		setMsgType(MESSAGE_TYPE_LOCATION);
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("Location_X").setText(locationX);;
		element.addElement("Location_Y").setText(locationY);;
		element.addElement("Scale").setText(scale);;
		element.addElement("Label").addCDATA(label);
		return element;
	}
	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setLocationX(root.elementText("Location_X"));
			setLocationY(root.elementText("Location_Y"));
			setScale(root.elementText("Scale"));
			setLabel(root.elementText("Label"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}

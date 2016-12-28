package com.lanxi.wechat.entity.event;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;

public class LocationEvent extends WechatEventMessage {
	private String latitude;
	private String longitude;
	private String precision;
	public LocationEvent() {
		setEvent(EVENT_LOCATION);
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("Latitude").setText(latitude);;
		element.addElement("Longitude").setText(longitude);;
		element.addElement("Precision").setText(precision);;
		return element;
	}

	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setLatitude(root.elementText("Latitude"));
			setLongitude(root.elementText("Longitude"));
			setPrecision(root.elementText("Precision"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}

}
